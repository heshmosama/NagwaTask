package com.example.nagwatask.presentation.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nagwatask.databinding.ListItemViewBinding
import com.example.nagwatask.domain.entities.FilesListItemEntity

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
            binding.imgDownload.setOnClickListener {
                interaction?.onItemSelected(file, it)

            }
        }

    }

    interface Interaction {
        fun onItemSelected(file: FilesListItemEntity, view: View)
    }
}
