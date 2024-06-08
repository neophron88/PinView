package com.pulchukur.pinview.utils

import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

internal inline fun Lifecycle.onStart(crossinline block: () -> Unit): LifecycleObserver {
    val observer = object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            block()
        }
    }
    addObserver(observer)
    return observer
}

internal fun LifecycleOwner.post(
    delayMillis: Long = 0,
    handler: Handler = Handler(Looper.getMainLooper()),
    removeWhenAtLeast: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    token: Any? = null,
    run: () -> Unit
): Handler {

    if (this.lifecycle.currentState == Lifecycle.State.DESTROYED) return handler

    val observer = object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event >= removeWhenAtLeast) {
                handler.removeCallbacksAndMessages(token)
                this@post.lifecycle.removeObserver(this)
            }
        }
    }

    val runnable = Runnable {
        if (this.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            run.invoke()
            this.lifecycle.removeObserver(observer)
        }
    }

    this.lifecycle.addObserver(observer)
    HandlerCompat.postDelayed(handler, runnable, token, delayMillis)
    return handler
}