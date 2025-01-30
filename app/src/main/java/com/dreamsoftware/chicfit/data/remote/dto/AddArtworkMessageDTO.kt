package com.dreamsoftware.chicfit.data.remote.dto

data class AddArtworkMessageDTO(
    val uid: String,
    val userId: String,
    val question: String,
    val questionRole: String,
    val answer: String,
    val answerRole: String
)
