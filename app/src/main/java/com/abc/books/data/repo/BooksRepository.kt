package com.abc.books.data.repo

import com.abc.books.data.net.BooksApiService
import com.abc.books.data.models.Book

interface BooksRepository {
    suspend fun getBooks(searchQuery: String, startIndex: Int): List<Book>
}

class NetworkBooksRepository(private val booksApiService: BooksApiService) : BooksRepository {
    override suspend fun getBooks(searchQuery: String, startIndex: Int): List<Book> =
        booksApiService.getBooks(searchQuery = searchQuery, startIndex = startIndex).items
}