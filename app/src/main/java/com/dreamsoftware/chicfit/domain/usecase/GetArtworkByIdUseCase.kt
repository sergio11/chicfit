package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class GetArtworkByIdUseCase(
    private val userRepository: IUserRepository,
    private val artworkRepository: IOutfitRepository
) : BrownieUseCaseWithParams<GetArtworkByIdUseCase.Params, OutfitBO>() {

    override suspend fun onExecuted(params: Params): OutfitBO {
        val userId = userRepository.getUserAuthenticatedUid()
        return artworkRepository.fetchById(userId = userId, id = params.id)
    }

    data class Params(val id: String)
}