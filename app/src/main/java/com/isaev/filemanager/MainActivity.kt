package com.isaev.filemanager

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.viewModels
import java.io.File

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {

            val isManager = Environment.isExternalStorageManager()

            if (!isManager) {
                startActivity(
                    Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                )
            }
        }

        val files: Array<File> =
            Environment.getExternalStorageDirectory().listFiles() ?: emptyArray()

        files.forEach {
            Log.i(TAG, "File: ${it.name} isFile: ${it.isFile}")
        }


        Log.i(TAG, files.toList().toString())

    }

    companion object {

        const val TAG = "MainActivity"
    }
}