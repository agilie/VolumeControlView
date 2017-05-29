package com.agilie.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ControllerActivity : AppCompatActivity() {

    companion object {
        fun getCallingIntent(context: android.content.Context) = Intent(context, ControllerActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        val circle_view = findViewById(R.id.circle_view)
        circle_view.setOnTouchListener { v, event -> false }
    }
}
