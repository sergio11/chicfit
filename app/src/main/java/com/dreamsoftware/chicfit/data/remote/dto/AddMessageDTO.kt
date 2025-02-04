package com.dreamsoftware.chicfit.data.remote.dto

data class AddMessageDTO(
    val uid: String,
    val userId: String,
    val question: String,
    val questionRole: String,
    val answer: String,
    val answerRole: String
)
