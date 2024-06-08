package com.pulchukur.pinview.standard.behaviors.appearance.animation

import android.view.View
import com.pulchukur.pinview.xml.enums.PinAnimationType.FromBottom
import com.pulchukur.pinview.xml.enums.PinAnimationType.FromBottomToBottom
import com.pulchukur.pinview.xml.enums.PinAnimationType.FromBottomToScaleDecreasing
import com.pulchukur.pinview.xml.enums.PinAnimationType.FromBottomToTop
import com.pulchukur.pinview.xml.enums.PinAnimationType.FromScaleIncreasing
import com.pulchukur.pinview.xml.enums.PinAnimationType.FromTop
import com.pulchukur.pinview.xml.enums.PinAnimationType.FromTopToBottom
import com.pulchukur.pinview.xml.enums.PinAnimationType.FromTopToScaleDecreasing
import com.pulchukur.pinview.xml.enums.PinAnimationType.FromTopToTop
import com.pulchukur.pinview.xml.enums.PinAnimationType.IncreasingScaleToBottom
import com.pulchukur.pinview.xml.enums.PinAnimationType.IncreasingScaleToTop
import com.pulchukur.pinview.xml.enums.PinAnimationType.None
import com.pulchukur.pinview.xml.enums.PinAnimationType.Scale
import com.pulchukur.pinview.xml.VisualBehaviorsFromXmlAttrs

internal object PinAnimatorFactory {

    fun produce(attrs: VisualBehaviorsFromXmlAttrs, pinRoot: View): PinAnimator? {
        val type = attrs.pinAnimationType
        val animationDuration: Long = attrs.pinAnimationDuration
        return when (type) {
            None -> null
            FromTopToTop -> PinAnimationFromTopToTop(pinRoot, animationDuration)
            FromTopToBottom -> PinAnimFromTopToBottom(pinRoot, animationDuration)
            FromTopToScaleDecreasing -> PinAnimFromTopToScaleDecreasing(pinRoot, animationDuration)
            FromBottomToBottom -> PinAnimFromBottomToBottom(pinRoot, animationDuration)
            FromBottomToTop -> PinAnimFromBottomToTop(pinRoot, animationDuration)
            FromBottomToScaleDecreasing -> PinAnimFromBottomToScaleDecreasing(pinRoot, animationDuration)
            IncreasingScaleToTop -> PinAnimFromScaleIncreasingToTop(pinRoot, animationDuration)
            IncreasingScaleToBottom -> PinAnimFromScaleIncreasingToBottom(pinRoot, animationDuration)
            Scale -> PinAnimScale(animationDuration)
            FromTop -> PinAnimFromTop(pinRoot,animationDuration)
            FromBottom -> PinAnimFromBottom(pinRoot,animationDuration)
            FromScaleIncreasing -> PinFromAnimScaleIncreasing(animationDuration)
        }
    }
}