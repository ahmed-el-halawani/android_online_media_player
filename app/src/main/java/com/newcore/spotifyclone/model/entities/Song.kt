package com.newcore.spotifyclone.model.entities

data class Song(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val songUrl: String
) {
    constructor() : this("", "", "", "", "")
}