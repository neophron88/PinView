
package com.pulchukur.pinview.standard.behaviors.appearance.animation

import android.animation.TimeInterpolator
import android.view.View
import android.view.animation.LinearInterpolator
import com.pulchukur.pinview.utils.DEFAULT_PIN_ANIM_DURATION

abstract class PinAppearanceAnimator : PinAnimator {

    override fun animate(isAppear: Boolean, target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate().cancel()
        if (isAppear) {
            onPreAppear(target)
            appear(target, onStart, onEnd)
        } else {
            disappear(target, onStart, onEnd)
        }
    }

    open fun onPreAppear(target: View) {}

    abstract fun appear(target: View, onStart: Runnable?, onEnd: Runnable?)

    abstract fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?)

}


class PinAnimFromTop(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {


    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(0f).start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        onStart?.run()
        target.translationY = (-translationRelativeTo.height.toFloat())
        onEnd?.run()
    }
}

class PinAnimFromTopToBottom(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {


    override fun onPreAppear(target: View) {
        target.translationY = (-translationRelativeTo.height.toFloat())
    }

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(0f).start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(translationRelativeTo.height.toFloat()).start()
    }
}

class PinAnimationFromTopToTop(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {


    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(0f).start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(-translationRelativeTo.height.toFloat()).start()
    }
}

class PinAnimFromTopToScaleDecreasing(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {


    override fun onPreAppear(target: View) {
        target.apply {
            translationY = (-translationRelativeTo.height.toFloat())
            scaleX = 1f
            scaleY = 1f
        }
    }

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(0f).start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .scaleX(0f)
            .scaleY(0f)
            .start()
    }
}


class PinAnimFromBottom(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(0f).start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        onStart?.run()
        target.translationY = (translationRelativeTo.height.toFloat())
        onEnd?.run()
    }
}


class PinAnimFromBottomToTop(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {


    override fun onPreAppear(target: View) {
        target.translationY = (translationRelativeTo.height.toFloat())
    }

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(0f).start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(-translationRelativeTo.height.toFloat()).start()
    }
}


class PinAnimFromBottomToBottom(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(0f).start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(translationRelativeTo.height.toFloat()).start()
    }
}

class PinAnimFromBottomToScaleDecreasing(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {

    override fun onPreAppear(target: View) {
        target.apply {
            translationY = translationRelativeTo.height.toFloat()
            scaleX = 1f
            scaleY = 1f
        }
    }

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(0f).start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .scaleX(0f).scaleY(0f).start()
    }
}


class PinAnimFromScaleIncreasingToTop(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {

    override fun onPreAppear(target: View) {
        target.apply {
            scaleX = 0f
            scaleY = 0f
            translationY = 0f
        }
    }

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .scaleY(1f)
            .scaleX(1f)
            .start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(-translationRelativeTo.height.toFloat()).start()
    }
}


class PinAnimFromScaleIncreasingToBottom(
    private val translationRelativeTo: View,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {

    override fun onPreAppear(target: View) {
        target.apply {
            scaleX = 0f
            scaleY = 0f
            translationY = 0f
        }
    }

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .scaleY(1f)
            .scaleX(1f)
            .start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .translationY(translationRelativeTo.height.toFloat()).start()
    }
}


class PinFromAnimScaleIncreasing(
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {


    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .scaleY(1f)
            .scaleX(1f)
            .start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        onStart?.run()
        target.apply {
            scaleX = 0f
            scaleY = 0f
        }
        onEnd?.run()
    }
}

class PinAnimScale(
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .scaleY(1f)
            .scaleX(1f)
            .start()
    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .scaleY(0f)
            .scaleX(0f)
            .start()
    }
}


class PinBubbleAnim(
    private val sizeOfIncreaseOnAppear: Float = 1.5f,
    private val animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    private val interpolator: TimeInterpolator = LinearInterpolator()
) : PinAppearanceAnimator() {

    override fun appear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .scaleY(sizeOfIncreaseOnAppear)
            .scaleX(sizeOfIncreaseOnAppear)
            .withStartAction(onStart)
            .withEndAction {
                target.animate()
                    .setInterpolator(interpolator)
                    .setDuration(animationDuration)
                    .withEndAction(onEnd)
                    .scaleY(1f).scaleX(1f).start()
            }.start()

    }


    override fun disappear(target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate()
            .setInterpolator(interpolator)
            .setDuration(animationDuration)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .scaleY(0f)
            .scaleX(0f)
            .start()
    }
}