package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.chicfit.domain.repository.IImageRepository
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class DeleteOutfitByIdUseCase(
    private val userRepository: IUserRepository,
    private val imageRepository: IImageRepository,
    private val outfitRepository: IOutfitRepository
) : BrownieUseCaseWithParams<DeleteOutfitByIdUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params): Unit = with(params) {
        val userId = userRepository.getUserAuthenticatedUid()
        imageRepository.deleteByName(id)
        outfitRepository.deleteById(userId = userId, id = id)
    }

    data class Params(
        val id: String,
    )
}