package com.dreamsoftware.chicfit.data.local.preferences.datasource

interface IPreferencesDataSource {

    suspend fun saveAuthUserUid(uid: String)

    suspend fun getAuthUserUid(): String

    suspend fun setAssistantMutedStatus(isMuted: Boolean)

    suspend fun isAssistantMuted(): Boolean

    suspend fun clearData()
}