package com.abc.books.data.models.book

import kotlinx.serialization.Serializable

@Serializable
data class AccessInfo (
    val epub: BookFormat,
    val pdf: BookFormat,
    val webReaderLink: String
)

@Serializable
data class BookFormat (
    val isAvailable: Boolean,
    val downloadLink: String = ""
)