package com.isaev.filemanager

import android.app.Application

class FileManagerApp : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: FileManagerApp
    }
}