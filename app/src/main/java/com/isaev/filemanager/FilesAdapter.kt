package com.isaev.filemanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.isaev.filemanager.databinding.ListItemFileBinding
import java.io.File

class FilesAdapter(private val viewModel: MainViewModel) :
    ListAdapter<File, FileViewHolder>(FileDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val bindingView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_file, parent, false)

        return FileViewHolder(ListItemFileBinding.bind(bindingView), viewModel)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

object FileDiffCallback : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
        return oldItem == newItem
    }
}