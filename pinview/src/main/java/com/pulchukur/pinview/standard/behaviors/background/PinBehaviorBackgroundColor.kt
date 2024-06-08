package com.pulchukur.pinview.standard.behaviors.background

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.ColorInt
import com.pulchukur.pinview.PinView

abstract class PinBehaviorBackgroundColor(
    targetView: View,
    @ColorInt private val activeColor: Int,
    @ColorInt private val inActiveFilledColor: Int,
    @ColorInt private val inActiveEmptyColor: Int,
    @ColorInt private val successColor: Int,
    @ColorInt private val errorColor: Int,
    private val isSmoothColorTransitionEnabled: Boolean,
    private val colorTransitionDuration: Long
) : PinView.VisualBehavior(targetView) {

    private var animator: ValueAnimator? = null
    private var lastColor = -1


    override fun onStateChanged(index: Int, state: PinView.ItemState) {

        val newColor = when (state) {
            is PinView.ItemState.InActiveFilled -> inActiveFilledColor
            is PinView.ItemState.InActiveEmpty -> inActiveEmptyColor
            is PinView.ItemState.Active -> activeColor
            is PinView.ItemState.Success -> successColor
            is PinView.ItemState.Error -> errorColor
        }

        if (lastColor == -1 || isSmoothColorTransitionEnabled.not()) {
            setColorDrawable(newColor)
        } else {
            animator?.cancel()
            animator = ObjectAnimator
                .ofArgb(lastColor, newColor)
                .setDuration(colorTransitionDuration)
                .apply {
                    addUpdateListener { setColorDrawable(it.animatedValue as Int) }
                }
            animator?.start()
        }
        lastColor = newColor
    }

    abstract fun setColorDrawable(@ColorInt color: Int)

}
