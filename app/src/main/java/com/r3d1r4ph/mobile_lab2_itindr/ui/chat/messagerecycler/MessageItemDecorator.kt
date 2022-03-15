package com.r3d1r4ph.mobile_lab2_itindr.ui.chat.messagerecycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.r3d1r4ph.mobile_lab2_itindr.R

class MessageItemDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val margin16 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_16)
        val margin12 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_12)

        val newRect = when (position) {
            0 -> Rect(margin16, margin12, margin16, margin16)
            state.itemCount - 1 -> Rect(margin16, margin16, margin16, margin12)
            else -> Rect(margin16, margin12, margin16, margin12)
        }

        outRect.apply {
            left = newRect.left
            top = newRect.top
            right = newRect.right
            bottom = newRect.bottom
        }
    }
}