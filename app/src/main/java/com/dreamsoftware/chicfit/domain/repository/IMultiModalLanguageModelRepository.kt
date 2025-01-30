package com.dreamsoftware.chicfit.domain.repository

import com.dreamsoftware.chicfit.domain.exception.GenerateImageDescriptionException
import com.dreamsoftware.chicfit.domain.exception.ResolveQuestionFromContextException
import com.dreamsoftware.chicfit.domain.model.ResolveQuestionBO

interface IMultiModalLanguageModelRepository {

    @Throws(ResolveQuestionFromContextException::class)
    suspend fun resolveQuestion(data: ResolveQuestionBO): String

    @Throws(GenerateImageDescriptionException::class)
    suspend fun generateImageDescription(imageUrl: String): String
}