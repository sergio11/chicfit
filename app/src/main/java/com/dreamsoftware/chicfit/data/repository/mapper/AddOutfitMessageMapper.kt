package com.dreamsoftware.chicfit.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.chicfit.data.remote.dto.AddMessageDTO
import com.dreamsoftware.chicfit.domain.model.AddMessageBO
import com.dreamsoftware.chicfit.domain.model.OutfitMessageRoleEnum

internal class AddOutfitMessageMapper : IBrownieOneSideMapper<AddMessageBO, AddMessageDTO> {

    override fun mapInListToOutList(input: Iterable<AddMessageBO>): Iterable<AddMessageDTO> =
        input.map(::mapInToOut)

    override fun mapInToOut(input: AddMessageBO): AddMessageDTO = with(input) {
        AddMessageDTO(
            uid = uid,
            userId = userId,
            question = question,
            questionRole = OutfitMessageRoleEnum.USER.name,
            answer = answer,
            answerRole = OutfitMessageRoleEnum.MODEL.name
        )
    }
}