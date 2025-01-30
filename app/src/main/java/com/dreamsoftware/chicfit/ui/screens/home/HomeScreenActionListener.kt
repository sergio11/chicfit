package com.dreamsoftware.chicfit.ui.screens.home

import com.dreamsoftware.brownie.core.IBrownieScreenActionListener
import com.dreamsoftware.chicfit.domain.model.ArtworkBO

interface HomeScreenActionListener: IBrownieScreenActionListener {
    fun onArtworkClicked(artworkBO: ArtworkBO)
    fun onArtworkDetailClicked(artworkBO: ArtworkBO)
    fun onSearchQueryUpdated(newSearchQuery: String)
    fun onArtworkDeleted(artworkBO: ArtworkBO)
    fun onDeleteArtworkConfirmed()
    fun onDeleteArtworkCancelled()
}