package com.example.nagwatask.presentation.mainscreen

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.nagwatask.R
import com.example.nagwatask.core.utils.Download
import com.example.nagwatask.core.utils.DownloadAPI
import com.example.nagwatask.core.utils.DownloadProgressListener
import com.example.nagwatask.databinding.ActivityMainBinding
import com.example.nagwatask.domain.entities.FilesListItemEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FilesAdapter.Interaction {
    private val mainViewModel: MainViewModel by viewModels()
    lateinit var mainBinding : ActivityMainBinding
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

        val adapter = FilesAdapter(this)
        mainBinding.rvFiles.adapter = adapter

    }

    override fun onItemSelected(file: FilesListItemEntity, view: View) {
         view.visibility = View.GONE

        val listener: DownloadProgressListener = object : DownloadProgressListener {

            override fun update(bytesRead: Long, contentLength: Long?, done: Boolean) {
                val download = Download()
                download.totalFileSize = contentLength!!
                download.currentFileSize = bytesRead
                val progress = (bytesRead * 100 / contentLength).toInt()
                download.progress = progress
            }
        }
        val outputFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "file.apk"
        )

        val uri = URI(file.url!!)
        val hostname: String = uri.getHost()
        val baseurl = "https//${hostname}/"
        val path = file.url?.substring(baseurl.length+1)
        DownloadAPI(baseurl, listener).downloadAPK(file.url!!, outputFile, {
            Log.v("loaded",it.toString())

        })

    }


}