package com.isaev.filemanager

import android.icu.text.DateFormat
import android.icu.text.DateIntervalFormat
import android.icu.text.SimpleDateFormat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.isaev.filemanager.databinding.ListItemFileBinding
import java.io.File
import java.time.LocalDateTime
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds

class FileViewHolder(
    private val binding: ListItemFileBinding,
    private val viewModel: MainViewModel
) : ViewHolder(binding.root) {

    private val context get() = binding.root.context

    init {
        binding.root.setOnClickListener {
            viewModel.currentFilesList.value?.get(adapterPosition)?.let {
                if (it.isDirectory) {
                    viewModel.refreshFilesList(it.listFiles() ?: emptyArray())
                }
            }
        }
    }

    fun bind(file: File) {

        binding.fileIcon.setImageResource(
            if (file.isDirectory)
                R.drawable.folder_icon
            else
                R.drawable.file_icon
        )

        val fileDate = file.lastModified().readableDate(context.resources.configuration.locales[0])

        binding.fileName.text = file.name
        binding.fileSizeDate.text =
            if (file.isFile)
                "${file.readableLength()}, $fileDate"
            else fileDate
    }

}
