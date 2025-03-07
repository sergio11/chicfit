package com.dreamsoftware.chicfit.ui.screens.detail

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener

interface OutfitDetailScreenActionListener: IBrownieScreenActionListener {
    fun onBackPressed()
    fun onOpenChatClicked()
    fun onOutfitDeleted()
    fun onDeleteConfirmed()
    fun onDeleteCancelled()
}