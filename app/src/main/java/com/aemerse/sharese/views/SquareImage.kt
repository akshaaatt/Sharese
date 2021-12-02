package com.aemerse.sharese.views

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet

class SquareImage : AppCompatImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    override fun onMeasure(width: Int, height: Int) {
        super.onMeasure(width, height)
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        if (measuredWidth > measuredHeight) {
            setMeasuredDimension(measuredHeight, measuredHeight)
        } else {
            setMeasuredDimension(measuredWidth, measuredWidth)
        }
    }
}