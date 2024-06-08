@file:Suppress("NOTHING_TO_INLINE")

package com.pulchukur.pinview.standard.behaviors.background

import android.graphics.drawable.GradientDrawable
import android.view.View
import com.pulchukur.pinview.PinView

class PinBehaviorStaticBackground(
    targetView: View,
    drawable: GradientDrawable,
) : PinView.VisualBehavior(targetView) {

    init {
        targetView.background = drawable
    }

    override fun onStateChanged(index: Int, state: PinView.ItemState) {
        doNothing()
    }

    private inline fun doNothing() {}
}
