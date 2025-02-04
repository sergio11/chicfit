package com.dreamsoftware.chicfit.domain.repository

import com.dreamsoftware.chicfit.domain.exception.AddOutfitMessageException
import com.dreamsoftware.chicfit.domain.exception.DeleteOutfitByIdException
import com.dreamsoftware.chicfit.domain.exception.FetchAllOutfitException
import com.dreamsoftware.chicfit.domain.exception.FetchOutfitByIdException
import com.dreamsoftware.chicfit.domain.exception.SaveOutfitException
import com.dreamsoftware.chicfit.domain.exception.SearchOutfitException
import com.dreamsoftware.chicfit.domain.model.AddMessageBO
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.model.CreateOutfitBO

interface IOutfitRepository {

    @Throws(SearchOutfitException::class)
    suspend fun search(userId: String, term: String): List<OutfitBO>

    @Throws(SaveOutfitException::class)
    suspend fun create(data: CreateOutfitBO): OutfitBO

    @Throws(AddOutfitMessageException::class)
    suspend fun addMessage(data: AddMessageBO): OutfitBO

    @Throws(DeleteOutfitByIdException::class)
    suspend fun deleteById(userId: String, id: String)

    @Throws(FetchOutfitByIdException::class)
    suspend fun fetchById(userId: String, id: String): OutfitBO

    @Throws(FetchAllOutfitException::class)
    suspend fun fetchAllByUserId(userId: String): List<OutfitBO>
}