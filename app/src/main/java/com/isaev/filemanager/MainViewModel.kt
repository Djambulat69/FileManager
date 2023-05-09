package com.isaev.filemanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class MainViewModel : ViewModel() {

    private val repository = MainRepository()

    private var rootFile = File("")

    private val _storageAccess: MutableLiveData<Boolean> = MutableLiveData()
    private val _currentFilesList: MutableLiveData<List<File>> = MutableLiveData()
    private val modifiedFiles = mutableListOf<File>()

    private var modifiedShowed = false

    val storageAccess: LiveData<Boolean> = _storageAccess
    val currentFilesList: LiveData<List<File>> = _currentFilesList

    fun refreshAccess(allowed: Boolean) {
        if (allowed != storageAccess.value) {
            _storageAccess.value = allowed
        }
    }

    fun refreshFilesList(files: Array<File>) {
        _currentFilesList.value = files.toList()
    }

    fun refreshFilesList(file: File) {
        rootFile = file
        _currentFilesList.value = file.listFiles()?.toList().orEmpty()
    }

    fun saveHashCodes(rootDir: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val newFilesTree = rootDir.walkTopDown().filter { it.isFile }

            for (file in newFilesTree) {
                val modified = repository.isModified(file.hashCode())
                if (modified) {
                    modifiedFiles.add(file)
                }
            }

            repository.refreshHashFiles(
                newFilesTree.map { HashFile(it.hashCode()) }.toList()
            )
        }
    }

    fun showModifiedFiles() {
        if (modifiedShowed)
            refreshFilesList(rootFile.listFiles() ?: emptyArray())
        else
            refreshFilesList(modifiedFiles.toTypedArray())

        modifiedShowed = !modifiedShowed
    }

    fun isModified(file: File) = file in modifiedFiles

}
