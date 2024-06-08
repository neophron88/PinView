package com.pulchukur.pinview.utils

import android.view.View

inline fun View.runDependingOnLayoutPhase(crossinline action: () -> Unit) {
    if (isLaidOut && !isLayoutRequested) action()
    else post { action() }
}