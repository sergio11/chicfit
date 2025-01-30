package com.dreamsoftware.chicfit.domain.model

data class AuthUserBO(
    val uid: String,
    val displayName: String,
    val email: String,
    val photoUrl: String
)