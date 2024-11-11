package com.abc.books.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Response (
    val totalItems: Int,
    val items: List<Book> = emptyList()
)