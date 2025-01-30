package com.dreamsoftware.chicfit.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.chicfit.data.remote.datasource.IArtworkDataSource
import com.dreamsoftware.chicfit.data.remote.dto.AddArtworkMessageDTO
import com.dreamsoftware.chicfit.data.remote.dto.CreateArtworkDTO
import com.dreamsoftware.chicfit.data.remote.dto.ArtworkDTO
import com.dreamsoftware.chicfit.data.remote.exception.AddArtworkMessageRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.CreateArtworkRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.DeleteArtworkByIdRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.FetchAllArtworkRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.FetchArtworkByIdRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.SearchArtworkRemoteDataException
import com.dreamsoftware.chicfit.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.chicfit.domain.exception.AddArtworkMessageException
import com.dreamsoftware.chicfit.domain.exception.DeleteArtworkByIdException
import com.dreamsoftware.chicfit.domain.exception.FetchAllArtworkException
import com.dreamsoftware.chicfit.domain.exception.FetchArtworkByIdException
import com.dreamsoftware.chicfit.domain.exception.SaveArtworkException
import com.dreamsoftware.chicfit.domain.exception.SearchArtworkException
import com.dreamsoftware.chicfit.domain.model.AddArtworkMessageBO
import com.dreamsoftware.chicfit.domain.model.CreateArtworkBO
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.repository.IArtworkRepository
import kotlinx.coroutines.CoroutineDispatcher

internal class ArtworkRepositoryImpl(
    private val artworkDataSource: IArtworkDataSource,
    private val saveArtworkMapper: IBrownieOneSideMapper<CreateArtworkBO, CreateArtworkDTO>,
    private val addArtworkMapper: IBrownieOneSideMapper<AddArtworkMessageBO, AddArtworkMessageDTO>,
    private val artworkMapper: IBrownieOneSideMapper<ArtworkDTO, ArtworkBO>,
    dispatcher: CoroutineDispatcher
): SupportRepositoryImpl(dispatcher), IArtworkRepository {

    @Throws(SearchArtworkException::class)
    override suspend fun search(userId: String, term: String): List<ArtworkBO> = safeExecute {
        try {
            artworkDataSource.search(userId, term)
                .let(artworkMapper::mapInListToOutList)
                .toList()
        } catch (ex: SearchArtworkRemoteDataException) {
            ex.printStackTrace()
            throw SearchArtworkException("An error occurred when searching content", ex)
        }
    }

    @Throws(SaveArtworkException::class)
    override suspend fun create(data: CreateArtworkBO): ArtworkBO = safeExecute {
        try {
            with(artworkDataSource) {
                create(saveArtworkMapper.mapInToOut(data))
                artworkMapper.mapInToOut(fetchById(userId = data.userId, id = data.uid))
            }
        } catch (ex: CreateArtworkRemoteDataException) {
            ex.printStackTrace()
            throw SaveArtworkException("An error occurred when trying to save artwork", ex)
        }
    }

    @Throws(AddArtworkMessageException::class)
    override suspend fun addMessage(data: AddArtworkMessageBO): ArtworkBO = safeExecute {
        try {
            with(artworkDataSource) {
                addMessage(addArtworkMapper.mapInToOut(data))
                artworkMapper.mapInToOut(fetchById(userId = data.userId, id = data.uid))
            }
        } catch (ex: AddArtworkMessageRemoteDataException) {
            ex.printStackTrace()
            throw AddArtworkMessageException("An error occurred when trying to save new message", ex)
        }
    }

    @Throws(DeleteArtworkByIdException::class)
    override suspend fun deleteById(userId: String, id: String) {
        safeExecute {
            try {
                artworkDataSource.deleteById(userId = userId, id = id)
            } catch (ex: DeleteArtworkByIdRemoteDataException) {
                ex.printStackTrace()
                throw DeleteArtworkByIdException("An error occurred when trying to delete the artwork", ex)
            }
        }
    }

    @Throws(FetchArtworkByIdException::class)
    override suspend fun fetchById(userId: String, id: String): ArtworkBO = safeExecute {
        try {
            artworkDataSource.fetchById(userId = userId, id = id)
                .let(artworkMapper::mapInToOut)
        } catch (ex: FetchArtworkByIdRemoteDataException) {
            ex.printStackTrace()
            throw FetchArtworkByIdException("An error occurred when trying to fetch the artwork data", ex)
        }
    }

    @Throws(FetchAllArtworkException::class)
    override suspend fun fetchAllByUserId(userId: String): List<ArtworkBO> = safeExecute {
        try {
            artworkDataSource.fetchAllByUserId(userId = userId)
                .let(artworkMapper::mapInListToOutList)
                .toList()
        } catch (ex: FetchAllArtworkRemoteDataException) {
            ex.printStackTrace()
            throw FetchAllArtworkException("An error occurred when trying to fetch all user questions", ex)
        }
    }
}