package com.r3d1r4ph.mobile_lab2_itindr.utils.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.setSystemMarginTop() {
    doOnApplyWindowInsets { view, windowInsets, initialMargin ->
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(
            params.leftMargin,
            initialMargin.top + windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
            params.rightMargin,
            params.bottomMargin
        )
        layoutParams = params
    }
}

fun View.setSystemMarginBottom() {
    doOnApplyWindowInsets { view, windowInsets, initialMargin ->
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(
            params.leftMargin,
            params.topMargin,
            params.rightMargin,
            initialMargin.bottom + windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
        )
        layoutParams = params
    }
}

fun View.doOnApplyWindowInsets(setMargin: (View, WindowInsetsCompat, InitialMargin) -> Unit) {
    val initialMargin = recordInitialMarginForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        setMargin(v, insets, initialMargin)
        insets
    }
}