package com.pulchukur.pinview.standard.behaviors.transition

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt

class PinBehaviorTransitionSolid(
    targetView: View,
    private val drawable: GradientDrawable,
    @ColorInt activeColor: Int,
    @ColorInt inActiveFilledColor: Int = activeColor,
    @ColorInt inActiveEmptyColor: Int = Color.TRANSPARENT,
    @ColorInt successColor: Int = Color.GREEN,
    @ColorInt errorColor: Int = Color.RED,
    isSmoothColorTransitionEnabled: Boolean = true,
    colorTransitionDuration: Long = 300,
) : PinBehaviorTransition(
    targetView,
    activeColor,
    inActiveFilledColor,
    inActiveEmptyColor,
    successColor,
    errorColor,
    isSmoothColorTransitionEnabled,
    colorTransitionDuration
) {
    init {
        targetView.background = drawable
    }

    override fun setColorDrawable(@ColorInt color: Int) {
        drawable.setColor(color)
    }

}
