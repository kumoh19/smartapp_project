package kr.ac.kumoh.ce.s20190467.smartapp_project

data class Book(
    val id: Int,
    val title: String,
    val writer: String,
    val rating: Int,
    val image: String?,
    val description: String?
)
