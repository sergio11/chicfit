package com.dreamsoftware.chicfit.ui.screens.account.onboarding

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface OnboardingScreenActionListener: IBrownieScreenActionListener {
    fun onNavigateToSignIn()
    fun onNavigateToSignUp()
}