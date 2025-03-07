package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCase
import com.dreamsoftware.chicfit.domain.model.AuthUserBO
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

/**
 * VerifyUserSessionUseCase
 * @param userRepository
 */
class VerifyUserSessionUseCase(
    private val userRepository: IUserRepository
) : BrownieUseCase<AuthUserBO>() {

    override suspend fun onExecuted(): AuthUserBO = userRepository.getCurrentAuthenticatedUser()
}