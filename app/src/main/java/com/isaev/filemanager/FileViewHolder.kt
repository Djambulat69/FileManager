package com.isaev.filemanager


import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.isaev.filemanager.databinding.ListItemFileBinding
import java.io.File


class FileViewHolder(
    private val binding: ListItemFileBinding,
    private val viewModel: MainViewModel
) : ViewHolder(binding.root) {

    private val context get() = binding.root.context

    init {
        binding.root.setOnClickListener {

            if (adapterPosition == RecyclerView.NO_POSITION)
                return@setOnClickListener

            viewModel.currentFilesList.value?.get(adapterPosition)?.let {
                if (it.isDirectory) {
                    viewModel.refreshFilesList(it)
                } else {
                    val uri = FileProvider.getUriForFile(context, "com.isaev.fileprovider", it)
                    try {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, uri)
                        )
                    } catch (_: Exception) {
                        Toast.makeText(
                            context, context.getString(R.string.cant_open_file), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    fun bind(file: File) {
        binding.fileIcon.setImageResource(
            if (file.isDirectory)
                R.drawable.folder_icon
            else {
                val name = file.name
                when {
                    name.endsWith("txt") -> R.drawable.txt_file_icon
                    name.endsWith("pdf") -> R.drawable.pdf_file_icon
                    name.endsWith("gif") -> R.drawable.gif_file_icon
                    name.endsWith("mp3")
                            || name.endsWith("wav")
                            || name.endsWith("flac") -> R.drawable.audio_file_icon
                    name.endsWith("mp4")
                            || name.endsWith("mov")
                            || name.endsWith("avi") -> R.drawable.video_file_icon
                    name.endsWith("jpg")
                            || name.endsWith("jpeg")
                            || name.endsWith("png") -> R.drawable.image_file_icon
                    else -> R.drawable.file_icon
                }
            }
        )

        val fileDate = file.lastModified().readableDate(context.resources.configuration.locales[0])

        binding.fileName.text = file.name
        binding.fileSizeDate.text =
            if (file.isFile)
                "${file.readableLength()}, $fileDate"
            else fileDate


        // Можно лучше, но время чуть поджимает
        binding.editIcon.isVisible = viewModel.isModified(file) && file.isFile
    }

}
