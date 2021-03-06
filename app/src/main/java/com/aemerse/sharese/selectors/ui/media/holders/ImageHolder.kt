package com.aemerse.sharese.selectors.ui.media.holders

import android.graphics.drawable.ColorDrawable
import android.view.View

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.aemerse.sharese.R
import com.aemerse.sharese.views.SquareImage
import java.io.File

import kotlinx.android.synthetic.main.item_image.view.*

class ImageHolder(itemView: View) : BaseMediaHolder(itemView) {

    override var check: View? = itemView.imageChecked
    private val image: SquareImage = itemView.imageView

    override fun setFile(file: File) {
        Glide.with(itemView).load(file)
            .placeholder(
                ColorDrawable(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.placeholder_gray
                    )
                )
            )
            .centerCrop()
            .thumbnail(0.5f)
            .into(image)
    }
}