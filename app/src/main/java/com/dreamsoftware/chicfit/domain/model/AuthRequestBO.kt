package com.dreamsoftware.chicfit.domain.model

data class AuthRequestBO(
    val email: String,
    val password: String
) {
    companion object {
        const val FIELD_EMAIL = "email"
        const val FIELD_PASSWORD = "password"
    }
}