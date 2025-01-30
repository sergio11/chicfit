package com.dreamsoftware.chicfit

import android.app.Application
import com.dreamsoftware.brownie.utils.IBrownieAppEvent
import com.dreamsoftware.chicfit.utils.IApplicationAware
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChicFitApplication : Application(), IApplicationAware

sealed interface AppEvent: IBrownieAppEvent {
    data object SignOff: AppEvent
}