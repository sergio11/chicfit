package com.dreamsoftware.chicfit.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.chicfit.data.remote.datasource.IOutfitDataSource
import com.dreamsoftware.chicfit.data.remote.dto.AddMessageDTO
import com.dreamsoftware.chicfit.data.remote.dto.CreateOutfitDTO
import com.dreamsoftware.chicfit.data.remote.dto.OutfitDTO
import com.dreamsoftware.chicfit.data.remote.exception.AddOutfitMessageRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.CreateOutfitRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.DeleteOutfitByIdRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.FetchAllOutfitRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.FetchOutfitByIdRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.SearchOutfitRemoteDataException
import com.dreamsoftware.chicfit.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.chicfit.domain.exception.AddOutfitMessageException
import com.dreamsoftware.chicfit.domain.exception.DeleteOutfitByIdException
import com.dreamsoftware.chicfit.domain.exception.FetchAllOutfitException
import com.dreamsoftware.chicfit.domain.exception.FetchOutfitByIdException
import com.dreamsoftware.chicfit.domain.exception.SaveOutfitException
import com.dreamsoftware.chicfit.domain.exception.SearchOutfitException
import com.dreamsoftware.chicfit.domain.model.AddMessageBO
import com.dreamsoftware.chicfit.domain.model.CreateOutfitBO
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import kotlinx.coroutines.CoroutineDispatcher

internal class OutfitRepositoryImpl(
    private val outfitDataSource: IOutfitDataSource,
    private val saveOutfitMapper: IBrownieOneSideMapper<CreateOutfitBO, CreateOutfitDTO>,
    private val addOutfitMapper: IBrownieOneSideMapper<AddMessageBO, AddMessageDTO>,
    private val outfitMapper: IBrownieOneSideMapper<OutfitDTO, OutfitBO>,
    dispatcher: CoroutineDispatcher
): SupportRepositoryImpl(dispatcher), IOutfitRepository {

    @Throws(SearchOutfitException::class)
    override suspend fun search(userId: String, term: String): List<OutfitBO> = safeExecute {
        try {
            outfitDataSource.search(userId, term)
                .let(outfitMapper::mapInListToOutList)
                .toList()
        } catch (ex: SearchOutfitRemoteDataException) {
            ex.printStackTrace()
            throw SearchOutfitException("An error occurred when searching content", ex)
        }
    }

    @Throws(SaveOutfitException::class)
    override suspend fun create(data: CreateOutfitBO): OutfitBO = safeExecute {
        try {
            with(outfitDataSource) {
                create(saveOutfitMapper.mapInToOut(data))
                outfitMapper.mapInToOut(fetchById(userId = data.userId, id = data.uid))
            }
        } catch (ex: CreateOutfitRemoteDataException) {
            ex.printStackTrace()
            throw SaveOutfitException("An error occurred when trying to save outfit", ex)
        }
    }

    @Throws(AddOutfitMessageException::class)
    override suspend fun addMessage(data: AddMessageBO): OutfitBO = safeExecute {
        try {
            with(outfitDataSource) {
                addMessage(addOutfitMapper.mapInToOut(data))
                outfitMapper.mapInToOut(fetchById(userId = data.userId, id = data.uid))
            }
        } catch (ex: AddOutfitMessageRemoteDataException) {
            ex.printStackTrace()
            throw AddOutfitMessageException("An error occurred when trying to save new message", ex)
        }
    }

    @Throws(DeleteOutfitByIdException::class)
    override suspend fun deleteById(userId: String, id: String) {
        safeExecute {
            try {
                outfitDataSource.deleteById(userId = userId, id = id)
            } catch (ex: DeleteOutfitByIdRemoteDataException) {
                ex.printStackTrace()
                throw DeleteOutfitByIdException("An error occurred when trying to delete the outfit", ex)
            }
        }
    }

    @Throws(FetchOutfitByIdException::class)
    override suspend fun fetchById(userId: String, id: String): OutfitBO = safeExecute {
        try {
            outfitDataSource.fetchById(userId = userId, id = id)
                .let(outfitMapper::mapInToOut)
        } catch (ex: FetchOutfitByIdRemoteDataException) {
            ex.printStackTrace()
            throw FetchOutfitByIdException("An error occurred when trying to fetch the outfit data", ex)
        }
    }

    @Throws(FetchAllOutfitException::class)
    override suspend fun fetchAllByUserId(userId: String): List<OutfitBO> = safeExecute {
        try {
            outfitDataSource.fetchAllByUserId(userId = userId)
                .let(outfitMapper::mapInListToOutList)
                .toList()
        } catch (ex: FetchAllOutfitRemoteDataException) {
            ex.printStackTrace()
            throw FetchAllOutfitException("An error occurred when trying to fetch all user questions", ex)
        }
    }
}