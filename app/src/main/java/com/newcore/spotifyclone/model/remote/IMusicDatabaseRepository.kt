package com.newcore.spotifyclone.model.remote

import com.newcore.spotifyclone.model.entities.Song

interface IMusicDatabaseRepository {
    suspend fun getAllSongs(): List<Song>
}