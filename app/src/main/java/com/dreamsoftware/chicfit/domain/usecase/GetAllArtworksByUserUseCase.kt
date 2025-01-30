package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.repository.IArtworkRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class GetAllArtworksByUserUseCase(
    private val userRepository: IUserRepository,
    private val artworkRepository: IArtworkRepository
) : BrownieUseCase<List<ArtworkBO>>() {

    override suspend fun onExecuted(): List<ArtworkBO> =
        artworkRepository.fetchAllByUserId(userId = userRepository.getUserAuthenticatedUid())
}