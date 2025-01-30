package com.dreamsoftware.chicfit.domain.repository

import com.dreamsoftware.chicfit.domain.exception.AddArtworkMessageException
import com.dreamsoftware.chicfit.domain.exception.DeleteArtworkByIdException
import com.dreamsoftware.chicfit.domain.exception.FetchAllArtworkException
import com.dreamsoftware.chicfit.domain.exception.FetchArtworkByIdException
import com.dreamsoftware.chicfit.domain.exception.SaveArtworkException
import com.dreamsoftware.chicfit.domain.exception.SearchArtworkException
import com.dreamsoftware.chicfit.domain.model.AddArtworkMessageBO
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.model.CreateArtworkBO

interface IArtworkRepository {

    @Throws(SearchArtworkException::class)
    suspend fun search(userId: String, term: String): List<ArtworkBO>

    @Throws(SaveArtworkException::class)
    suspend fun create(data: CreateArtworkBO): ArtworkBO

    @Throws(AddArtworkMessageException::class)
    suspend fun addMessage(data: AddArtworkMessageBO): ArtworkBO

    @Throws(DeleteArtworkByIdException::class)
    suspend fun deleteById(userId: String, id: String)

    @Throws(FetchArtworkByIdException::class)
    suspend fun fetchById(userId: String, id: String): ArtworkBO

    @Throws(FetchAllArtworkException::class)
    suspend fun fetchAllByUserId(userId: String): List<ArtworkBO>
}