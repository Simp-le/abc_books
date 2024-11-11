package com.abc.books.app

import android.app.Application
import com.abc.books.data.repo.AppContainer
import com.abc.books.data.repo.DefaultAppContainer

class BooksApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}