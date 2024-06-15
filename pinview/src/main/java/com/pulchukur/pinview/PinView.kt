package com.pulchukur.pinview

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.Selection
import android.text.Spannable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.pulchukur.pinview.utils.ChangeInfo
import com.pulchukur.pinview.utils.DEFAULT_PIN_COUNT
import com.pulchukur.pinview.utils.EMPTY_PIN
import com.pulchukur.pinview.utils.EachItemTargetBehaviors
import com.pulchukur.pinview.utils.createNewOrAddToExistingList
import com.pulchukur.pinview.utils.getResourceId
import com.pulchukur.pinview.utils.onTextChanged
import com.pulchukur.pinview.utils.runDependingOnLayoutPhase
import com.pulchukur.pinview.xml.VisualBehaviorsFromXmlAttrs


class PinView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val changeInfo = ChangeInfo()
    private val pinsContainer: LinearLayout
    private val pinsInputSource: AppCompatTextView
    private val oldBehaviorProducersByViewIds = SparseArray<MutableList<VisualBehaviorProducer>>()
    private val newBehaviorProducersByViewIds = SparseArray<MutableList<VisualBehaviorProducer>>()
    private val attachedBehaviorsByViewIds = SparseArray<MutableList<EachItemTargetBehaviors>>()
    private val pinTextChangeListeners: MutableList<PinChangeListener> = mutableListOf()
    private val _pinItems: MutableList<View> = mutableListOf()
    val pinItems: List<View> get() = _pinItems

    @LayoutRes
    var pinItemLayout: Int? = null
        set(value) {
            if (field == value) return
            field = value
            changeInfo.isPinLayoutChanged = true
        }

    @LayoutRes
    var pinDecorationLayout: Int? = null
        set(value) {
            if (field == value) return
            field = value
            changeInfo.isPinDecorationChanged = true
        }

    var pinDecorationPositions: List<Int> = listOf()
        set(value) {
            if (field == value) return
            field = value
            changeInfo.isPinDecorationChanged = true
        }

    var pinCount: Int = 0
        set(value) {
            if (field == value) return
            field = value
            changeInfo.isPinCountChanged = true
        }

    var pinText: String
        get() = pinsInputSource.text.toString()
        set(value) {
            pinsInputSource.text = value
        }

    var pinImeOptions: Int
        get() = pinsInputSource.imeOptions
        set(value) {
            pinsInputSource.imeOptions = value
        }

    var pinInputType: Int
        get() = pinsInputSource.inputType
        set(value) {
            pinsInputSource.inputType = value
        }

    var forcedState: State = State.Default
        set(value) {
            if (field == value) return
            field = value
            handleInputSourceTextChangeDependingOnLayoutPhase(pinsInputSource.text)
        }


    init {
        LayoutInflater.from(context).inflate(R.layout.pin_view_layout, this)
        pinsContainer = findViewById(R.id.pins_container)
        pinsInputSource = findViewById(R.id.pin_input_source)
        fetchAttrs(attrs)
        setupPinsInputSource()
        VisualBehaviorsFromXmlAttrs.addBehaviorsToPinView(this, attrs)
        setupInEditMode()
    }


    private fun fetchAttrs(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.PinView).also { typedArray ->
            pinCount = typedArray.getInt(R.styleable.PinView_pinCount, DEFAULT_PIN_COUNT)
            pinItemLayout = typedArray.getResourceId(R.styleable.PinView_pinItemLayout, null)
            pinDecorationLayout =
                typedArray.getResourceId(R.styleable.PinView_pinDecorationLayout, null)
            pinDecorationPositions = typedArray.let { _ ->
                val string = typedArray.getString(R.styleable.PinView_pinDecorationPositions)
                val data = string?.split(",") ?: listOf()
                data.filter { it.isNotBlank() }.map { it.toInt() }
            }
            pinImeOptions =
                typedArray.getInt(R.styleable.PinView_pinImeOptions, EditorInfo.IME_ACTION_DONE)
            pinInputType =
                typedArray.getInt(R.styleable.PinView_pinInputType, InputType.TYPE_CLASS_NUMBER)
        }.recycle()
    }

    private fun setupPinsInputSource() = pinsInputSource.apply {
        filters = arrayOf(InputFilter.LengthFilter(pinCount))
        setOnClickListener { Selection.setSelection(text as Spannable, text.length) }
        setOnFocusChangeListener { _, _ ->
            Selection.setSelection(text as Spannable, text.length)
            handleInputSourceTextChangeDependingOnLayoutPhase(text)
        }
        onTextChanged { text, _, _, _ ->
            if (text == null) return@onTextChanged
            handleInputSourceTextChangeDependingOnLayoutPhase(text)
            pinTextChangeListeners.forEach { it.onPinChange(text.toString()) }
        }
    }


    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        pinsInputSource.isEnabled = enabled
    }

    private fun inflateItemsAndDecors() {
        val itemsAndDecors = mutableListOf<View>()
        inflateItems(itemsAndDecors)
        inflateDecors(itemsAndDecors)
        itemsAndDecors.forEach { pinsContainer.addView(it) }
    }


    private fun inflateItems(itemsAndDecors: MutableList<View>) = pinItemLayout?.let {
        val layoutInflater = LayoutInflater.from(context)
        for (i in 0 until pinCount) {
            val view = layoutInflater.inflate(it, pinsContainer, false)
            itemsAndDecors.add(view)
            _pinItems.add(view)
        }
    }

    private fun inflateDecors(itemsAndDecors: MutableList<View>) = pinDecorationLayout?.let {
        val layoutInflater = LayoutInflater.from(context)
        pinDecorationPositions.distinct().forEach { index ->
            if (index > itemsAndDecors.size) return@forEach
            val view = layoutInflater.inflate(it, pinsContainer, false)
            itemsAndDecors.add(index, view)
        }
    }

    private fun handleInputSourceTextChange(text: CharSequence) {
        for (i in 0 until _pinItems.size) {

            val pin = (text.getOrNull(i) ?: EMPTY_PIN).toString().trim()

            val hasPinsInputSourceFocus = pinsInputSource.hasFocus()
            val isPinActive = i == text.length
            val isPinEmpty = pin.isBlank()

            val newState =
                if (forcedState == State.Error) ItemState.Error(pin)
                else if (forcedState == State.Success) ItemState.Success(pin)
                else if (hasPinsInputSourceFocus && isPinActive) ItemState.Active
                else if (isPinEmpty) ItemState.InActiveEmpty
                else ItemState.InActiveFilled(pin)

            val size = attachedBehaviorsByViewIds.size()
            for (index in 0 until size) {
                val behaviors = attachedBehaviorsByViewIds.valueAt(index)
                behaviors.forEach {
                    it[i].stateChanged(newState)
                }
            }
        }
    }

    private fun handleInputSourceTextChangeDependingOnLayoutPhase(text: CharSequence) =
        runDependingOnLayoutPhase { handleInputSourceTextChange(text) }

    private fun setupInEditMode() {
        if (isInEditMode) {
            pinsInputSource.requestFocus()
            val text = "88"
            pinsInputSource.text = text
            handleInputSourceTextChange(pinsInputSource.text)
        }
    }

    fun pinInvalidate() {
        if (changeInfo.isPinLayoutChanged || changeInfo.isPinCountChanged || changeInfo.isPinDecorationChanged) {
            pinsContainer.removeAllViews()
            _pinItems.clear()
            attachedBehaviorsByViewIds.clear()
            inflateItemsAndDecors()

            if (changeInfo.isPinCountChanged) {
                pinsInputSource.filters = arrayOf(InputFilter.LengthFilter(pinCount))
            }

            if (changeInfo.isPinLayoutChanged) {
                oldBehaviorProducersByViewIds.clear()
            } else {
                val size = oldBehaviorProducersByViewIds.size()
                for (i in 0 until size) {
                    val viewId = oldBehaviorProducersByViewIds.keyAt(i)
                    val producers = oldBehaviorProducersByViewIds.valueAt(i)
                    val behaviors: List<EachItemTargetBehaviors> =
                        producers.produceBehaviors(viewId)
                    attachedBehaviorsByViewIds.createNewOrAddToExistingList(viewId, behaviors)
                }
            }
        }

        if (changeInfo.isAddedBehavior) {
            val size = newBehaviorProducersByViewIds.size()
            for (i in 0 until size) {
                val viewId = newBehaviorProducersByViewIds.keyAt(i)
                val producers = newBehaviorProducersByViewIds.valueAt(i)
                val behaviors: List<EachItemTargetBehaviors> = producers.produceBehaviors(viewId)
                attachedBehaviorsByViewIds.createNewOrAddToExistingList(viewId, behaviors)
                oldBehaviorProducersByViewIds.createNewOrAddToExistingList(viewId, producers)
            }
            newBehaviorProducersByViewIds.clear()
        }

        changeInfo.reset()
        handleInputSourceTextChangeDependingOnLayoutPhase(pinsInputSource.text)
    }

    private fun MutableList<VisualBehaviorProducer>.produceBehaviors(viewId: Int): List<EachItemTargetBehaviors> {
        return map { producer ->
            _pinItems.mapIndexed { position, itemRoot ->
                val targetView = itemRoot.findViewById<View>(viewId)
                    ?: error("View with such id not found")
                producer.createVisualBehavior(position, targetView)
            }
        }
    }

    fun pinAddVisualBehaviorProducer(@IdRes viewId: Int, producer: VisualBehaviorProducer) {
        newBehaviorProducersByViewIds.createNewOrAddToExistingList(viewId, listOf(producer))
        changeInfo.isAddedBehavior = true
    }

    fun pinRemoveVisualBehaviorForViewById(@IdRes viewId: Int) {
        newBehaviorProducersByViewIds.remove(viewId)
        oldBehaviorProducersByViewIds.remove(viewId)
        attachedBehaviorsByViewIds.remove(viewId)
    }

    fun pinClearVisualBehaviors() {
        newBehaviorProducersByViewIds.clear()
        oldBehaviorProducersByViewIds.clear()
        attachedBehaviorsByViewIds.clear()
    }

    fun pinAddOnTextChangeListener(listener: PinChangeListener) {
        post {
            pinTextChangeListeners.add(listener)
            pinsInputSource.text ?: return@post
            listener.onPinChange(pinsInputSource.text.toString())
        }
    }

    fun pinRemoveOnTextChangeListener(listener: PinChangeListener) {
        pinTextChangeListeners.remove(listener)
    }

    fun pinClearOnTextChangeListeners() {
        pinTextChangeListeners.clear()
    }


    sealed class ItemState {
        data object Active : ItemState()
        data object InActiveEmpty : ItemState()
        data class InActiveFilled(val pin: String) : ItemState()
        data class Error(val pin: String) : ItemState()
        data class Success(val pin: String) : ItemState()
    }

    enum class State {
        Error, Success, Default
    }

    fun interface PinChangeListener {
        fun onPinChange(text: String)
    }

    fun interface VisualBehaviorProducer {
        fun createVisualBehavior(position: Int, target: View): VisualBehavior
    }

    abstract class VisualBehavior(val targetView: View) {

        private var lastState: ItemState? = null

        internal fun stateChanged(newState: ItemState) {
            if (lastState == newState) return
            onStateChanged(newState)
            lastState = newState
        }

        abstract fun onStateChanged(state: ItemState)

    }
}
