package com.agilie.controller.animation.painter

import android.graphics.*
import android.util.Log
import com.agilie.controller.getPointOnBorderLineOfCircle
import com.agilie.controller.view.ControllerView.Companion.DECREASE_FACTOR
import com.agilie.controller.view.ControllerView.Companion.DELTA_TIME
import com.agilie.controller.view.ControllerView.Companion.INCREASE_FACTOR
import com.agilie.controller.view.ControllerView.Companion.MAX_FACTOR
import com.agilie.controller.view.ControllerView.Companion.MIN_FACTOR

class BackgroundShiningImpl(val paint: Paint,
                            val paint2: Paint,
                            var colors: IntArray,
                            var colors2: IntArray) : Painter {


    @Volatile
    private var factor = 1.3f
    private val incrementer = Incrementer()
    var radius: Float = 0f
    var center = PointF()
    var gradientAngle = 0


    override fun onDraw(canvas: Canvas) {

        paint.shader = RadialGradient(center.x, center.y, radius * factor, colors,
                floatArrayOf(0.01F, 0.99F), Shader.TileMode.CLAMP)

        val startPoint = getPointOnBorderLineOfCircle(center, radius, gradientAngle + 180)
        val endPoint = getPointOnBorderLineOfCircle(center, radius, gradientAngle)

        paint2.shader = LinearGradient(startPoint.x, startPoint.y, endPoint.x, endPoint.y, colors2,
                null,
                Shader.TileMode.CLAMP)

        canvas.drawCircle(center.x, center.y, radius * factor, paint)
        canvas.drawCircle(center.x, center.y, radius * factor, paint2)

    }

    override fun onSizeChanged(w: Int, h: Int) {
        incrementer.start()
    }

    inner class Incrementer : Thread() {

        @Volatile
        private var isIncrement = true
        private var time = 0.0

        override fun run() {

            do {
                sleep(16)
                Log.d("Incrementer", "--------------------------------------------------------------")
                when (isIncrement) {
                    true -> onIncrement()
                    false -> onDecrement()
                }

            } while (!Thread.interrupted())
        }

        private fun onDecrement() {
            if (factor >= MIN_FACTOR) {
                time += DELTA_TIME
                factor -= Math.pow(time, DECREASE_FACTOR).toFloat()
                Log.d("Incrementer", " Decrement factor= " + factor)
            } else {
                time = 0.0
                isIncrement = true
            }
        }

        private fun onIncrement() {
            if (factor <= MAX_FACTOR) {
                factor += Math.pow(time, INCREASE_FACTOR).toFloat()
                time += DELTA_TIME
                Log.d("Incrementer", "Increment factor= " + factor)
            } else {
                time = 0.0
                isIncrement = false
            }
        }
    }

}