package com.dreamsoftware.chicfit.ui.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dreamsoftware.chicfit.ui.screens.chat.ChatScreenArgs
import com.dreamsoftware.chicfit.ui.screens.detail.ArtworkDetailScreenArgs

sealed class Screens(val route: String, arguments: List<NamedNavArgument> = emptyList()) {

    data object Splash: Screens("splash")
    data object Onboarding: Screens("onboarding")
    data object SignIn: Screens("sign_in")
    data object SignUp: Screens("sign_up")


    sealed class Main private constructor(route: String) : Screens(route) {
        companion object {
            const val route = "main"
            const val startDestination = Home.route
        }

        sealed class Home private constructor(route: String) : Screens(route) {
            companion object {
                const val route = "home"
                val startDestination = Info.route
            }
            data object Info : Screens("info")
            data object CreateArtwork : Screens("CreateArtwork")
            data object Settings: Screens("settings")
            data object Detail : Screens("detail/{artwork_id}", arguments = listOf(
                navArgument("artwork_id") {
                    type = NavType.StringType
                }
            )) {
                fun buildRoute(artworkId: String): String =
                    route.replace(
                        oldValue = "{artwork_id}",
                        newValue = artworkId
                    )

                fun parseArgs(args: Bundle): ArtworkDetailScreenArgs? = with(args) {
                    getString("artwork_id")?.let {
                        ArtworkDetailScreenArgs(id = it)
                    }
                }
            }
            data object Chat : Screens("chat/{artwork_id}", arguments = listOf(
                navArgument("artwork_id") {
                    type = NavType.StringType
                }
            )) {
                fun buildRoute(artworkId: String): String =
                    route.replace(
                        oldValue = "{artwork_id}",
                        newValue = artworkId
                    )

                fun parseArgs(args: Bundle): ChatScreenArgs? = with(args) {
                    getString("artwork_id")?.let {
                        ChatScreenArgs(id = it)
                    }
                }
            }
        }
    }
}