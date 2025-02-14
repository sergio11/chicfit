package com.dreamsoftware.chicfit.ui.screens.create

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface CreateOutfitScreenActionListener: IBrownieScreenActionListener {
    fun onStartListening()
    fun onUpdateQuestion(newQuestion: String)
    fun onCreate()
    fun onCancel()
}