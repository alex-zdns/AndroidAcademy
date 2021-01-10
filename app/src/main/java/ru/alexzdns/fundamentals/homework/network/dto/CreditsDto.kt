package ru.alexzdns.fundamentals.homework.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsDto (
    @SerialName("id")
    val id: Int,

    @SerialName("cast")
    val cast : List<CastDto>
)