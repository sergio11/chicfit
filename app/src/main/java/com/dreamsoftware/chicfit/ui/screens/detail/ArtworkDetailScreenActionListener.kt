package com.dreamsoftware.chicfit.ui.screens.detail

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface ArtworkDetailScreenActionListener: IBrownieScreenActionListener {
    fun onBackPressed()
    fun onOpenChatClicked()
    fun onArtworkDeleted()
    fun onDeleteConfirmed()
    fun onDeleteCancelled()
}