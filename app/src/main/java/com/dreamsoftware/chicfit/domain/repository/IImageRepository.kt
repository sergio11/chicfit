package com.dreamsoftware.chicfit.domain.repository

import com.dreamsoftware.chicfit.domain.exception.DeletePictureException
import com.dreamsoftware.chicfit.domain.exception.SavePictureException

interface IImageRepository {

    @Throws(SavePictureException::class)
    suspend fun save(path: String, name: String): String

    @Throws(DeletePictureException::class)
    suspend fun deleteByName(name: String)
}