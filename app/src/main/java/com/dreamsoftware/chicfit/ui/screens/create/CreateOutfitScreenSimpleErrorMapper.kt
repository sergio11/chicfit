package com.dreamsoftware.chicfit.ui.screens.create

import android.content.Context
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.chicfit.R
import com.dreamsoftware.chicfit.domain.exception.InvalidDataException

class CreateOutfitScreenSimpleErrorMapper(
    private val context: Context
): IBrownieErrorMapper {
    override fun mapToMessage(ex: Throwable): String = context.getString(when(ex) {
        is InvalidDataException -> R.string.generic_form_invalid_data_provided
        else -> R.string.generic_error_exception
    })
}