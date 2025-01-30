package com.dreamsoftware.chicfit.data.repository.mapper

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.chicfit.data.remote.dto.ArtworkDTO
import com.dreamsoftware.chicfit.domain.model.ArtworkBO
import com.dreamsoftware.chicfit.domain.model.ArtworkMessageBO
import com.dreamsoftware.chicfit.domain.model.ArtworkMessageRoleEnum
import com.dreamsoftware.chicfit.utils.enumNameOfOrDefault

internal class ArtworkMapper : IBrownieOneSideMapper<ArtworkDTO, ArtworkBO> {

    override fun mapInListToOutList(input: Iterable<ArtworkDTO>): Iterable<ArtworkBO> =
        input.map(::mapInToOut)

    override fun mapInToOut(input: ArtworkDTO): ArtworkBO = with(input) {
        ArtworkBO(
            uid = uid,
            userId = userId,
            imageUrl = imageUrl,
            imageDescription = imageDescription,
            createAt = createAt.toDate(),
            question = messages.first().text,
            messages = messages.map {
                ArtworkMessageBO(
                    uid = it.uid,
                    role = enumNameOfOrDefault(it.role, ArtworkMessageRoleEnum.MODEL),
                    text = it.text
                )
            }
        )
    }
}