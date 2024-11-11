package com.abc.books.data.models.book

import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfo(
    val title: String = "",
    val authors: List<String> = emptyList(),
    val publishedDate: String = "",
    val description: String = "",
    val categories: List<String> = emptyList(),
    val maturityRating: MaturityRating, // TODO: Check if works, else String type
    val imageLinks: ImageLinks = ImageLinks(),
    val language: String

)

enum class MaturityRating {
    NOT_MATURE, MATURE
}

@Serializable
data class ImageLinks(
    val thumbnail: String = "",

//    val smallThumbnail: String = "",
//    val small: String = "", // nullable
//    val medium: String = "", // nullable
//    val large: String = "", // nullable
//    val extraLarge: String = "", // nullable
)
