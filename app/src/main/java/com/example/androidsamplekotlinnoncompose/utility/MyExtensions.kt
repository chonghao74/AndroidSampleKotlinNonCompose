package com.example.androidsamplekotlinnoncompose.utility

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewDebug.IntToString

val Float.dpToPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()

fun View.showVisible() {
    this.visibility = View.VISIBLE
}

fun View.showInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.showGone() {
    this.visibility = View.GONE
}

