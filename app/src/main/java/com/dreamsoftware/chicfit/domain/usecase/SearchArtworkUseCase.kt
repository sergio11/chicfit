package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class SearchArtworkUseCase(
    private val userRepository: IUserRepository,
    private val artworkRepository: IOutfitRepository,
) : BrownieUseCaseWithParams<SearchArtworkUseCase.Params, List<OutfitBO>>() {

    override suspend fun onExecuted(params: Params): List<OutfitBO> = with(params) {
        val userUid = userRepository.getUserAuthenticatedUid()
        artworkRepository.search(userUid, term)
    }

    data class Params(
        val term: String
    )
}