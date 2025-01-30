package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.repository.IArtworkRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class SearchArtworkUseCase(
    private val userRepository: IUserRepository,
    private val artworkRepository: IArtworkRepository,
) : BrownieUseCaseWithParams<SearchArtworkUseCase.Params, List<ArtworkBO>>() {

    override suspend fun onExecuted(params: Params): List<ArtworkBO> = with(params) {
        val userUid = userRepository.getUserAuthenticatedUid()
        artworkRepository.search(userUid, term)
    }

    data class Params(
        val term: String
    )
}