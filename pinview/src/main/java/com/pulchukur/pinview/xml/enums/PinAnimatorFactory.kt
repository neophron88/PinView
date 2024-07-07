package com.pulchukur.pinview.xml.enums

import android.view.View
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromBottom
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromBottomToBottom
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromBottomToScaleDecreasing
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromBottomToTop
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromScaleIncreasingToBottom
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromScaleIncreasingToTop
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromTop
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromTopToBottom
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromTopToScaleDecreasing
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimScale
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimationFromTopToTop
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAppearanceAnimator
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinBubbleAnim
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinFromAnimScaleIncreasing
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
import com.pulchukur.pinview.xml.enums.PinAnimationType.Bubble

internal object PinAnimatorFactory {

    fun produce(attrs: VisualBehaviorsFromXmlAttrs, pinRoot: View): PinAppearanceAnimator? {
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
            Bubble -> PinBubbleAnim(animationDuration = animationDuration)
        }
    }
}