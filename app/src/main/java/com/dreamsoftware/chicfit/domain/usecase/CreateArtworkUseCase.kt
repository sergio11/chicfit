package com.dreamsoftware.chicfit.domain.usecase

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.model.ResolveQuestionBO
import com.dreamsoftware.chicfit.domain.model.CreateArtworkBO
import com.dreamsoftware.chicfit.domain.repository.IImageRepository
import com.dreamsoftware.chicfit.domain.repository.IArtworkRepository
import com.dreamsoftware.chicfit.domain.repository.IMultiModalLanguageModelRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository
import java.util.UUID

/**
 * Use case for creating a new Artwork entry.
 * This involves saving an image, generating an answer using a multi-modal language model,
 * and then creating an Artwork record with the generated data.
 *
 * @param userRepository Repository for user-related operations.
 * @param imageRepository Repository for image-related operations.
 * @param artworkRepository Repository for Artwork records.
 * @param multiModalLanguageModelRepository Repository for multi-modal language model interactions.
 */
class CreateArtworkUseCase(
    private val userRepository: IUserRepository,
    private val imageRepository: IImageRepository,
    private val artworkRepository: IArtworkRepository,
    private val multiModalLanguageModelRepository: IMultiModalLanguageModelRepository
) : BrownieUseCaseWithParams<CreateArtworkUseCase.Params, ArtworkBO>() {

    /**
     * Executes the use case to create a new artwork record.
     *
     * @param params Parameters containing the image URL and the user's question.
     * @return The newly created artwork business object (ArtworkBO).
     */
    override suspend fun onExecuted(params: Params): ArtworkBO = with(params) {
        // Generate a unique ID for the artwork entry
        val artworkId = UUID.randomUUID().toString()

        // Save the image and get the new image URL
        val newImageUrl = imageRepository.save(path = imageUrl, name = artworkId)

        // Generate a description for the image
        val imageDescription = multiModalLanguageModelRepository.generateImageDescription(newImageUrl)

        // Prepare the question for resolution
        val resolveQuestion = ResolveQuestionBO(
            context = imageDescription,
            question = question
        )

        // Resolve the question
        val answer = multiModalLanguageModelRepository.resolveQuestion(resolveQuestion)

        // Get the authenticated user's ID
        val userId = userRepository.getUserAuthenticatedUid()

        // Create the Artwork entry
        val artworkBO = CreateArtworkBO(
            uid = artworkId,
            userId = userId,
            imageUrl = newImageUrl,
            imageDescription = imageDescription,
            question = question,
            answer = answer
        )

        // Save the artwork entry
        artworkRepository.create(artworkBO)
    }

    /**
     * Data class representing the parameters for the use case.
     *
     * @property imageUrl The URL of the image to be processed.
     * @property question The question asked by the user.
     */
    data class Params(
        val imageUrl: String,
        val question: String
    )
}