package com.pulchukur.pinview.standard.behaviors.appearance

import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import com.pulchukur.pinview.PinView

class PinBehaviorAppearance(
    targetView: View,
) : PinView.VisualBehavior(targetView) {

    private var lastPin: String? = null

    init {
        targetView.isInvisible = true
    }

    override fun onStateChanged(state: PinView.ItemState) {
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

        textView?.text = newPin
        targetView.isInvisible = newPin.isBlank()
    }
}