package com.example.nagwatask.core.utils

interface DownloadProgressListener {
    fun update(bytesRead: Long, contentLength: Long?, done: Boolean)
}