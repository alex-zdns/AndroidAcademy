package ru.alexzdns.fundamentals.homework.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesDto(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val movies: List<MovieDto>,

    @SerialName("total_pages")
    val totalPages: Int
)