package kr.ac.kumoh.ce.s20190467.smartapp_project

import retrofit2.http.GET

interface BookApi {
    @GET("book")
    suspend fun getBooks(): List<Book>
}