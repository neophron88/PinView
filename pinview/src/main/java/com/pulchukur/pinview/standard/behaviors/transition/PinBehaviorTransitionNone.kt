@file:Suppress("NOTHING_TO_INLINE")

package com.pulchukur.pinview.standard.behaviors.transition

import android.graphics.drawable.GradientDrawable
import android.view.View
import com.pulchukur.pinview.PinView

class PinBehaviorTransitionNone(
    targetView: View,
    drawable: GradientDrawable,
) : PinView.VisualBehavior(targetView) {

    init {
        targetView.background = drawable
    }

    override fun onStateChanged(state: PinView.ItemState) {
        doNothing()
    }

    private inline fun doNothing() {}
}
