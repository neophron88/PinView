package com.pulchukur.pinview.utils

import android.animation.Animator
import android.util.Log
import android.view.ViewPropertyAnimator


internal inline fun ViewPropertyAnimator.setListener(
    crossinline onStart: (animation: Animator) -> Unit = {},
    crossinline onEnd: (animation: Animator) -> Unit = {},
    crossinline onCancel: (animation: Animator) -> Unit = {},
    crossinline onRepeat: (animation: Animator) -> Unit = {}
) = setListener(object : Animator.AnimatorListener {

    override fun onAnimationStart(animation: Animator) = onStart(animation)

    override fun onAnimationEnd(animation: Animator){
        onEnd(animation)
    }

    override fun onAnimationCancel(animation: Animator){
        onCancel(animation)
    }

    override fun onAnimationRepeat(animation: Animator) = onRepeat(animation)

})