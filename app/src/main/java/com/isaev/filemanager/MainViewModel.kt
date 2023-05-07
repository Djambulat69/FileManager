package com.isaev.filemanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _storageAccess: MutableLiveData<Boolean> = MutableLiveData()

    val storageAccess: LiveData<Boolean> = _storageAccess


    fun refreshAccess(allowed: Boolean) {
        _storageAccess.value = allowed
    }
}
