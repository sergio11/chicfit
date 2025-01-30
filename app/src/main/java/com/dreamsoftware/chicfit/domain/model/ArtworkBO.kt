package com.dreamsoftware.chicfit.domain.model

import java.util.Date

data class ArtworkBO(
    val uid: String,
    val userId: String,
    val imageUrl: String,
    val imageDescription: String,
    val createAt: Date,
    val question: String,
    val messages: List<ArtworkMessageBO>
)
