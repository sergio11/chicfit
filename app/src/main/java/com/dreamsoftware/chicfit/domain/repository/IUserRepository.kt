package com.dreamsoftware.chicfit.domain.repository

import com.dreamsoftware.chicfit.domain.exception.CheckAuthenticatedException
import com.dreamsoftware.chicfit.domain.exception.CloseSessionException
import com.dreamsoftware.chicfit.domain.exception.SignInException
import com.dreamsoftware.chicfit.domain.exception.SignUpException
import com.dreamsoftware.chicfit.domain.model.AuthRequestBO
import com.dreamsoftware.chicfit.domain.model.AuthUserBO
import com.dreamsoftware.chicfit.domain.model.SignUpBO

interface IUserRepository {

    @Throws(CheckAuthenticatedException::class)
    suspend fun getCurrentAuthenticatedUser(): AuthUserBO

    @Throws(CheckAuthenticatedException::class)
    suspend fun getUserAuthenticatedUid(): String

    @Throws(SignInException::class)
    suspend fun signIn(authRequest: AuthRequestBO): AuthUserBO

    @Throws(SignUpException::class)
    suspend fun signUp(data: SignUpBO): AuthUserBO

    @Throws(CloseSessionException::class)
    suspend fun closeSession()
}