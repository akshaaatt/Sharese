package com.aemerse.sharese.zshare_helpers

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.aemerse.sharese.ShareActivity
import com.aemerse.sharese.ShareActivity.Companion.TAG
import com.aemerse.sharese.modals.*
import com.aemerse.sharese.utils.*
import com.aemerse.sharese.utils.Constants.Companion.APPS
import com.aemerse.sharese.utils.Constants.Companion.FILES
import java.io.File
import java.lang.Exception

fun ShareActivity.handActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
) {
    if (resultCode == Activity.RESULT_OK) {


        if (requestCode == RESULT_CODE_INPUT_APPS) {
            val apps = data?.getSerializableExtra(APPS) as ArrayList<AppData>
            for (app in apps) {
                Log.d(TAG, "handActivityResult: ${app.appName}")
                mService?.addSendFileToQue(getSendItemFromAppData(app))
            }
        } else if (
            requestCode == RESULT_CODE_INPUT_DOCS ||
            requestCode == RESULT_CODE_INPUT_IMAGES ||
            requestCode == RESULT_CODE_INPUT_VIDEOS ||
            requestCode == RESULT_CODE_INPUT_AUDIOS ||
            requestCode == RESULT_CODE_INPUT_COMPRESSED
        ) {
            val files = data?.getSerializableExtra(FILES) as ArrayList<File>
            for (file in files) {

                val type = when (requestCode) {
                    RESULT_CODE_INPUT_DOCS -> FileType.Documents
                    RESULT_CODE_INPUT_IMAGES -> FileType.Images
                    RESULT_CODE_INPUT_VIDEOS -> FileType.Videos
                    RESULT_CODE_INPUT_AUDIOS -> FileType.Audios
                    RESULT_CODE_INPUT_COMPRESSED -> FileType.Compressed
                    else -> {
                        throw Exception("Impossible!")
                    }
                }

                mService?.addSendFileToQue(getSendItemFromFileAndType(file, type))
            }

        }
    }
}