package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class GetOutfitByIdUseCase(
    private val userRepository: IUserRepository,
    private val outfitRepository: IOutfitRepository
) : BrownieUseCaseWithParams<GetOutfitByIdUseCase.Params, OutfitBO>() {

    override suspend fun onExecuted(params: Params): OutfitBO {
        val userId = userRepository.getUserAuthenticatedUid()
        return outfitRepository.fetchById(userId = userId, id = params.id)
    }

    data class Params(val id: String)
}