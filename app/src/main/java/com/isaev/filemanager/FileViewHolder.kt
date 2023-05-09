package com.isaev.filemanager


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
    }

}
