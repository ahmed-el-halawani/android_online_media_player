package com.newcore.spotifyclone.model.remote

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.newcore.spotifyclone.model.entities.Song
import com.newcore.spotifyclone.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MusicDatabaseRepository : IMusicDatabaseRepository {
    val firestore = Firebase.firestore


    override suspend fun getAllSongs(): List<Song> = withContext(Dispatchers.IO) {
        try {
            val songsCollection = firestore.collection(Constants.SONGS_COLLECTION)
            Tasks.await(songsCollection.get()).toObjects(Song::class.java)
        } catch (t: Throwable) {
            println("Throwable: "+t)
            emptyList()
        }
    }


}