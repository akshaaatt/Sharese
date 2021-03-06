package com.aemerse.sharese.zshare_helpers

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_wconn_receive_file.view.*
import kotlinx.android.synthetic.main.item_wconn_send_file.view.*
import com.aemerse.sharese.BuildConfig
import com.aemerse.sharese.R
import com.aemerse.sharese.modals.*
import com.aemerse.sharese.utils.ApkInstaller
import com.aemerse.sharese.utils.docsColormap
import com.aemerse.sharese.utils.dpToPx
import com.aemerse.sharese.utils.humanizeBytes
import java.io.File


open class BaseConnHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ConnSendHolder(itemView: View) : BaseConnHolder(itemView) {
    fun setItem(item: ConnSendFileItem) {
        val itemFile = File(item.filePath)
        when (item.fileType) {
            FileType.Compressed -> {
                itemView.sendFileIcon.setColorFilter(
                    getColorStateList(
                        itemView.context,
                        R.color.c_com
                    ).defaultColor, PorterDuff.Mode.SRC_IN
                )
                itemView.sendFileIcon.setImageDrawable(
                    getDrawable(
                        itemView.context,
                        R.drawable.ic_compressed
                    )
                )
            }
            FileType.Documents -> {
                ContextCompat.getColor(
                    itemView.context!!,
                    docsColormap.getOrElse(itemFile.extension) { R.color.color_pdf })
                    .apply {
                        itemView.sendFileIcon.setColorFilter(this, PorterDuff.Mode.SRC_IN)
                        itemView.sendFileIcon.setImageDrawable(
                            getDrawable(
                                itemView.context,
                                R.drawable.ic_doc_file
                            )
                        )
                    }
            }
            FileType.Audios -> {
                itemView.sendFileIcon.setColorFilter(
                    getColorStateList(
                        itemView.context,
                        R.color.c_aud
                    ).defaultColor, PorterDuff.Mode.SRC_IN
                )
                itemView.sendFileIcon.setImageDrawable(
                    getDrawable(
                        itemView.context,
                        R.drawable.ic_audio_outline
                    )
                )
            }
            FileType.Images -> {
                Glide.with(itemView).load(item.filePath).thumbnail(0.5f).centerCrop()
                    .error(R.drawable.ic_images).into(itemView.sendFileIcon)
            }
            FileType.Videos -> Glide.with(itemView)
                .load(item.filePath).thumbnail(0.2f).centerCrop()
                .error(R.drawable.ic_images).into(itemView.sendFileIcon)
            FileType.Apps -> {
                val pm = itemView.context.packageManager
                pm?.getPackageArchiveInfo(
                    item.filePath,
                    PackageManager.GET_META_DATA
                )?.let {
                    it.applicationInfo.sourceDir = item.filePath
                    it.applicationInfo.publicSourceDir = item.filePath
                    Log.d("ConnReceiveHolder", "appInfo: ${it.applicationInfo}")
                    Glide
                        .with(itemView)
                        .load(it.applicationInfo.loadIcon(pm))
                        .error(R.drawable.ic_apk)
                        .thumbnail(0.2f)
                        .centerCrop()
                        .into(itemView.sendFileIcon)
                }
            }
        }

        itemView.sendFileName.text = item.fileName
        itemView.sendFileName.isSelected = true
        itemView.sendFileType.text = item.fileType.name
        itemView.sendFileSize.text = humanizeBytes(itemFile.length())
        itemView.sendFileStatus.text = when (item.status) {
            ConnFileStatusTypes.WAITING -> "In queue"
            ConnFileStatusTypes.LOADING -> {
                itemView.sendFileStatus.setTypeface(
                    itemView.sendFileStatus.typeface,
                    Typeface.BOLD_ITALIC
                )
                "Sending"
            }
            ConnFileStatusTypes.LOADED -> "Sent"
        }
    }
}

class ConnReceiveHolder(itemView: View) : BaseConnHolder(itemView) {
    fun setItem(item: ConnReceiveFileItem) {

        val itemFile = File(item.filePath)

        when (item.fileType) {
            FileType.Compressed -> {
                itemView.receiveFileIcon.setColorFilter(
                    getColorStateList(
                        itemView.context,
                        R.color.c_com
                    ).defaultColor, PorterDuff.Mode.SRC_IN
                )
                itemView.receiveFileIcon.setImageDrawable(
                    getDrawable(
                        itemView.context,
                        R.drawable.ic_compressed
                    )
                )
            }
            FileType.Documents -> {
                ContextCompat.getColor(
                    itemView.context!!,
                    docsColormap.getOrElse(itemFile.extension) { R.color.color_pdf })
                    .apply {
                        itemView.receiveFileIcon.setColorFilter(this, PorterDuff.Mode.SRC_IN)
                        itemView.receiveFileIcon.setImageDrawable(
                            getDrawable(
                                itemView.context,
                                R.drawable.ic_doc_file
                            )
                        )
                    }
            }

            FileType.Audios -> {
                itemView.receiveFileIcon.setColorFilter(
                    getColorStateList(
                        itemView.context,
                        R.color.c_aud
                    ).defaultColor, PorterDuff.Mode.SRC_IN
                )
                itemView.receiveFileIcon.setImageDrawable(
                    getDrawable(
                        itemView.context,
                        R.drawable.ic_audio_outline
                    )
                )
            }
            FileType.Images -> Glide.with(itemView)
                .load(item.filePath).thumbnail(0.2f).centerCrop()
                .error(
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_images)
                )
                .into(itemView.receiveFileIcon)
            FileType.Videos -> Glide.with(itemView)
                .load(item.filePath).thumbnail(0.2f).centerCrop().error(
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_video)
                )
                .into(itemView.receiveFileIcon)

            FileType.Apps -> {

                if (item.status == ConnFileStatusTypes.LOADED) {
                    val pm = itemView.context.packageManager
                    pm?.getPackageArchiveInfo(
                        item.filePath,
                        PackageManager.GET_META_DATA
                    )?.let {
                        it.applicationInfo.sourceDir = item.filePath
                        it.applicationInfo.publicSourceDir = item.filePath
                        Log.d("ConnReceiveHolder", "setItem: ${it.applicationInfo}")
                        Glide
                            .with(itemView)
                            .load(it.applicationInfo.loadIcon(pm))
                            .error(R.drawable.ic_apk)
                            .thumbnail(0.2f)
                            .centerCrop()
                            .into(itemView.receiveFileIcon)
                    }
                } else
                    Glide
                        .with(itemView)
                        .load(R.drawable.ic_apk)
                        .thumbnail(0.2f)
                        .centerCrop()
                        .into(itemView.receiveFileIcon)

            }
        }

        itemView.receiveFileName.text = item.fileName
        itemView.receiveFileName.isSelected = true
        itemView.receiveFileType.text = item.fileType.name
        itemView.receiveFileSize.text = humanizeBytes(item.fileSize)

        itemView.receiveFileStatus.text = when (item.status) {
            ConnFileStatusTypes.WAITING -> "In queue"
            ConnFileStatusTypes.LOADING -> {
                itemView.receiveFileStatus.setTypeface(
                    itemView.receiveFileStatus.typeface,
                    Typeface.BOLD_ITALIC
                )
                "Receiving"
            }
            ConnFileStatusTypes.LOADED -> "Received"
        }

        if (item.status == ConnFileStatusTypes.LOADED) {
            val context = itemView.context
            itemView.setOnClickListener {
                val alertDialog = AlertDialog.Builder(context).create()
                alertDialog.setTitle("Do you want to open ${item.fileName}?")
                alertDialog.setMessage("The on going activity may interrupt.")
                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE, "No"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEGATIVE, "Yes"
                ) { dialog, which ->
                    val file = File(item.filePath)
                    if (item.fileType == FileType.Apps) {
                        ApkInstaller.installApplication(context, file)
                    } else {
                        val uri: Uri = FileProvider.getUriForFile(
                            context,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file
                        )
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        context.startActivity(intent)
                    }

                    dialog.dismiss()
                }
                alertDialog.show()
            }
        }
    }
}

class ConnectionMessageAdapter(
    private val list: ArrayList<ConnectionItem>,
    private val activity: Activity
) :
    RecyclerView.Adapter<BaseConnHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConnHolder {
        return when (viewType) {
            ConnectionMessageType.SEND_FILE.ordinal -> ConnSendHolder(
                LayoutInflater.from(activity)
                    .inflate(R.layout.item_wconn_send_file, parent, false)
            )

            ConnectionMessageType.RECEIVE_FILE.ordinal -> ConnReceiveHolder(
                LayoutInflater.from(activity)
                    .inflate(R.layout.item_wconn_receive_file, parent, false)
            )
            else -> BaseConnHolder(View(activity))
        }
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: BaseConnHolder, position: Int) {
        when (list[position].type) {
            ConnectionMessageType.SEND_FILE -> {
                (holder as ConnSendHolder).setItem(list[position] as ConnSendFileItem)
                if (position > 0 && (list[position - 1] is ConnReceiveFileItem)) {
                    holder.itemView.updatePadding(top = dpToPx(20))//layoutParams = marginParams;
                }

            }
            ConnectionMessageType.RECEIVE_FILE -> {
                (holder as ConnReceiveHolder).setItem(list[position] as ConnReceiveFileItem)
                if (position > 0 && (list[position - 1] is ConnSendFileItem)) {
                    holder.itemView.updatePadding(top = dpToPx(20))//layoutParams = marginParams;
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].type.ordinal

}