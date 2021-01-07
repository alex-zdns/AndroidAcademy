package ru.alexzdns.fundamentals.homework.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO (
    @SerialName("adult")
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("genres")
    val genres: List<GenreDTO>,

    @SerialName("id")
    val id: Long,

    @SerialName("overview")
    val overview: String?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("title")
    val title: String,

    @SerialName("vote_average")
    val voteAverage: Float,

    @SerialName("vote_count")
    val voteCount: Int,

    @SerialName("runtime")
    val runtime: Int?
)