package com.pulchukur.pinview.xml

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import com.pulchukur.pinview.PinView
import com.pulchukur.pinview.R
import com.pulchukur.pinview.standard.behaviors.appearance.PinBehaviorAnimatedAppearance
import com.pulchukur.pinview.standard.behaviors.appearance.PinBehaviorAppearance
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimatorFactory
import com.pulchukur.pinview.standard.behaviors.transition.PinBehaviorTransitionSolid
import com.pulchukur.pinview.standard.behaviors.transition.PinBehaviorTransitionStroke
import com.pulchukur.pinview.standard.behaviors.transition.PinBehaviorTransitionNone
import com.pulchukur.pinview.standard.behaviors.cursor.PinBehaviorCursor
import com.pulchukur.pinview.utils.DEFAULT_DRAWABLE_TRANSITION_DURATION
import com.pulchukur.pinview.utils.DEFAULT_PIN_ANIM_DURATION
import com.pulchukur.pinview.utils.getColor
import com.pulchukur.pinview.utils.getResourceId
import com.pulchukur.pinview.xml.enums.PinAnimationType
import com.pulchukur.pinview.xml.enums.PinDrawableColorType
import com.pulchukur.pinview.xml.enums.PinShape


internal class VisualBehaviorsFromXmlAttrs {

    @ColorInt var pinColorActive: Int = 0
    @ColorInt var pinColorEmptyInactive: Int = 0
    @ColorInt var pinColorFilledInactive: Int = 0
    @ColorInt var pinColorSuccess: Int = 0
    @ColorInt var pinColorError: Int = 0
    @ColorInt var pinStrokeColor: Int = 0
    @ColorInt var pinSolidColor: Int = 0
    var pinCornersTopRightRadius: Float = 0f
    var pinCornersTopLeftRadius: Float = 0f
    var pinCornersBottomLeftRadius: Float = 0f
    var pinCornersBottomRightRadius: Float = 0f
    var pinStrokeWidth = 0
    var pinDrawableColorChangingAttr: PinDrawableColorType = PinDrawableColorType.None
    var colorTransitionDuration = DEFAULT_DRAWABLE_TRANSITION_DURATION
    var isColorSmoothTransitionEnabled: Boolean = true
    var pinShape: PinShape = PinShape.Rectangle
    var pinAnimationType: PinAnimationType = PinAnimationType.Scale
    var pinAnimationDuration: Long = DEFAULT_PIN_ANIM_DURATION
    @IdRes var pinIdViewToWhichToApplyDrawable: Int? = null
    @IdRes var pinIdCursorView: Int? = null
    @IdRes var pinIdViewThatAppears: Int? = null

    fun fetchAttrs(context: Context, attrs: AttributeSet?) {
        if (attrs == null) return
        val colorPrimary = context.theme.getColor(androidx.appcompat.R.attr.colorPrimary)
        context.obtainStyledAttributes(attrs, R.styleable.PinView).also {
            pinColorActive = it.getColor(R.styleable.PinView_pinTransitionBehaviorColorActive, colorPrimary)
            pinColorFilledInactive = it.getColor(R.styleable.PinView_pinTransitionBehaviorColorFilledInactive, pinColorActive)
            pinColorEmptyInactive = it.getColor(R.styleable.PinView_pinTransitionBehaviorColorEmptyInactive, Color.TRANSPARENT)
            pinColorError = it.getColor(R.styleable.PinView_pinTransitionBehaviorColorError, Color.RED)
            pinColorSuccess = it.getColor(R.styleable.PinView_pinTransitionBehaviorColorSuccess, Color.GREEN)
            val cornersRadius = it.getDimension(R.styleable.PinView_pinTransitionBehaviorDrawableShapeCornersRadius, 0f)
            pinCornersTopRightRadius = it.getDimension(R.styleable.PinView_pinTransitionBehaviorDrawableShapeCornerTopRightRadius, cornersRadius)
            pinCornersTopLeftRadius = it.getDimension(R.styleable.PinView_pinTransitionBehaviorDrawableShapeCornerTopLeftRadius, cornersRadius)
            pinCornersBottomLeftRadius = it.getDimension(R.styleable.PinView_pinTransitionBehaviorDrawableShapeCornerBottomLeftRadius, cornersRadius)
            pinCornersBottomRightRadius = it.getDimension(R.styleable.PinView_pinTransitionBehaviorDrawableShapeCornerBottomRightRadius, cornersRadius)
            pinStrokeWidth = it.getDimension(R.styleable.PinView_pinTransitionBehaviorDrawableShapeStrokeWidth, 0f).toInt()
            pinStrokeColor = it.getColor(R.styleable.PinView_pinTransitionBehaviorDrawableShapeStrokeColor, Color.TRANSPARENT)
            pinSolidColor = it.getColor(R.styleable.PinView_pinTransitionBehaviorDrawableShapeSolidColor, Color.TRANSPARENT)
            isColorSmoothTransitionEnabled = it.getBoolean(R.styleable.PinView_pinTransitionBehaviorSmoothColorTransitionEnabled, true)
            colorTransitionDuration = it.getInt(R.styleable.PinView_pinTransitionBehaviorSmoothColorTransitionDuration, DEFAULT_DRAWABLE_TRANSITION_DURATION.toInt()).toLong()
            val bgAttrIndex = it.getInt(R.styleable.PinView_pinTransitionBehaviorDrawableShapeColorTransitionAttr, PinDrawableColorType.None.ordinal)
            pinDrawableColorChangingAttr = PinDrawableColorType.values()[bgAttrIndex]
            val shape = it.getInt(R.styleable.PinView_pinTransitionBehaviorDrawableShape, PinShape.Rectangle.ordinal)
            pinShape = PinShape.values()[shape]
            val animAttrIndex = it.getInt(R.styleable.PinView_pinAppearanceBehaviorAnimation, PinAnimationType.Scale.ordinal)
            pinAnimationType = PinAnimationType.values()[animAttrIndex]
            pinAnimationDuration = it.getInt(R.styleable.PinView_pinAppearanceBehaviorAnimationDuration, DEFAULT_PIN_ANIM_DURATION.toInt()).toLong()
            pinIdViewThatAppears = it.getResourceId(R.styleable.PinView_pinAppearanceBehaviorApplyToViewWithId, null)
            pinIdCursorView = it.getResourceId(R.styleable.PinView_pinCursorBehaviorApplyToViewWithId, null)
            pinIdViewToWhichToApplyDrawable = it.getResourceId(R.styleable.PinView_pinTransitionBehaviorApplyToViewWithId, null)
        }.recycle()
    }


    companion object {

        fun addBehaviorsToPinView(pinView: PinView, attrs: AttributeSet?) {

            if (pinView.pinItemLayout == null) return

            val xmlAttrs = VisualBehaviorsFromXmlAttrs()
            xmlAttrs.fetchAttrs(pinView.context, attrs)

            if (pinView.isInEditMode) {
                xmlAttrs.pinAnimationType = PinAnimationType.None
                xmlAttrs.isColorSmoothTransitionEnabled = false
            }

            xmlAttrs.pinIdViewToWhichToApplyDrawable?.let { idView ->
                pinView.pinAddVisualBehaviorProducer(idView) { _, targetView ->
                    createDrawableColorTransitionBehavior(targetView, xmlAttrs)
                }
            }

            xmlAttrs.pinIdCursorView?.let { idView ->
                pinView.pinAddVisualBehaviorProducer(idView) { position, targetView ->
                    PinBehaviorCursor(position, targetView)
                }
            }

            xmlAttrs.pinIdViewThatAppears?.let { idView ->
                pinView.pinAddVisualBehaviorProducer(idView) { position, targetView ->
                    val animator = PinAnimatorFactory.produce(
                        attrs = xmlAttrs,
                        pinRoot = pinView.pinItems[position]
                    )
                    if (animator == null) PinBehaviorAppearance(targetView)
                    else PinBehaviorAnimatedAppearance(targetView, animator)
                }
            }
            pinView.pinInvalidate()
        }

        private fun createDrawableColorTransitionBehavior(
            targetView: View,
            xmlAttrs: VisualBehaviorsFromXmlAttrs
        ): PinView.VisualBehavior {
            return when (xmlAttrs.pinDrawableColorChangingAttr) {
                PinDrawableColorType.Stroke ->
                    PinBehaviorTransitionStroke(
                        targetView,
                        drawable = xmlAttrs.createGradientDrawable(),
                        strokeWidth = xmlAttrs.pinStrokeWidth,
                        activeColor = xmlAttrs.pinColorActive,
                        inActiveFilledColor = xmlAttrs.pinColorFilledInactive,
                        inActiveEmptyColor = xmlAttrs.pinColorEmptyInactive,
                        successColor = xmlAttrs.pinColorSuccess,
                        errorColor = xmlAttrs.pinColorError,
                        isSmoothColorTransitionEnabled = xmlAttrs.isColorSmoothTransitionEnabled,
                        colorTransitionDuration = xmlAttrs.colorTransitionDuration
                    )

                PinDrawableColorType.Solid -> PinBehaviorTransitionSolid(
                    targetView,
                    drawable = xmlAttrs.createGradientDrawable(),
                    activeColor = xmlAttrs.pinColorActive,
                    inActiveFilledColor = xmlAttrs.pinColorFilledInactive,
                    inActiveEmptyColor = xmlAttrs.pinColorEmptyInactive,
                    successColor = xmlAttrs.pinColorSuccess,
                    errorColor = xmlAttrs.pinColorError,
                    isSmoothColorTransitionEnabled = xmlAttrs.isColorSmoothTransitionEnabled,
                    colorTransitionDuration = xmlAttrs.colorTransitionDuration,
                )

                PinDrawableColorType.None ->
                    PinBehaviorTransitionNone(targetView, xmlAttrs.createGradientDrawable())
            }
        }

        private fun VisualBehaviorsFromXmlAttrs.createGradientDrawable() =
            GradientDrawable().apply {
                shape = when (pinShape) {
                    PinShape.Rectangle -> GradientDrawable.RECTANGLE
                    PinShape.Oval -> GradientDrawable.OVAL
                }
                cornerRadii = floatArrayOf(
                    pinCornersTopLeftRadius, pinCornersTopLeftRadius,
                    pinCornersTopRightRadius, pinCornersTopRightRadius,
                    pinCornersBottomRightRadius, pinCornersBottomRightRadius,
                    pinCornersBottomLeftRadius, pinCornersBottomLeftRadius
                )
                setStroke(pinStrokeWidth, pinStrokeColor)
                setColor(pinSolidColor)
            }
    }
}
