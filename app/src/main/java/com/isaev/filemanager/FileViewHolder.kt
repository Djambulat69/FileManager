package com.isaev.filemanager

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.isaev.filemanager.databinding.ListItemFileBinding
import java.io.File

class FileViewHolder(private val binding: ListItemFileBinding) : ViewHolder(binding.root) {
    fun bind(file: File) {
        binding.fileIcon.setImageResource(
            if (file.isDirectory)
                R.drawable.folder_icon
            else
                R.drawable.file_icon
        )

        binding.fileName.text = file.name
    }
}
