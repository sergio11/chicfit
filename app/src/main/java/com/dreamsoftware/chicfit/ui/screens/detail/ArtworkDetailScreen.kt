package com.dreamsoftware.chicfit.ui.screens.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

data class ArtworkDetailScreenArgs(
    val id: String
)

@Composable
fun ArtworkDetailScreen(
    viewModel: ArtworkDetailViewModel = hiltViewModel(),
    args: ArtworkDetailScreenArgs,
    onGoToChat: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val scrollState: ScrollState = rememberScrollState(0)
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { ArtworkDetailUiState() },
        onSideEffect = {
            when(it) {
                ArtworkDetailSideEffects.CloseDetail -> onBackPressed()
                ArtworkDetailSideEffects.ArtworkDeleted -> onBackPressed()
                ArtworkDetailSideEffects.OpenArtworkChat -> onGoToChat(args.id)
            }
        },
        onInit = { load(id = args.id) }
    ) { uiState ->
        ArtworkDetailScreenContent(
            context = context,
            density = density,
            scrollState = scrollState,
            uiState = uiState,
            actionListener = viewModel
        )
    }
}
