package com.isaev.filemanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class MainViewModel : ViewModel() {
    private val _storageAccess: MutableLiveData<Boolean> = MutableLiveData()
    private val _currentFilesList: MutableLiveData<List<File>> = MutableLiveData()

    val storageAccess: LiveData<Boolean> = _storageAccess
    val currentFilesList: LiveData<List<File>> = _currentFilesList


    fun refreshAccess(allowed: Boolean) {
        _storageAccess.value = allowed
    }

    fun refreshFilesList(files: Array<File>) {
        _currentFilesList.value = files.toList()
    }

    private fun saveHashCodes(files: Array<File>) {
        files.forEach { file ->
            if (file.isFile) {
                saveHashCodeToDb(file.hashCode())
            } else {
                saveHashCodes(file.listFiles() ?: emptyArray())
            }
        }
    }

    private fun saveHashCodeToDb(hash: Int) {
        // save to db
    }
}
