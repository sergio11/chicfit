package com.dreamsoftware.chicfit.data.remote.dto

import com.google.firebase.Timestamp

data class ArtworkDTO(
    val uid: String,
    val userId: String,
    val imageUrl: String,
    val imageDescription: String,
    val createAt: Timestamp,
    val messages: List<ArtworkMessageDTO>
)