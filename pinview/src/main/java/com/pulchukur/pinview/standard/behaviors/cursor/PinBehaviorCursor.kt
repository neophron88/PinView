package com.pulchukur.pinview.standard.behaviors.cursor

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.pulchukur.pinview.PinView
import com.pulchukur.pinview.utils.CURSOR_ANIM_DURATION
import com.pulchukur.pinview.utils.onStart
import com.pulchukur.pinview.utils.post

class PinBehaviorCursor(
    private val position: Int,
    targetView: View,
) : PinView.VisualBehavior(targetView) {

    private val cursorHandler = Handler(Looper.getMainLooper())
    private var lifecycleOwner: LifecycleOwner? = null
    private var observer: LifecycleObserver? = null

    override fun onStateChanged(state: PinView.ItemState) {
        if (lifecycleOwner == null) lifecycleOwner = targetView.findViewTreeLifecycleOwner()
        val isActive = state == PinView.ItemState.Active
        targetView.isVisible = isActive
        if (isActive) {
            targetView.animateCursor(position, 0)
            observer = lifecycleOwner?.lifecycle?.onStart {
                cursorHandler.removeCallbacksAndMessages(position)
                targetView.animateCursor(position, 0)
            }
        } else {
            cursorHandler.removeCallbacksAndMessages(position)
            val observer = this.observer
            if (observer != null) {
                lifecycleOwner?.lifecycle?.removeObserver(observer)
            }
        }
    }

    private fun View.animateCursor(token: Int, delayMillis: Long) {
        lifecycleOwner?.post(
            handler = cursorHandler,
            token = token,
            removeWhenAtLeast = Lifecycle.Event.ON_STOP,
            delayMillis = delayMillis
        ) {
            val duration = CURSOR_ANIM_DURATION
            animate()
                .alpha(0f)
                .setDuration(duration / 2)
                .withEndAction { animate().alpha(1f).setDuration(duration / 2).start() }
                .start()
            animateCursor(token, CURSOR_ANIM_DURATION)
        }
    }
}