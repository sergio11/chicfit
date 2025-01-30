package com.dreamsoftware.chicfit.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onGoToDetail: (String) -> Unit,
    onGoToChat: (String) -> Unit
) {
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { HomeUiState() },
        onInit = { loadData() },
        onSideEffect = {
            when(it) {
                is HomeSideEffects.OpenArtworkChat -> onGoToChat(it.id)
                is HomeSideEffects.OpenArtworkDetail -> onGoToDetail(it.id)
            }
        }
    ) { uiState ->
        HomeScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}