package com.aemerse.sharese.utils

import android.content.Context
import android.content.res.Resources
import com.aemerse.sharese.BuildConfig
import com.aemerse.sharese.R
import com.aemerse.sharese.modals.FileType
import java.io.File
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import java.util.*

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun humanizeBytes(_bytes: Long, limit: Int = 2): String? {
    var bytes = _bytes
    if (-1000 < bytes && bytes < 1000) {
        return "$bytes B"
    }
    val ci: CharacterIterator = StringCharacterIterator("KMGTPE")
    while (bytes <= -999950 || bytes >= 999950) {
        bytes /= 1000
        ci.next()
    }
    return String.format(
        Locale.getDefault(),
        "%.${limit}f %cB",
        bytes / 1000.0,
        ci.current()
    )
}

fun humanizeTime(_millis: Long): String? {
    var seconds = _millis / 1000
    var out = ""
    val hr = seconds / (60 * 60)
    seconds %= (60 * 60)
    val min = seconds / 60
    seconds %= 60
    var isHours = false
    var isMins = false
    if (hr > 0) {
        out += "$hr Hr" + if (hr == 1L) " " else "s "
    }
    if (min > 0) {
        out += "$min M "
    }
    if (seconds > 0) {
        out += "$seconds s"
    }
    return out
}


val docsColormap = mapOf(
    "pdf" to R.color.color_pdf,
    "csv" to R.color.color_csv,
    "doc" to R.color.color_doc,
    "docx" to R.color.color_docx,
    "xls" to R.color.color_xls,
    "xlsx" to R.color.color_xlsx
)

val fileTypeList = FileType.values()

fun Context.getExternalDirectory(): String = getExternalFilesDir(null)?.absolutePath!!.replace(
    "Android/data/${BuildConfig.APPLICATION_ID}/files", ""
)

fun Context.getShareseFolder(): File = File("${getExternalDirectory()}/Sharese/")
