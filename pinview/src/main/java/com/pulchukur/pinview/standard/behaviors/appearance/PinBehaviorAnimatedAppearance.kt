package com.pulchukur.pinview.standard.behaviors.appearance

import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import com.pulchukur.pinview.PinView
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimator

class PinBehaviorAnimatedAppearance(
    targetView: View,
    private val animator: PinAnimator
) : PinView.VisualBehavior(targetView) {

    private var lastPin: String? = null

    init {
        targetView.isInvisible = true
    }

    override fun onStateChanged(index: Int, state: PinView.ItemState) {
        val view = targetView
        val textView = (view as? TextView)

        val newPin: String = when (state) {
            is PinView.ItemState.InActiveFilled-> state.pin
            is PinView.ItemState.Error-> state.pin
            is PinView.ItemState.Success-> state.pin
            is PinView.ItemState.InActiveEmpty -> ""
            is PinView.ItemState.Active -> ""

        }
        if (newPin == lastPin) return

        if (newPin.isNotBlank()) animator.animate(
            isAppear = true,
            target = view,
            onStart = {
                textView?.text = newPin
                view.isInvisible = false
            },
            onEnd = { }
        ) else animator.animate(
            isAppear = false,
            target = view,
            onStart = { },
            onEnd = {
                textView?.text = ""
                view.isInvisible = true
            }
        )
        lastPin = newPin


    }
}