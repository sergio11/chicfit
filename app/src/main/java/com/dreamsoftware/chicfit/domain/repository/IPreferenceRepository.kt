package com.dreamsoftware.chicfit.domain.repository

import com.dreamsoftware.chicfit.domain.exception.PreferenceDataException

interface IPreferenceRepository {

    @Throws(PreferenceDataException::class)
    suspend fun saveAuthUserUid(uid: String)

    @Throws(PreferenceDataException::class)
    suspend fun getAuthUserUid(): String

    @Throws(PreferenceDataException::class)
    suspend fun setAssistantMutedStatus(isMuted: Boolean)

    @Throws(PreferenceDataException::class)
    suspend fun isAssistantMuted(): Boolean

    @Throws(PreferenceDataException::class)
    suspend fun clearData()
}