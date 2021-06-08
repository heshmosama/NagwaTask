package com.example.nagwatask.core.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nagwatask.domain.entities.FilesListEntity
import com.example.nagwatask.presentation.mainscreen.FilesAdapter


@BindingAdapter("moviesList")
fun bindFilesList(recyclerView: RecyclerView, list: FilesListEntity?) {
    list?.let { (recyclerView.adapter as FilesAdapter).submitList(it) }
}


