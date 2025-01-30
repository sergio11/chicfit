package com.dreamsoftware.chicfit.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.chicfit.data.remote.datasource.IMultiModalLanguageModelDataSource
import com.dreamsoftware.chicfit.data.remote.dto.ResolveQuestionDTO
import com.dreamsoftware.chicfit.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.chicfit.domain.exception.GenerateImageDescriptionException
import com.dreamsoftware.chicfit.domain.exception.ResolveQuestionFromContextException
import com.dreamsoftware.chicfit.domain.model.ResolveQuestionBO
import com.dreamsoftware.chicfit.domain.repository.IMultiModalLanguageModelRepository
import kotlinx.coroutines.CoroutineDispatcher

internal class IMultiModalLanguageModelRepositoryImpl(
    private val multiModalLanguageModelDataSource: IMultiModalLanguageModelDataSource,
    private val resolveQuestionMapper: IBrownieOneSideMapper<ResolveQuestionBO, ResolveQuestionDTO>,
    dispatcher: CoroutineDispatcher
): SupportRepositoryImpl(dispatcher), IMultiModalLanguageModelRepository {

    @Throws(ResolveQuestionFromContextException::class)
    override suspend fun resolveQuestion(data: ResolveQuestionBO): String = safeExecute {
        multiModalLanguageModelDataSource.resolveQuestionFromContext(resolveQuestionMapper.mapInToOut(data))
    }

    @Throws(GenerateImageDescriptionException::class)
    override suspend fun generateImageDescription(imageUrl: String): String = safeExecute {
        multiModalLanguageModelDataSource.generateImageDescription(imageUrl)
    }
}