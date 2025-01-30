package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.repository.IArtworkRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class GetArtworkByIdUseCase(
    private val userRepository: IUserRepository,
    private val artworkRepository: IArtworkRepository
) : BrownieUseCaseWithParams<GetArtworkByIdUseCase.Params, ArtworkBO>() {

    override suspend fun onExecuted(params: Params): ArtworkBO {
        val userId = userRepository.getUserAuthenticatedUid()
        return artworkRepository.fetchById(userId = userId, id = params.id)
    }

    data class Params(val id: String)
}