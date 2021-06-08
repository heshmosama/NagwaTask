package com.example.nagwatask.data.Models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FilesListItem(
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("type")
    @Expose
    val type: String?,
    @SerializedName("url")
    @Expose
    val url: String?
)