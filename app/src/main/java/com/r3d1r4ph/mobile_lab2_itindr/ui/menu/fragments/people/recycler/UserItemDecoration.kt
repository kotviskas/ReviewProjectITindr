package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.people.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.r3d1r4ph.mobile_lab2_itindr.R

class UserItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val margin8 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_8)
        val margin12 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_12)
        val margin16 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_16)
        val margin80 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_80)

        val lm = parent.layoutManager as StaggeredGridLayoutManager
        val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams

        val spanCount = lm.spanCount
        val spanIndex = lp.spanIndex

        val newRect = Rect(margin8, margin12, margin8, margin12)

        when (position) {
            0 -> {
                newRect.top = margin16
            }
            1 -> {
                newRect.top = margin80
            }
            2 -> {
                newRect.top = margin16
            }
        }

        outRect.apply {
            left = newRect.left
            top = newRect.top
            right = newRect.right
            bottom = newRect.bottom
        }
    }
}