package com.example.nagwatask.domain.entities


data class FilesListItemEntity(
    val id: Int?,
    val name: String?,
    val type: String?,
    val url: String?,
    var isloading :Boolean = false,
    var progress :Int = 0
)