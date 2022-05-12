package com.newcore.spotifyclone.exoplayer

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val SERVICE_TAG = "SERVICE_TAG"

@AndroidEntryPoint
class MusicService : MediaBrowserServiceCompat() {


    @Inject
    lateinit var dataSourceFactory: DefaultDataSourceFactory

    @Inject
    lateinit var exoplayerProvider: ExoPlayer


    lateinit var mediaSessionCompat: MediaSessionCompat
    lateinit var mediaSessionConnector: MediaSessionConnector


    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?,
    ): BrowserRoot? {
        TODO("Not yet implemented")
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>,
    ) {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()

        /**
         * packageManager#getLaunchIntnetForPackage
         * use to open package with packageName
         * in our case open the same application
         */
        val pendingIntent = packageManager?.getLaunchIntentForPackage(packageName).let {
            PendingIntent.getActivity(this, 200, it,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }


        mediaSessionCompat = MediaSessionCompat(this, SERVICE_TAG).apply {
            setSessionActivity(pendingIntent)
            isActive = true
        }

        val mediaToken = mediaSessionCompat.sessionToken

        mediaSessionConnector = MediaSessionConnector(mediaSessionCompat)
        mediaSessionConnector.setPlayer(exoplayerProvider)

    }
}