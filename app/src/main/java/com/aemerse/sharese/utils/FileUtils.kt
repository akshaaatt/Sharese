package com.aemerse.sharese.utils

import java.io.File
import android.os.Environment
import android.os.StatFs
import java.time.Duration
import java.util.*

object FileUtils {
    fun humanizeMillis(duration: Duration): String {
        return duration.toString()
            .substring(2)
            .replace("(\\d[HMS])(?!$)".toRegex(), "$1 ")
            .lowercase(Locale.getDefault())
    }

    fun getLastModified(path: String): Long {
        return File(path).lastModified()
    }

    fun getNameByPath(path: String): String {
        var result = "%20"
        val i = path.lastIndexOf('/')
        if (i > 0) result = path.substring(i + 1)
        return result
    }

    fun getExtensionByPath(path: String): String {
        var result = "%20"
        val i = path.lastIndexOf('.')
        if (i > 0) result = path.substring(i + 1)
        return result
    }

    fun externalMemoryAvailable(): Boolean {
        return Environment.getExternalStorageState() ==
                Environment.MEDIA_MOUNTED
    }

    val availableInternalMemorySize: Long
        get() {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSizeLong
            val availableBlocks = stat.availableBlocksLong
            return availableBlocks * blockSize
        }
    val totalInternalMemorySize: Long
        get() {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong
            return totalBlocks * blockSize
        }
}