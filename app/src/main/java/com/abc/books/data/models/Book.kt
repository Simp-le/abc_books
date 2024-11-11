package com.abc.books.data.models

import com.abc.books.data.models.book.AccessInfo
import com.abc.books.data.models.book.VolumeInfo
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val selfLink: String,
    private val volumeInfo: VolumeInfo,
    private val accessInfo: AccessInfo
) {
    val title: String
        get() = volumeInfo.title
    val description: String
        get() = volumeInfo.description
    val publishedDate: String
        get() = volumeInfo.publishedDate
    val authors: List<String>
        get() = volumeInfo.authors
    val imageUrl: String
        get() = cleanAndConvertUrl(volumeInfo.imageLinks.thumbnail)

    private fun cleanAndConvertUrl(url: String): String {
        val updatedUrl = url.replace("http://", "https://")

        val parts = updatedUrl.split("?")

        if (parts.size > 1) {
            val baseUrl = parts[0]
            val params = parts[1]

            val cleanedParams = params.split("&")
                .filter { !it.startsWith("zoom=") && !it.startsWith("edge=") }
                .joinToString("&")

            return if (cleanedParams.isNotEmpty()) "$baseUrl?$cleanedParams" else baseUrl
        }

        return updatedUrl
    }

    // val categories: List<String> = emptyList()
    // val maturityRating: MaturityRating
    // val language: String
    // val epub: BookFormat
    // val pdf: BookFormat
    // val webReaderLink: String
}