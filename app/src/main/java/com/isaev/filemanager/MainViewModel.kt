package com.isaev.filemanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class MainViewModel : ViewModel() {

    private val repository = MainRepository()

    private val _storageAccess: MutableLiveData<Boolean> = MutableLiveData()
    private val _currentFilesList: MutableLiveData<List<File>> = MutableLiveData()
    private val modifiedFiles = mutableListOf<File>()

    private var modifiedShowed = false

    private val _pathFiles: MutableList<File> = mutableListOf(File(""))

    val currentRootFile get() = _pathFiles.last()

    var currentSortOption = SortOption.NAME_A_Z
        private set(value) {
            field = value

            viewModelScope.launch(Dispatchers.Default) {
                val sorted = _currentFilesList.value?.sortedWith(
                    value.fileComparator
                )
                withContext(Dispatchers.Main) {
                    _currentFilesList.value = sorted.orEmpty()
                }
            }
        }

    val storageAccess: LiveData<Boolean> = _storageAccess
    val currentFilesList: LiveData<List<File>> = _currentFilesList

    fun refreshAccess(allowed: Boolean) {
        if (allowed != storageAccess.value) {
            _storageAccess.value = allowed
        }
    }

    fun refreshFilesList(files: Array<File>) {
        _currentFilesList.value = files.sortedWith(currentSortOption.fileComparator)
    }

    fun refreshFilesList(file: File) {
        _pathFiles.add(file)
        _currentFilesList.value =
            file.listFiles()?.sortedWith(currentSortOption.fileComparator).orEmpty()
    }

    fun back() {
        _pathFiles.removeLast()
        _currentFilesList.value =
            _pathFiles.last().listFiles()?.sortedWith(currentSortOption.fileComparator).orEmpty()
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
            refreshFilesList(currentRootFile.listFiles() ?: emptyArray())
        else
            refreshFilesList(modifiedFiles.toTypedArray())

        modifiedShowed = !modifiedShowed
    }

    fun sortBy(sortOption: SortOption) {
        currentSortOption = sortOption
    }

    fun isModified(file: File) = file in modifiedFiles

}
