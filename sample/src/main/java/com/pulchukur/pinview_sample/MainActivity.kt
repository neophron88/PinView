package com.pulchukur.pinview_sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.pulchukur.pinview.PinView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pinView = findViewById<PinView>(R.id.pin_view_5)

        pinView.pinAddVisualBehaviorProducer(R.id.placeholder) { position, target ->
            PinBehaviorPlaceholder(target)
        }
        pinView.pinRecompose()
    }
}

class PinBehaviorPlaceholder(targetView: View) : PinView.VisualBehavior(targetView) {

    override fun onStateChanged(state: PinView.ItemState) {
        targetView.isInvisible = state is PinView.ItemState.InActiveFilled
    }
}

