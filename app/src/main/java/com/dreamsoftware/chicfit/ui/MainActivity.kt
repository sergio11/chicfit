package com.dreamsoftware.chicfit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dreamsoftware.brownie.utils.network.BrownieNetworkConnectivityMonitor
import com.dreamsoftware.chicfit.ui.screens.app.AppScreen
import com.dreamsoftware.chicfit.ui.theme.ArtifyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkConnectivityMonitor: BrownieNetworkConnectivityMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Complying with Android 12 Splash Screen guidelines
        super.onCreate(savedInstanceState)
        networkConnectivityMonitor.registerNetworkCallback()
        setContent {
            ArtifyTheme {
                AppScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkConnectivityMonitor.unregisterNetworkCallback()
    }
}
