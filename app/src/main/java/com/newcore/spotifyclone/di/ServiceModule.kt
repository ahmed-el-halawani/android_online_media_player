package com.newcore.spotifyclone.di

import android.content.Context
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.newcore.spotifyclone.model.remote.MusicDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
class ServiceModule {


    @ServiceScoped
    @Provides
    fun getFirestore() = Firebase.firestore

    @ServiceScoped
    @Provides
    fun getMusicDatabaseRepo(firebaseFirestore: FirebaseFirestore) =
        MusicDatabaseRepository(firebaseFirestore)

    @ServiceScoped
    @Provides
    fun provideAudio() = AudioAttributes.Builder()
        .setContentType(C.CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()


    @ServiceScoped
    @Provides
    fun provideExoPlayer(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes,
    ) = ExoPlayer.Builder(context).build().apply {
        setAudioAttributes(audioAttributes, true)
        setHandleAudioBecomingNoisy(true)
    }


    @ServiceScoped
    @Provides
    fun provideDateSourceFactory(
        @ApplicationContext context: Context,
    ) = DefaultDataSourceFactory(context, Util.getUserAgent(context, "spotyApp"))

}