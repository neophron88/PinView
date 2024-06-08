package com.pulchukur.pinview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<PinView>(R.id.pinview_placeholder).apply {
            pinAddVisualBehaviorProducer(R.id.placeholder) { position, target ->
                object : PinView.VisualBehavior(target) {
                    override fun onStateChanged(index: Int, state: PinView.ItemState) {
                        target.isVisible = state !is PinView.ItemState.InActiveFilled
                    }
                }
            }
            pinInvalidate()
        }
    }
}