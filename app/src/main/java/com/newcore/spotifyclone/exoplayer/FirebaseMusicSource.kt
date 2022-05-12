package com.newcore.spotifyclone.exoplayer

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaMetadataCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.newcore.spotifyclone.model.remote.MusicDatabaseRepository
import com.newcore.spotifyclone.utils.extensions.EntitiesExtensions.toMediaMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseMusicSource @Inject constructor(
    private val musicDatabaseRepository: MusicDatabaseRepository,
) {
    private val listeners = ArrayList<(Boolean) -> Unit>()

    private var songs = emptyList<MediaMetadataCompat>()

    suspend fun fetchMusics() = withContext(Dispatchers.IO) {
        songs = musicDatabaseRepository.getAllSongs()
            .map { it.toMediaMetadata() }

        state = State.STATE_INITIALIZED
    }

    fun asMediaSources(dataSourceFactory: DefaultDataSourceFactory): ConcatenatingMediaSource {
        return ConcatenatingMediaSource().apply {
            addMediaSources(
                songs.map {
                    ProgressiveMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(it.description.mediaUri!!))
                }
            )
        }
    }

    fun asMediaItems() = songs.map {
        MediaBrowserCompat.MediaItem(it.description, FLAG_PLAYABLE)
    }

    private var state = State.STATE_CREATED
        set(value) {
            field = value
            if (value == State.STATE_INITIALIZED || value == State.STATE_ERROR)
                synchronized(listeners) {
                    listeners.forEach { it(value == State.STATE_INITIALIZED) }
                }
        }


    fun whenReady(action: (Boolean) -> Unit) =
        (state == State.STATE_INITIALIZED || state == State.STATE_ERROR).also {
            if (it)
                action(state == State.STATE_INITIALIZED)
            else
                listeners.add(action)
        }
}

enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}