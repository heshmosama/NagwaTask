package com.example.nagwatask.presentation.mainscreen

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.nagwatask.R
import com.example.nagwatask.core.utils.DownloadResult
import com.example.nagwatask.core.utils.downloadFile
import com.example.nagwatask.databinding.ActivityMainBinding
import com.example.nagwatask.domain.entities.FilesListItemEntity
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.io.File


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FilesAdapter.Interaction {
    private val mainViewModel: MainViewModel by viewModels()
    lateinit var mainBinding : ActivityMainBinding
    lateinit var adapter :FilesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.viewmodel = mainViewModel
        mainBinding.lifecycleOwner = this
        mainViewModel.getFilesList()
        lifecycleScope.launch {
            mainViewModel.error.collect {
                Toast.makeText(this@MainActivity, "${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

         adapter = FilesAdapter(this)
        mainBinding.rvFiles.adapter = adapter

    }

    override fun onItemSelected(file: FilesListItemEntity, view: ProgressBar) {



        val filedir = applicationContext.getDir("drop",Context.MODE_PRIVATE)
        val sd_main = File(filedir, "frame-counter-one-hour.mp4")

        lifecycleScope.launch {
            file.url?.let {
                val uri = Url(it)

                mainViewModel.downloadFile(sd_main,uri).collect {
                    when (it) {
                        is DownloadResult.Success -> {
                            adapter.setDownloading(file,false)
                        }
                        is DownloadResult.Error -> {
                            adapter.setDownloading(file,false)
                            Log.v("Error","Error")
                            Toast.makeText(this@MainActivity, "Error while downloading ${file.name}", Toast.LENGTH_LONG).show()
                        }
                        is DownloadResult.Progress -> {
                            Log.v("Error","${it.progress}")
                            adapter.setProgress(file, it.progress)
                        }
                    }

                }
            }
        }



    }

}