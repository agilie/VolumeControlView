package com.agilie.controller

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.agilie.controller.animation.controller.ControllerImpl
import kotlinx.android.synthetic.main.activity_controller.*

class ControllerActivity : AppCompatActivity() {

    companion object {
        fun getCallingIntent(context: android.content.Context) = Intent(context, ControllerActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        controller_view.backgroundLayoutColor = Color.parseColor("#000000")
        controller_view.controller?.onTouchControllerListener = (object : ControllerImpl.OnTouchControllerListener {
            override fun onControllerDown(angle: Int) {


            }

            override fun onControllerMove(angle: Int) {

            }

            override fun onAngleChange(angle: Int) {
                when (angle) {
                    in 0..45 -> controller_view.setBackgroundShiningColor(Color.parseColor("#FF7F00"))
                    in 46..90 -> controller_view.setBackgroundShiningColor(Color.parseColor("#9FFF00"))
                    in 91..135 -> controller_view.setBackgroundShiningColor(Color.parseColor("#FACC00"))
                    in 136..180 -> controller_view.setBackgroundShiningColor(Color.parseColor("#3B9800"))
                    in 181..225 -> controller_view.setBackgroundShiningColor(Color.parseColor("#00493D"))
                    in 226..270 -> controller_view.setBackgroundShiningColor(Color.parseColor("#E7FBE1"))
                    in 271..315 -> controller_view.setBackgroundShiningColor(Color.parseColor("#53FFFF"))
                    in 316..360 -> controller_view.setBackgroundShiningColor(Color.parseColor("#FF7F00"))
                }
            }
        })

    }
}
