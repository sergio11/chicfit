package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.chicfit.domain.service.ITranscriptionService

class EndUserSpeechCaptureUseCase(
    private val transcriptionService: ITranscriptionService
) : BrownieUseCase<Unit>() {

    override suspend fun onExecuted() =
        transcriptionService.stopListening()
}