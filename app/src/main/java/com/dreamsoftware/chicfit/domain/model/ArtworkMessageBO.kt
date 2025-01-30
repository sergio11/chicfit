package com.dreamsoftware.chicfit.domain.model

data class ArtworkMessageBO(
    val uid: String,
    val role: ArtworkMessageRoleEnum,
    val text: String
)
