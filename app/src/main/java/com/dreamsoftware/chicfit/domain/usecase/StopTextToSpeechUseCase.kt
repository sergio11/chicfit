package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.chicfit.domain.service.ITTSService

class StopTextToSpeechUseCase(
    private val ttsService: ITTSService
) : BrownieUseCase<Unit>() {

    override suspend fun onExecuted() {
        ttsService.stop()
    }
}