package com.dreamsoftware.chicfit.data.remote.datasource

import com.dreamsoftware.chicfit.data.remote.dto.AddMessageDTO
import com.dreamsoftware.chicfit.data.remote.dto.CreateOutfitDTO
import com.dreamsoftware.chicfit.data.remote.dto.OutfitDTO
import com.dreamsoftware.chicfit.data.remote.exception.AddOutfitMessageRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.DeleteOutfitByIdRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.FetchAllOutfitRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.FetchOutfitByIdRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.CreateOutfitRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.SearchOutfitRemoteDataException

interface IOutfitDataSource {

    @Throws(SearchOutfitRemoteDataException::class)
    suspend fun search(userId: String, term: String): List<OutfitDTO>

    @Throws(CreateOutfitRemoteDataException::class)
    suspend fun create(data: CreateOutfitDTO)

    @Throws(AddOutfitMessageRemoteDataException::class)
    suspend fun addMessage(data: AddMessageDTO)

    @Throws(FetchOutfitByIdRemoteDataException::class)
    suspend fun fetchById(userId: String, id: String): OutfitDTO

    @Throws(FetchAllOutfitRemoteDataException::class)
    suspend fun fetchAllByUserId(userId: String): List<OutfitDTO>

    @Throws(DeleteOutfitByIdRemoteDataException::class)
    suspend fun deleteById(userId: String, id: String)
}