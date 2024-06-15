package com.pulchukur.pinview.standard.behaviors.appearance.animation

import android.view.View

interface PinAnimator {

    fun animate(isAppear: Boolean, target: View, onStart: Runnable?, onEnd: Runnable?)
}