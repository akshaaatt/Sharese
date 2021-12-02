package com.aemerse.sharese.zshare_helpers

import com.google.gson.Gson
import com.aemerse.sharese.modals.ConnSendFileItem
import com.aemerse.sharese.modals.FileData
import java.io.File

fun ConnSendFileItem.toJson(): String =
    Gson().toJson(FileData(fileType, fileName, filePath, File(filePath).length()))