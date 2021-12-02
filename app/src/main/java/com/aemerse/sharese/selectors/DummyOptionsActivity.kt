package com.aemerse.sharese.selectors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dummy_options.*
import com.aemerse.sharese.R
import com.aemerse.sharese.zshare_helpers.setupSendOptions

class DummyOptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy_options)

        setupSendOptions(sendOptions)
    }
}