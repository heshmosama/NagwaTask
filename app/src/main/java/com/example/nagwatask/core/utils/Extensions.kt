package com.example.nagwatask.core.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nagwatask.domain.entities.FilesListEntity
import com.example.nagwatask.presentation.mainscreen.FilesAdapter
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt


@BindingAdapter("moviesList")
fun bindFilesList(recyclerView: RecyclerView, list: FilesListEntity?) {
    list?.let { (recyclerView.adapter as FilesAdapter).submitList(it) }
}






