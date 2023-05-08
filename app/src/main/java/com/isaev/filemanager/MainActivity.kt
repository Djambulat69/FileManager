package com.isaev.filemanager

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.isaev.filemanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val isHighApi get() = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val storageAccess =
                if (isHighApi) Environment.isExternalStorageManager()
                else checkSelfPermission(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

            viewModel.refreshAccess(storageAccess)
        }

        binding.recyclerView.adapter = FilesAdapter(viewModel)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

        viewModel.storageAccess.observe(this) { allowed ->
            binding.grantPermissionText.isVisible = !allowed
            binding.grantButton.isVisible = !allowed

            binding.recyclerView.isVisible = allowed

            if (allowed) {
                viewModel.refreshFilesList(
                    Environment.getExternalStorageDirectory().listFiles() ?: emptyArray()
                )

                viewModel.saveHashCodes(Environment.getExternalStorageDirectory())
            }
        }

        viewModel.currentFilesList.observeForever { files ->
            (binding.recyclerView.adapter as FilesAdapter).submitList(files)
        }

        val legacyPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                viewModel.refreshAccess(granted)
            }

        binding.grantButton.setOnClickListener {
            if (isHighApi) {
                val isManager = Environment.isExternalStorageManager()

                if (!isManager) {
                    startActivity(
                        Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    )
                }
            } else {
                legacyPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (isHighApi) {
            viewModel.refreshAccess(Environment.isExternalStorageManager())
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        const val TAG = "MainActivityTAG"
    }
}
