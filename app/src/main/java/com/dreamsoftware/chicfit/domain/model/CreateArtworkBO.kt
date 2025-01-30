package com.dreamsoftware.chicfit.domain.model

data class CreateArtworkBO(
    val uid: String,
    val userId: String,
    val imageUrl: String,
    val imageDescription: String,
    val question: String,
    val answer: String
)
