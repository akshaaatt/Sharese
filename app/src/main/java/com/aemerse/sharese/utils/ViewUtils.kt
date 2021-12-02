package com.aemerse.sharese.utils

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.aemerse.sharese.R

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.bulge() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(
            context, R.anim.bulge
        )
        startAnimation(animation)
    }
}

fun View.shrink() {
    this.visibility = View.GONE
    val animation = AnimationUtils.loadAnimation(
        context, R.anim.shrink
    )
    startAnimation(animation)
}

fun View.slideUp() {
    val transition: Transition = Slide(Gravity.BOTTOM)
    transition.duration = 200
    transition.addTarget(this)

    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    visibility = View.VISIBLE
}

fun View.slideDown() {
    val transition: Transition = Slide(Gravity.BOTTOM)
    transition.duration = 200
    transition.addTarget(this)

    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    visibility = View.GONE
}