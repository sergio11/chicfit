package com.dreamsoftware.chicfit.ui.screens.home

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.chicfit.di.HomeErrorMapper
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.usecase.DeleteArtworkByIdUseCase
import com.dreamsoftware.chicfit.domain.usecase.GetAllArtworksByUserUseCase
import com.dreamsoftware.chicfit.domain.usecase.SearchArtworkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllArtworksByUserUseCase: GetAllArtworksByUserUseCase,
    private val deleteArtworkByIdUseCase: DeleteArtworkByIdUseCase,
    private val searchArtworkUseCase: SearchArtworkUseCase,
    @HomeErrorMapper private val errorMapper: IBrownieErrorMapper
) : BrownieViewModel<HomeUiState, HomeSideEffects>(), HomeScreenActionListener {

    fun loadData() {
        onLoadData()
    }

    override fun onGetDefaultState(): HomeUiState = HomeUiState()

    override fun onArtworkClicked(artworkBO: ArtworkBO) {
        launchSideEffect(HomeSideEffects.OpenArtworkChat(artworkBO.uid))
    }

    override fun onArtworkDetailClicked(artworkBO: ArtworkBO) {
        launchSideEffect(HomeSideEffects.OpenArtworkDetail(artworkBO.uid))
    }

    override fun onSearchQueryUpdated(newSearchQuery: String) {
        updateState { it.copy(searchQuery = newSearchQuery) }
        onLoadData()
    }

    override fun onArtworkDeleted(artworkBO: ArtworkBO) {
        updateState { it.copy(confirmDeleteArtwork = artworkBO) }
    }

    override fun onDeleteArtworkConfirmed() {
        doOnUiState {
            confirmDeleteArtwork?.let { artwork ->
                executeUseCaseWithParams(
                    useCase = deleteArtworkByIdUseCase,
                    params = DeleteArtworkByIdUseCase.Params(
                        id = artwork.uid
                    ),
                    onSuccess = {
                        onDeleteArtworkCompleted(artwork)
                    },
                    onMapExceptionToState = ::onMapExceptionToState
                )
            }
            updateState { it.copy(confirmDeleteArtwork = null) }
        }
    }

    override fun onDeleteArtworkCancelled() {
        updateState { it.copy(confirmDeleteArtwork = null) }
    }

    override fun onInfoMessageCleared() {
        updateState { it.copy(infoMessage = null) }
    }

    private fun onDeleteArtworkCompleted(artwork: ArtworkBO) {
        updateState { it.copy(artworkList = it.artworkList.filter { iq -> iq.uid != artwork.uid }) }
    }

    private fun onLoadArtworkCompleted(data: List<ArtworkBO>) {
        updateState {
            it.copy(artworkList = data)
        }
    }

    private fun onLoadData() {
        doOnUiState {
            if(searchQuery.isEmpty()) {
                executeUseCase(
                    useCase = getAllArtworksByUserUseCase,
                    onSuccess = ::onLoadArtworkCompleted,
                    onMapExceptionToState = ::onMapExceptionToState
                )
            } else {
                executeUseCaseWithParams(
                    useCase = searchArtworkUseCase,
                    params = SearchArtworkUseCase.Params(term = searchQuery),
                    onSuccess = ::onLoadArtworkCompleted,
                    onMapExceptionToState = ::onMapExceptionToState
                )
            }
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: HomeUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class HomeUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val confirmDeleteArtwork: ArtworkBO? = null,
    val infoMessage: String? = null,
    val artworkList: List<ArtworkBO> = emptyList(),
    val searchQuery: String = String.EMPTY
): UiState<HomeUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): HomeUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}


sealed interface HomeSideEffects: SideEffect {
    data class OpenArtworkDetail(val id: String): HomeSideEffects
    data class OpenArtworkChat(val id: String): HomeSideEffects
}