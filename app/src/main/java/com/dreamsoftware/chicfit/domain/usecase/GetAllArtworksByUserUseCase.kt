package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class GetAllArtworksByUserUseCase(
    private val userRepository: IUserRepository,
    private val artworkRepository: IOutfitRepository
) : BrownieUseCase<List<OutfitBO>>() {

    override suspend fun onExecuted(): List<OutfitBO> =
        artworkRepository.fetchAllByUserId(userId = userRepository.getUserAuthenticatedUid())
}