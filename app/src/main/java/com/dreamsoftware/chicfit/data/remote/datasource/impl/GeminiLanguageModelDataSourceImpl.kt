package com.dreamsoftware.chicfit.data.remote.datasource.impl

import com.dreamsoftware.chicfit.data.remote.datasource.IMultiModalLanguageModelDataSource
import com.dreamsoftware.chicfit.data.remote.datasource.impl.core.SupportDataSourceImpl
import com.dreamsoftware.chicfit.data.remote.dto.ResolveQuestionDTO
import com.dreamsoftware.chicfit.data.remote.exception.GenerateImageDescriptionRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.ResolveQuestionFromContextRemoteDataException
import com.dreamsoftware.chicfit.utils.urlToBitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.CoroutineDispatcher

internal class GeminiLanguageModelDataSourceImpl(
    private val generativeTextModel: GenerativeModel,
    private val generativeMultiModalModel: GenerativeModel,
    private val dispatcher: CoroutineDispatcher
) : SupportDataSourceImpl(dispatcher), IMultiModalLanguageModelDataSource {

    private companion object {
        const val USER = "user"
    }

    @Throws(ResolveQuestionFromContextRemoteDataException::class)
    override suspend fun resolveQuestionFromContext(data: ResolveQuestionDTO): String = safeExecution(
        onExecuted = {
            val currentChatSession = generativeTextModel.startChat(data.history.map {
                content(it.role) { text(it.text) }
            })
            // Generate prompt combining the context and the user's question
            val prompt = generatePrompt(data.question, data.context)
            // Send the message and return the response
            currentChatSession.sendMessage(prompt).text ?: throw IllegalStateException("Answer couldn't be obtained")
        },
        onErrorOccurred = { ex ->
            ResolveQuestionFromContextRemoteDataException("An error occurred when trying to resolver user question", ex)
        }
    )

    /**
     * Generates a detailed description of the image located at the given [imageUrl].
     *
     * @param imageUrl The URL of the image for which a description needs to be generated.
     * @return A detailed description of the image, or `null` if an error occurs.
     */
    @Throws(GenerateImageDescriptionRemoteDataException::class)
    override suspend fun generateImageDescription(imageUrl: String): String = safeExecution(
        onExecuted = {
            val bitmap = imageUrl.urlToBitmap(dispatcher) ?: throw IllegalStateException("bitmap couldn't be obtained")
            generativeMultiModalModel.generateContent(content(USER) {
                image(bitmap)
                text("Provide a detailed description of the contents of the image.")
            }).text  ?: throw IllegalStateException("Description couldn't be obtained")
        },
        onErrorOccurred = { ex ->
            GenerateImageDescriptionRemoteDataException("An error occurred when trying to generate the image description", ex)
        }
    )

    private fun generatePrompt(question: String, imageDescription: String?): Content =
        content(USER) {
            imageDescription?.let {
                text(
                    """
                    |You are Artify, a knowledgeable and engaging art assistant. Your role is to provide insightful, 
                    |educational, and accessible explanations about artworks. You analyze and interpret pieces, 
                    |revealing their history, significance, techniques, and curiosities in a clear and engaging way.
                    |
                    |A user is presenting you with an artwork or its description and seeking your expertise. 
                    |Respond as if you are an art historian standing alongside the user, offering valuable insights 
                    |with clarity and enthusiasm. Avoid generic responses—focus on details that enhance understanding 
                    |and appreciation.
                    |
                    |Keep your answers strictly related to the artwork being discussed. Do not provide information 
                    |on unrelated topics, general conversations, or speculative discussions beyond the context of art. 
                    |Your responses should be precise, informative, and directly connected to the user's inquiry 
                    |about the artwork.
                    |
                    |Ensure your responses are structured, engaging, and maintain a professional yet accessible tone. 
                    |Do not mention that your insights are based on a description—speak as if you are directly 
                    |observing the artwork.
                """.trimMargin()
                )
                text(it)
            }
            text(question)
        }
}