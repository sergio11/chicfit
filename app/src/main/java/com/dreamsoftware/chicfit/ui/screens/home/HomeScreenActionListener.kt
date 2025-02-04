package com.dreamsoftware.chicfit.ui.screens.home

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener
import com.dreamsoftware.chicfit.domain.model.OutfitBO

interface HomeScreenActionListener: IBrownieScreenActionListener {
    fun onArtworkClicked(outfitBO: OutfitBO)
    fun onArtworkDetailClicked(outfitBO: OutfitBO)
    fun onSearchQueryUpdated(newSearchQuery: String)
    fun onArtworkDeleted(outfitBO: OutfitBO)
    fun onDeleteArtworkConfirmed()
    fun onDeleteArtworkCancelled()
}