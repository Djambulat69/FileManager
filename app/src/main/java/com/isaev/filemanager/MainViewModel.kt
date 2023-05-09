package com.isaev.filemanager

import android.os.FileUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainViewModel : ViewModel() {

    private val _storageAccess: MutableLiveData<Boolean> = MutableLiveData()
    private val _currentFilesList: MutableLiveData<List<File>> = MutableLiveData()

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

    fun saveHashCodes(rootDir: File) {
        viewModelScope.launch(Dispatchers.IO) {
            rootDir.walkTopDown().forEach { file ->
                //TODO save to db
            }
        }
    }

    private fun saveHashCodeToDb(hash: Int) {
        // save to db
    }
}
