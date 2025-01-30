package com.dreamsoftware.chicfit.domain.validation

interface ISignInValidationMessagesResolver {
    fun getInvalidEmailMessage(): String
    fun getShortPasswordMessage(minLength: Int): String
}