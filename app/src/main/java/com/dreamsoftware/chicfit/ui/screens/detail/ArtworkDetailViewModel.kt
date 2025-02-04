package com.dreamsoftware.chicfit.ui.screens.detail

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.chicfit.di.ArtworkDetailErrorMapper
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.usecase.DeleteArtworkByIdUseCase
import com.dreamsoftware.chicfit.domain.usecase.GetArtworkByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtworkDetailViewModel @Inject constructor(
    private val getArtworkByIdUseCase: GetArtworkByIdUseCase,
    private val deleteArtworkByIdUseCase: DeleteArtworkByIdUseCase,
    @ArtworkDetailErrorMapper private val errorMapper: IBrownieErrorMapper
) : BrownieViewModel<ArtworkDetailUiState, ArtworkDetailSideEffects>(), ArtworkDetailScreenActionListener {

    fun load(id: String) {
        executeUseCaseWithParams(
            useCase = getArtworkByIdUseCase,
            params = GetArtworkByIdUseCase.Params(id = id),
            onSuccess = ::onLoadArtworkDetailCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onGetDefaultState(): ArtworkDetailUiState = ArtworkDetailUiState()

    private fun onLoadArtworkDetailCompleted(outfitBO: OutfitBO) {
        updateState {
            with(outfitBO) {
                it.copy(
                    uid = uid,
                    imageUrl = imageUrl,
                    title = question,
                    description = imageDescription
                )
            }
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: ArtworkDetailUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    override fun onBackPressed() {
        launchSideEffect(ArtworkDetailSideEffects.CloseDetail)
    }

    override fun onOpenChatClicked() {
        launchSideEffect(ArtworkDetailSideEffects.OpenArtworkChat)
    }

    override fun onArtworkDeleted() {
        updateState { it.copy(showDeleteArtworkDialog = true) }
    }

    override fun onDeleteConfirmed() {
        updateState { it.copy(showDeleteArtworkDialog = false) }
        executeUseCaseWithParams(
            useCase = deleteArtworkByIdUseCase,
            params = DeleteArtworkByIdUseCase.Params(id = uiState.value.uid),
            onSuccess = { onDeleteArtworkCompleted() },
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onDeleteCancelled() {
        updateState { it.copy(showDeleteArtworkDialog = false) }
    }

    private fun onDeleteArtworkCompleted() {
        launchSideEffect(ArtworkDetailSideEffects.ArtworkDeleted)
    }
}

data class ArtworkDetailUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val showDeleteArtworkDialog: Boolean = false,
    val infoMessage: String = String.EMPTY,
    val uid: String = String.EMPTY,
    val imageUrl: String = String.EMPTY,
    val title: String = String.EMPTY,
    val description: String = String.EMPTY
) : UiState<ArtworkDetailUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): ArtworkDetailUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface ArtworkDetailSideEffects : SideEffect {
    data object CloseDetail: ArtworkDetailSideEffects
    data object ArtworkDeleted: ArtworkDetailSideEffects
    data object OpenArtworkChat: ArtworkDetailSideEffects
}