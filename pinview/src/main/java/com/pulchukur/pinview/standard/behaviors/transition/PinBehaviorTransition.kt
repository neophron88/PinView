package com.pulchukur.pinview.standard.behaviors.transition

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.ColorInt
import com.pulchukur.pinview.PinView

abstract class PinBehaviorTransition(
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
    private var lastColor: Int? = null


    override fun onStateChanged(state: PinView.ItemState) {

        val newColor = when (state) {
            is PinView.ItemState.InActiveFilled -> inActiveFilledColor
            is PinView.ItemState.InActiveEmpty -> inActiveEmptyColor
            is PinView.ItemState.Active -> activeColor
            is PinView.ItemState.Success -> successColor
            is PinView.ItemState.Error -> errorColor
        }

        if (lastColor == newColor) return

        if (lastColor == null || isSmoothColorTransitionEnabled.not()) {
            setColorDrawable(newColor)
        } else {
            animator?.cancel()
            animator = ObjectAnimator
                .ofArgb(lastColor!!, newColor)
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
