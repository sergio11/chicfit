package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class SearchOutfitUseCase(
    private val userRepository: IUserRepository,
    private val outfitRepository: IOutfitRepository,
) : BrownieUseCaseWithParams<SearchOutfitUseCase.Params, List<OutfitBO>>() {

    override suspend fun onExecuted(params: Params): List<OutfitBO> = with(params) {
        val userUid = userRepository.getUserAuthenticatedUid()
        outfitRepository.search(userUid, term)
    }

    data class Params(
        val term: String
    )
}