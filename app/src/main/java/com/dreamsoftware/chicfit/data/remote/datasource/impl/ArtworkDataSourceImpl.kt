package com.dreamsoftware.chicfit.data.remote.datasource.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.chicfit.data.remote.datasource.IArtworkDataSource
import com.dreamsoftware.chicfit.data.remote.datasource.impl.core.SupportDataSourceImpl
import com.dreamsoftware.chicfit.data.remote.dto.AddArtworkMessageDTO
import com.dreamsoftware.chicfit.data.remote.dto.CreateArtworkDTO
import com.dreamsoftware.chicfit.data.remote.dto.ArtworkDTO
import com.dreamsoftware.chicfit.data.remote.exception.AddArtworkMessageRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.CreateArtworkRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.DeleteArtworkByIdRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.FetchAllArtworkRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.FetchArtworkByIdRemoteDataException
import com.dreamsoftware.chicfit.data.remote.exception.SearchArtworkRemoteDataException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await

internal class ArtworkDataSourceImpl(
    private val firestore: FirebaseFirestore,
    private val saveArtworkMapper: IBrownieOneSideMapper<CreateArtworkDTO, Map<String, Any?>>,
    private val addArtworkMessageMapper: IBrownieOneSideMapper<AddArtworkMessageDTO, List<Map<String, String>>>,
    private val artworkMapper: IBrownieOneSideMapper<Map<String, Any?>, ArtworkDTO>,
    dispatcher: CoroutineDispatcher
): SupportDataSourceImpl(dispatcher), IArtworkDataSource {

    private companion object {
        const val COLLECTION_NAME = "user_artify"
        const val SUB_COLLECTION_NAME = "questions"
        const val MESSAGE_FIELD_NAME = "messages"
        const val IMAGE_DESCRIPTION_FIELD_NAME = "imageDescription"
        const val CREATED_AT_FIELD_NAME = "createdAt"
    }

    private val collection by lazy {
        firestore.collection(COLLECTION_NAME)
    }

    @Throws(SearchArtworkRemoteDataException::class)
    override suspend fun search(userId: String, term: String): List<ArtworkDTO> = safeExecution(
        onExecuted = {
            val snapshot = collection
                .document(userId)
                .collection(SUB_COLLECTION_NAME)
                .whereGreaterThanOrEqualTo(IMAGE_DESCRIPTION_FIELD_NAME, term)
                .whereLessThanOrEqualTo(IMAGE_DESCRIPTION_FIELD_NAME, term + "\uf8ff")
                .orderBy(CREATED_AT_FIELD_NAME, Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.documents.map { document ->
                artworkMapper.mapInToOut(
                    document.data ?: throw IllegalStateException("Document data is null")
                )
            }
        },
        onErrorOccurred = { ex ->
            SearchArtworkRemoteDataException("Failed to search questions", ex)
        }
    )

    @Throws(CreateArtworkRemoteDataException::class)
    override suspend fun create(data: CreateArtworkDTO): Unit = safeExecution(
        onExecuted = {
            collection
                .document(data.userId)
                .collection(SUB_COLLECTION_NAME)
                .document(data.uid)
                .set(saveArtworkMapper.mapInToOut(data))
                .await()
        },
        onErrorOccurred = { ex ->
            CreateArtworkRemoteDataException("Failed to save user question", ex)
        }
    )

    @Throws(AddArtworkMessageRemoteDataException::class)
    override suspend fun addMessage(data: AddArtworkMessageDTO): Unit = safeExecution(
        onExecuted = {
            val messages = addArtworkMessageMapper.mapInToOut(data)
            val documentRef = collection
                .document(data.userId)
                .collection(SUB_COLLECTION_NAME)
                .document(data.uid)
            for (message in messages) {
                documentRef.update(MESSAGE_FIELD_NAME, FieldValue.arrayUnion(message)).await()
            }
        },
        onErrorOccurred = { ex ->
            AddArtworkMessageRemoteDataException("Failed to save user question", ex)
        }
    )

    @Throws(FetchArtworkByIdRemoteDataException::class)
    override suspend fun fetchById(userId: String, id: String): ArtworkDTO =
        safeExecution(
            onExecuted = {
                val document = collection
                    .document(userId)
                    .collection(SUB_COLLECTION_NAME)
                    .document(id)
                    .get()
                    .await()
                artworkMapper.mapInToOut(
                    document.data ?: throw IllegalStateException("Document data is null")
                )
            },
            onErrorOccurred = { ex ->
                FetchArtworkByIdRemoteDataException(
                    "An error occurred when trying to fetch the user question with ID $id",
                    ex
                )
            }
        )

    @Throws(FetchAllArtworkRemoteDataException::class)
    override suspend fun fetchAllByUserId(userId: String): List<ArtworkDTO> = safeExecution(
        onExecuted = {
            val snapshot = collection
                .document(userId)
                .collection(SUB_COLLECTION_NAME)
                .orderBy(CREATED_AT_FIELD_NAME, Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.documents.map { document ->
                artworkMapper.mapInToOut(
                    document.data ?: throw IllegalStateException("Document data is null")
                )
            }
        },
        onErrorOccurred = { ex ->
            FetchAllArtworkRemoteDataException(
                "An error occurred when trying to fetch all user questions",
                ex
            )
        }
    )

    @Throws(DeleteArtworkByIdRemoteDataException::class)
    override suspend fun deleteById(userId: String, id: String): Unit = safeExecution(
        onExecuted = {
            collection
                .document(userId)
                .collection(SUB_COLLECTION_NAME)
                .document(id)
                .delete()
                .await()
        },
        onErrorOccurred = { ex ->
            DeleteArtworkByIdRemoteDataException(
                "An error occurred when trying to delete the user question",
                ex
            )
        }
    )
}