package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

class GetAllOutfitsByUserUseCase(
    private val userRepository: IUserRepository,
    private val outfitRepository: IOutfitRepository
) : BrownieUseCase<List<OutfitBO>>() {

    override suspend fun onExecuted(): List<OutfitBO> =
        outfitRepository.fetchAllByUserId(userId = userRepository.getUserAuthenticatedUid())
}