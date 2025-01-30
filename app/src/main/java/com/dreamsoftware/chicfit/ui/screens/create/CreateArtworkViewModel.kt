package com.dreamsoftware.chicfit.ui.screens.create

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.chicfit.di.CreateArtworkErrorMapper
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.usecase.CreateArtworkUseCase
import com.dreamsoftware.chicfit.domain.usecase.TranscribeUserQuestionUseCase
import com.dreamsoftware.chicfit.domain.usecase.EndUserSpeechCaptureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateArtworkViewModel @Inject constructor(
    private val transcribeUserQuestionUseCase: TranscribeUserQuestionUseCase,
    private val endUserSpeechCaptureUseCase: EndUserSpeechCaptureUseCase,
    private val createArtworkUseCase: CreateArtworkUseCase,
    @CreateArtworkErrorMapper private val errorMapper: IBrownieErrorMapper
) : BrownieViewModel<CreateArtworkUiState, CreateArtworkSideEffects>(), CreateArtworkScreenActionListener {

    companion object {
        private const val SHOW_CONFIRM_SCREEN_DELAY = 2000L
    }

    override fun onGetDefaultState(): CreateArtworkUiState = CreateArtworkUiState()

    fun onTranscribeUserQuestion(imageUrl: String) {
        updateState { it.copy(isListening = true, question = String.EMPTY, imageUrl = imageUrl) }
        executeUseCase(
            useCase = transcribeUserQuestionUseCase,
            onSuccess = ::onListenForTranscriptionCompleted,
            onMapExceptionToState = ::onMapExceptionToState,
            showLoadingState = false
        )
    }

    fun onCancelUserQuestion() {
        onResetState()
    }

    override fun onStartListening() {
        if(uiState.value.isListening) {
            onStopTranscription()
        } else {
            launchSideEffect(CreateArtworkSideEffects.StartListening)
        }
    }

    override fun onUpdateQuestion(newQuestion: String) {
        updateState { it.copy(question = newQuestion) }
    }

    override fun onCreate() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = createArtworkUseCase,
                params = CreateArtworkUseCase.Params(imageUrl = imageUrl, question = question),
                onSuccess = ::onArtworkCreatedSuccessfully,
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
    }

    override fun onCancel() {
        onResetState()
    }

    private fun onStopTranscription() {
        executeUseCase(
            useCase = endUserSpeechCaptureUseCase,
            onSuccess = { onResetState() },
            onMapExceptionToState = ::onMapExceptionToState,
            showLoadingState = false
        )
    }

    private fun onListenForTranscriptionCompleted(transcription: String) {
        viewModelScope.launch {
            updateState { it.copy(isListening = false, question = transcription) }
            delay(SHOW_CONFIRM_SCREEN_DELAY)
            updateState { it.copy(showConfirm = true) }
        }
    }

    private fun onResetState() {
        updateState {
            it.copy(
                isListening = false,
                question = String.EMPTY,
                imageUrl = String.EMPTY,
                showConfirm = false
            )
        }
    }

    private fun onArtworkCreatedSuccessfully(data: ArtworkBO) {
        onResetState()
        launchSideEffect(CreateArtworkSideEffects.ArtworkCreated(data.uid))
    }

    private fun onMapExceptionToState(ex: Exception, uiState: CreateArtworkUiState) =
        uiState.copy(
            isLoading = false,
            isListening = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class CreateArtworkUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val showConfirm: Boolean = false,
    val infoMessage: String = String.EMPTY,
    val isListening: Boolean = false,
    val imageUrl: String = String.EMPTY,
    val question: String = String.EMPTY
) : UiState<CreateArtworkUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): CreateArtworkUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface CreateArtworkSideEffects : SideEffect {
    data object StartListening: CreateArtworkSideEffects
    data class ArtworkCreated(val id: String): CreateArtworkSideEffects
}