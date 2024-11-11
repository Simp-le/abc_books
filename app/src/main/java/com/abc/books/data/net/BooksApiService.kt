package com.abc.books.data.net

import com.abc.books.data.models.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {

    @GET("v1/volumes")
    suspend fun getBooks(
        @Query("q") searchQuery: String,
        @Query("startIndex") startIndex: Int, // paging
        @Query("maxResults") maxResults: Int = 20,
        @Query("filter") filter: String = "free-ebooks"
    ): Response
}