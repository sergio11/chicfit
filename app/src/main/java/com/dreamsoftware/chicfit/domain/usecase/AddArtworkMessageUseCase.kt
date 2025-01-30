package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.chicfit.domain.model.AddArtworkMessageBO
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.model.ResolveQuestionBO
import com.dreamsoftware.chicfit.domain.repository.IArtworkRepository
import com.dreamsoftware.chicfit.domain.repository.IMultiModalLanguageModelRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository

/**
 * Use case for adding a new question to an existing artwork conversation.
 *
 * This use case is responsible for:
 * 1. Retrieving the currently authenticated user's ID.
 * 2. Fetching an existing Artwork conversation based on the provided artwork ID.
 * 3. Resolving the answer to the new question using a multi-modal language model.
 * 4. Adding the new question and its resolved answer to the Artwork conversation history.
 *
 * The use case interacts with the following repositories:
 * - `userRepository`: To retrieve the authenticated user's ID.
 * - `artworkRepository`: To fetch the artwork conversation by its ID and to add the new message to the conversation.
 * - `multiModalLanguageModelRepository`: To resolve the question using a multi-modal language model.
 */
class AddArtworkMessageUseCase(
    private val userRepository: IUserRepository,
    private val artworkRepository: IArtworkRepository,
    private val multiModalLanguageModelRepository: IMultiModalLanguageModelRepository
) : BrownieUseCaseWithParams<AddArtworkMessageUseCase.Params, ArtworkBO>() {

    /**
     * Executes the use case to add a new question to an existing Artwork conversation.
     *
     * @param params The parameters required for this use case, including the Artwork ID and the new question.
     * @return The updated Artwork business object containing the conversation history and the new message.
     * @throws Exception If any error occurs during the execution of the use case.
     */
    override suspend fun onExecuted(params: Params): ArtworkBO = with(params) {
        // Retrieve the authenticated user's ID
        val userId = userRepository.getUserAuthenticatedUid()

        // Fetch the existing Artwork conversation by ID
        val artwork = artworkRepository.fetchById(userId = userId, id = artworkId)

        // Resolve the answer to the new question using the multi-modal language model
        val answer = multiModalLanguageModelRepository.resolveQuestion(ResolveQuestionBO(
            context = artwork.imageDescription,
            question = question,
            history = artwork.messages.map { it.role.name to it.text }
        ))

        // Create a new Artwork message containing the question and the resolved answer
        val newArtworkMessage = AddArtworkMessageBO(
            uid = artworkId,
            userId = userId,
            question = question,
            answer = answer
        )

        // Add the new message to the Artwork conversation
        artworkRepository.addMessage(newArtworkMessage)
    }

    /**
     * Data class representing the parameters required to execute the AddArtworkQuestionUseCase.
     *
     * @property artworkId The ID of the Artwork conversation to which the new question will be added.
     * @property question The new question to be added to the conversation.
     */
    data class Params(
        val artworkId: String,
        val question: String
    )
}