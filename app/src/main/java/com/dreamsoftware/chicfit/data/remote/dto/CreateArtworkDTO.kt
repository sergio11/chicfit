package com.dreamsoftware.chicfit.data.remote.dto

data class CreateArtworkDTO(
    val uid: String,
    val userId: String,
    val imageUrl: String,
    val imageDescription: String,
    val question: String,
    val questionRole: String,
    val answer: String,
    val answerRole: String
)
