package com.example.nagwatask.presentation.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nagwatask.databinding.ListItemViewBinding
import com.example.nagwatask.domain.entities.FilesListItemEntity
import kotlinx.android.synthetic.main.list_item_view.view.*

class FilesAdapter(

    private val interaction: Interaction? = null
) : ListAdapter<FilesListItemEntity, FilesAdapter.FilesViewHolder>(FileDiffCallback()) {


    class FileDiffCallback : DiffUtil.ItemCallback<FilesListItemEntity>() {
        override fun areItemsTheSame(oldItem: FilesListItemEntity, newItem: FilesListItemEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilesListItemEntity, newItem: FilesListItemEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemViewBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return FilesViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val file = getItem(position)

        holder.bind(file)
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.firstOrNull() != null) {
            with(holder.itemView) {
                (payloads.first() as Bundle).getInt("progress").also {
                    val isVisible = it < 99
                    pb_download.progress = it
                    pb_download.isVisible= isVisible
                    img_download.isVisible =isVisible
                    animationView.isVisible = it == 100
                    tv_percentage.isVisible = it < 99
                    tv_percentage.text = "$it %"
                }
            }
        }
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class FilesViewHolder
    constructor(

        private val binding: ListItemViewBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(file: FilesListItemEntity) = with(itemView) {
            binding.model = file
            binding.executePendingBindings()
            binding.imgDownload.isVisible = !file.isFileExist
            binding.animationView.isVisible = file.isFileExist
            binding.imgDownload.setOnClickListener {
                interaction?.onItemSelected(file, binding.pbDownload)

            }
        }

    }

    fun setDownloading(file: FilesListItemEntity, isDownloading: Boolean,isFileExist :Boolean) {
        file.isloading = isDownloading
        file.isFileExist = isFileExist
        notifyItemChanged(this.currentList.indexOf(file))
    }

    fun setProgress(file: FilesListItemEntity, progress: Int) {
        file.progress = progress
        notifyItemChanged(this.currentList.indexOf(file), Bundle().apply { putInt("progress", progress) })
    }
    interface Interaction {
        fun onItemSelected(file: FilesListItemEntity, view: ProgressBar)
    }
}
