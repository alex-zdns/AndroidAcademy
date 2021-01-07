package ru.alexzdns.fundamentals.homework.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val movies: List<MovieShortDTO>,

    @SerialName("total_pages")
    val totalPages: Int
)