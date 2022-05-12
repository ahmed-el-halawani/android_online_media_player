package com.newcore.spotifyclone.utils.extensions

import android.support.v4.media.MediaMetadataCompat
import com.newcore.spotifyclone.model.entities.Song

object EntitiesExtensions {
    fun Song.toMediaMetadata(): MediaMetadataCompat =
        MediaMetadataCompat.Builder().apply {
            putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
            putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, songUrl)

            putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST, subtitle)
            putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, subtitle)

            putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
            putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, title)

            putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, imageUrl)
            putString(MediaMetadataCompat.METADATA_KEY_ART_URI, imageUrl)
            putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, imageUrl)
        }.build()
}