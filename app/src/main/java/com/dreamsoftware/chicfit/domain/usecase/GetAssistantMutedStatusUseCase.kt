package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.chicfit.domain.repository.IPreferenceRepository

class GetAssistantMutedStatusUseCase(
    private val preferencesRepository: IPreferenceRepository
) : BrownieUseCase<Boolean>() {

    override suspend fun onExecuted(): Boolean =
        preferencesRepository.isAssistantMuted()
}