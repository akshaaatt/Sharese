package com.aemerse.sharese

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_received.*
import com.aemerse.sharese.ui.received_files.ReceivedPagerAdapter


class ReceivedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_received)
        supportActionBar?.title = "Your received files..."
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerAdapter = ReceivedPagerAdapter(supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }

}