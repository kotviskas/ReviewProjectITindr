package com.r3d1r4ph.mobile_lab2_itindr.utils.extensions

import android.view.View
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

fun recordInitialMarginForView(view: View) = InitialMargin(
    view.marginLeft, view.marginTop, view.marginRight, view.marginBottom
)

data class InitialMargin(
    val left: Int, val top: Int,
    val right: Int, val bottom: Int
)