package com.beatrice.bookflow.data.di

import com.beatrice.bookflow.data.BookApiService
import com.beatrice.bookflow.data.repository.BookRepository
import com.beatrice.bookflow.data.repository.BookRepositoryImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

const val  BASE_URL = "https://openlibrary.org/"
val dataModule = module {
    single {
        createRetrofit(baseUrl = BASE_URL )
    }

    single { createBooksApiService(get()) }

    single<BookRepository>{BookRepositoryImpl(get())}
}

private fun createRetrofit(baseUrl: String): Retrofit =
    Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

private fun createBooksApiService(retrofit: Retrofit): BookApiService =
    retrofit.create(BookApiService::class.java)

private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    HttpLoggingInterceptor.Level.BODY
}

private val client: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(
    loggingInterceptor
).build()

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}