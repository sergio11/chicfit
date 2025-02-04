package com.dreamsoftware.chicfit.domain.model

data class OutfitMessageBO(
    val uid: String,
    val role: OutfitMessageRoleEnum,
    val text: String
)
