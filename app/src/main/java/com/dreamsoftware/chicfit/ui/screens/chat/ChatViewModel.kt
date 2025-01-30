package com.dreamsoftware.chicfit.ui.screens.chat

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.chicfit.di.ChatErrorMapper
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.model.ArtworkMessageBO
import com.dreamsoftware.chicfit.domain.usecase.AddArtworkMessageUseCase
import com.dreamsoftware.chicfit.domain.usecase.EndUserSpeechCaptureUseCase
import com.dreamsoftware.chicfit.domain.usecase.GetAssistantMutedStatusUseCase
import com.dreamsoftware.chicfit.domain.usecase.GetArtworkByIdUseCase
import com.dreamsoftware.chicfit.domain.usecase.StopTextToSpeechUseCase
import com.dreamsoftware.chicfit.domain.usecase.TextToSpeechUseCase
import com.dreamsoftware.chicfit.domain.usecase.TranscribeUserQuestionUseCase
import com.dreamsoftware.chicfit.domain.usecase.UpdateAssistantMutedStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getArtworkByIdUseCase: GetArtworkByIdUseCase,
    private val transcribeUserQuestionUseCase: TranscribeUserQuestionUseCase,
    private val endUserSpeechCaptureUseCase: EndUserSpeechCaptureUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase,
    private val stopTextToSpeechUseCase: StopTextToSpeechUseCase,
    private val addArtworkMessageUseCase: AddArtworkMessageUseCase,
    private val updateAssistantMutedStatusUseCase: UpdateAssistantMutedStatusUseCase,
    private val getAssistantMutedStatusUseCase: GetAssistantMutedStatusUseCase,
    @ChatErrorMapper private val errorMapper: IBrownieErrorMapper
) : BrownieViewModel<ChatUiState, ChatSideEffects>(), ChatScreenActionListener {

    fun load(id: String) {
        executeUseCase(
            useCase = getAssistantMutedStatusUseCase,
            onSuccess = ::onGetAssistantMutedStatusCompleted,
            showLoadingState = false
        )
        executeUseCaseWithParams(
            useCase = getArtworkByIdUseCase,
            params = GetArtworkByIdUseCase.Params(id = id),
            onSuccess = ::onGetArtworkCompletedSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onGetDefaultState(): ChatUiState = ChatUiState()

    override fun onAssistantMutedChange(isMuted: Boolean) {
        updateState { it.copy(isAssistantMuted = isMuted) }
        executeUseCaseWithParams(
            useCase = updateAssistantMutedStatusUseCase,
            params = UpdateAssistantMutedStatusUseCase.Params(isAssistantMuted = isMuted),
            showLoadingState = false
        )
        if (isMuted) {
            stopSpeaking()
        }
    }

    override fun onAssistantSpeechStopped() {
        if (uiState.value.isAssistantSpeaking) {
            stopSpeaking()
        }
    }

    override fun onStartListening() {
        if(uiState.value.isListening) {
            onStopTranscription()
        } else {
            onTranscribeUserQuestion()
        }
    }

    override fun onBackButtonClicked() {
        launchSideEffect(ChatSideEffects.CloseChat)
    }

    override fun onCleared() {
        doOnUiState {
            if(isAssistantSpeaking) {
                stopSpeaking()
            }
            if(isListening) {
                onStopTranscription()
            }
        }
        super.onCleared()
    }

    private fun onTranscribeUserQuestion() {
        updateState { it.copy(isListening = true) }
        executeUseCase(
            useCase = transcribeUserQuestionUseCase,
            onSuccess = ::onListenForTranscriptionCompleted,
            onMapExceptionToState = ::onMapExceptionToState,
            showLoadingState = false
        )
    }

    private fun onStopTranscription() {
        executeUseCase(
            useCase = endUserSpeechCaptureUseCase,
            onSuccess = { updateState { it.copy(isListening = false) } },
            onMapExceptionToState = ::onMapExceptionToState,
            showLoadingState = false
        )
    }

    private fun onListenForTranscriptionCompleted(transcription: String) {
        updateState { it.copy(isListening = false) }
        executeUseCaseWithParams(
            useCase = addArtworkMessageUseCase,
            params = AddArtworkMessageUseCase.Params(
                artworkId = uiState.value.artworkId,
                question = transcription
            ),
            onSuccess = ::onGetArtworkCompletedSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onGetArtworkCompletedSuccessfully(artworkBO: ArtworkBO) {
        updateState { it.copy(artworkId = artworkBO.uid, messageList = artworkBO.messages) }
        doOnUiState {
            if(!isAssistantMuted) {
                speakMessage(text = artworkBO.messages.last().text)
            }
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: ChatUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    private fun speakMessage(text: String) {
        updateState { it.copy(isAssistantSpeaking = true) }
        executeUseCaseWithParams(
            useCase = textToSpeechUseCase,
            params = TextToSpeechUseCase.Params(text),
            onMapExceptionToState = ::onMapExceptionToState,
            onSuccess = {
                updateState { it.copy(isAssistantSpeaking = false) }
            },
            showLoadingState = false
        )
    }

    private fun stopSpeaking() {
        executeUseCase(useCase = stopTextToSpeechUseCase)
        updateState { it.copy(isAssistantSpeaking = false) }
    }

    private fun onGetAssistantMutedStatusCompleted(isMuted: Boolean) {
        updateState { it.copy(isAssistantMuted = isMuted) }
    }
}


data class ChatUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val artworkId: String = String.EMPTY,
    val infoMessage: String = String.EMPTY,
    val isAssistantResponseLoading: Boolean = false,
    val isAssistantMuted: Boolean = false,
    val isAssistantSpeaking: Boolean = false,
    val isListening: Boolean = false,
    val lastQuestion: String = String.EMPTY,
    val messageList: List<ArtworkMessageBO> = emptyList()
): UiState<ChatUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): ChatUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}


sealed interface ChatSideEffects: SideEffect {
    data object CloseChat: ChatSideEffects
}

