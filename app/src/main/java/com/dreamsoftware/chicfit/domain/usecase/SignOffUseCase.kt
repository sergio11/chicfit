package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.chicfit.domain.repository.IPreferenceRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class SignOffUseCase(
    private val userRepository: IUserRepository,
    private val preferenceRepository: IPreferenceRepository
): BrownieUseCase<Unit>() {
    override suspend fun onExecuted() {
        userRepository.closeSession()
        preferenceRepository.clearData()
    }
}