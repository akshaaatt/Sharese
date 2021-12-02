package com.aemerse.sharese.zshare_helpers

import android.net.Uri
import com.aemerse.sharese.local_connection.MessagesRunnable


const val PLUSES =
    "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"

fun MessagesRunnable.messagePeer(type: String, payload: String? = null): Boolean {
    val data = "$type:${Uri.encode(payload)}"
    write("$PLUSES$data~$PLUSES".toByteArray())
    return true
}