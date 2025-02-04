package com.dreamsoftware.chicfit.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.chicfit.data.remote.dto.CreateOutfitDTO
import com.dreamsoftware.chicfit.domain.model.CreateOutfitBO
import com.dreamsoftware.chicfit.domain.model.OutfitMessageRoleEnum

internal class CreateOutfitMapper: IBrownieOneSideMapper<CreateOutfitBO, CreateOutfitDTO> {

    override fun mapInListToOutList(input: Iterable<CreateOutfitBO>): Iterable<CreateOutfitDTO> =
        input.map(::mapInToOut)

    override fun mapInToOut(input: CreateOutfitBO): CreateOutfitDTO = with(input) {
        CreateOutfitDTO(
            uid = uid,
            userId = userId,
            imageUrl = imageUrl,
            imageDescription = imageDescription,
            question = question,
            questionRole = OutfitMessageRoleEnum.USER.name,
            answer = answer,
            answerRole = OutfitMessageRoleEnum.MODEL.name
        )
    }
}