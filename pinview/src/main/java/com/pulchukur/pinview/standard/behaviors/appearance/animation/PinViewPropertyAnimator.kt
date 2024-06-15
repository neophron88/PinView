@file:Suppress("FunctionName")

package com.pulchukur.pinview.standard.behaviors.appearance.animation

import android.animation.TimeInterpolator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.LinearInterpolator
import com.pulchukur.pinview.utils.DEFAULT_PIN_ANIM_DURATION

abstract class PinAppearanceViewPropertyAnimator(
    private val animationDuration: Long,
    private val interpolator: TimeInterpolator,
) : PinAnimator {


    override fun animate(isAppear: Boolean, target: View, onStart: Runnable?, onEnd: Runnable?) {
        target.animate().cancel()
        val animator = target.animate()
            .setInterpolator(interpolator)
            .withEndAction(onEnd)
            .withStartAction(onStart)
            .setDuration(animationDuration)

        if (isAppear) appear(animator, target)
        else disappear(animator, target)
    }

    abstract fun appear(animator: ViewPropertyAnimator, target: View)

    abstract fun disappear(animator: ViewPropertyAnimator, target: View)

}

typealias OnPropertyAnimation = (animator: ViewPropertyAnimator, target: View) -> Unit

inline fun PinAppearanceViewPropertyAnimator(
    animationDuration: Long = 200L,
    interpolator: TimeInterpolator = LinearInterpolator(),
    crossinline appearing: OnPropertyAnimation,
    crossinline disappearing: OnPropertyAnimation,
) = object : PinAppearanceViewPropertyAnimator(animationDuration, interpolator) {


    override fun appear(animator: ViewPropertyAnimator, target: View) =
        appearing(animator, target)


    override fun disappear(animator: ViewPropertyAnimator, target: View) =
        disappearing(animator, target)
}

fun PinAnimFromTop(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, _ -> animate.translationY(0f).start() },
    disappearing = { _, target ->
        target.translationY = (-translationRelativeTo.height.toFloat())
    }
)

fun PinAnimFromTopToBottom(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, target ->
        target.translationY = (-translationRelativeTo.height.toFloat())
        animate.translationY(0f).start()
    },
    disappearing = { animate, _ ->
        animate.translationY(translationRelativeTo.height.toFloat()).start()
    }
)

fun PinAnimationFromTopToTop(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, _ ->
        animate.translationY(0f).start()
    },
    disappearing = { animate, _ ->
        animate.translationY(-translationRelativeTo.height.toFloat()).start()
    }
)

fun PinAnimFromTopToScaleDecreasing(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, target ->
        target.apply {
            translationY = (-translationRelativeTo.height.toFloat())
            scaleX = 1f
            scaleY = 1f
        }
        animate.translationY(0f).start()
    },
    disappearing = { animate, _ -> animate.scaleX(0f).scaleY(0f).start() }
)


fun PinAnimFromBottom(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, _ -> animate.translationY(0f).start() },
    disappearing = { _, target -> target.translationY = (translationRelativeTo.height.toFloat()) }
)

fun PinAnimFromBottomToTop(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, target ->
        target.translationY = (translationRelativeTo.height.toFloat())
        animate.translationY(0f).start()
    },
    disappearing = { animate, _ ->
        animate.translationY(-translationRelativeTo.height.toFloat()).start()
    }
)

fun PinAnimFromBottomToBottom(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, _ -> animate.translationY(0f).start() },
    disappearing = { animate, _ ->
        animate.translationY(translationRelativeTo.height.toFloat()).start()
    }
)

fun PinAnimFromBottomToScaleDecreasing(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, target ->
        target.apply {
            translationY = translationRelativeTo.height.toFloat()
            scaleX = 1f
            scaleY = 1f
        }

        animate.translationY(0f).start()
    },
    disappearing = { animate, _ -> animate.scaleX(0f).scaleY(0f).start() }

)


fun PinAnimFromScaleIncreasingToTop(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, target ->
        target.apply {
            scaleX = 0f
            scaleY = 0f
            translationY = 0f
        }
        animate.scaleY(1f).scaleX(1f).start()
    },
    disappearing = { animate, _ ->
        animate.translationY(-translationRelativeTo.height.toFloat()).start()
    }
)

fun PinAnimFromScaleIncreasingToBottom(
    translationRelativeTo: View,
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, target ->
        target.apply {
            scaleX = 0f
            scaleY = 0f
            translationY = 0f
        }
        animate.scaleY(1f).scaleX(1f).start()
    },
    disappearing = { animate, _ ->
        animate.translationY(translationRelativeTo.height.toFloat()).start()
    }
)

fun PinFromAnimScaleIncreasing(
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, _ -> animate.scaleY(1f).scaleX(1f).start() },
    disappearing = { _, target ->
        target.apply {
            scaleX = 0f
            scaleY = 0f
        }
    }
)


fun PinAnimScale(
    animationDuration: Long = DEFAULT_PIN_ANIM_DURATION,
    interpolator: TimeInterpolator = LinearInterpolator(),
) = PinAppearanceViewPropertyAnimator(
    animationDuration = animationDuration,
    interpolator = interpolator,
    appearing = { animate, _ -> animate.scaleY(1f).scaleX(1f).start() },
    disappearing = { animate, _ -> animate.scaleY(0f).scaleX(0f).start() }
)