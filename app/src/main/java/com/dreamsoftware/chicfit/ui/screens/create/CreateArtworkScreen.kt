package com.dreamsoftware.chicfit.ui.screens.create

import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen
import com.dreamsoftware.chicfit.utils.takePicture

@Composable
fun CreateArtworkScreen(
    viewModel: CreateArtworkViewModel = hiltViewModel(),
    onGoToChat: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val cameraController = remember { LifecycleCameraController(context) }
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { CreateArtworkUiState() },
        onSideEffect = {
            when(it) {
                CreateArtworkSideEffects.StartListening -> {
                    cameraController.takePicture(
                        context = context,
                        onSuccess = {
                            onTranscribeUserQuestion(imageUrl = it)
                        },
                        onError = {
                            onCancelUserQuestion()
                        }
                    )
                }
                is CreateArtworkSideEffects.ArtworkCreated -> onGoToChat(it.id)
            }
        }
    ) { uiState ->
        CreateArtworkScreenContent(
            uiState = uiState,
            actionListener = viewModel,
            lifecycleCameraController = cameraController
        )
    }
}
