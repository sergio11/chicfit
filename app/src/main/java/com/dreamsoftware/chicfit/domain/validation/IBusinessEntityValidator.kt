package com.dreamsoftware.chicfit.domain.validation

interface IBusinessEntityValidator<T> {
    fun validate(entity: T): Map<String, String>
}