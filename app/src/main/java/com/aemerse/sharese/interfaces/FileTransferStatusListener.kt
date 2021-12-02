package com.aemerse.sharese.interfaces

import com.aemerse.sharese.downloader.DownloadResult

interface FileTransferStatusListener {
    fun onUploadProgress(result: DownloadResult)
}