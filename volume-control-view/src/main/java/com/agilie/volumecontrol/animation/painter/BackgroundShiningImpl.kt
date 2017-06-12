package com.agilie.volumecontrol.animation.painter

import android.graphics.*
import com.agilie.volumecontrol.getPointOnBorderLineOfCircle

class BackgroundShiningImpl(val paint: Paint,
                            val paint2: Paint,
                            var colors: IntArray,
                            var colors2: IntArray,
                            var minShiningRadius: Float,
                            var maxShiningRadius: Float,
                            var frequency: Float) : Painter {

    private var incrementer: Incrementer? = null

    init {
        incrementer = Incrementer()
        incrementer?.start()
    }

    @Volatile
    private var currentSplash = 1.3f
    var radius: Float = 0f
    var center = PointF()
    var gradientAngle = 0

    override fun onDraw(canvas: Canvas) {

        paint.shader = RadialGradient(center.x, center.y, radius * currentSplash, colors,
                floatArrayOf(0.01F, 0.99F), Shader.TileMode.CLAMP)

        val startPoint = getPointOnBorderLineOfCircle(center, radius, gradientAngle + 180)
        val endPoint = getPointOnBorderLineOfCircle(center, radius, gradientAngle)

        paint2.shader = LinearGradient(startPoint.x, startPoint.y, endPoint.x, endPoint.y, colors2,
                null,
                Shader.TileMode.CLAMP)

        canvas.drawCircle(center.x, center.y, radius * currentSplash, paint)
        canvas.drawCircle(center.x, center.y, radius * currentSplash, paint2)

    }

    override fun onSizeChanged(w: Int, h: Int) {
    }

    /**Class for implementing shining logic */
    inner class Incrementer : Thread() {

        @Volatile
        private var isIncrement = true
        private var time = 0f

        override fun run() {

            do {
                sleep(16)
                when (isIncrement) {
                    true -> onIncrement()
                    false -> onDecrement()
                }

            } while (!Thread.interrupted())
        }

        private fun onDecrement() {
            if (currentSplash >= minShiningRadius) {
                time += frequency
                currentSplash -= time
            } else {
                time = 0f
                isIncrement = true
            }
        }

        private fun onIncrement() {
            if (currentSplash <= maxShiningRadius) {
                currentSplash += time
                time += frequency
            } else {
                time = 0f
                isIncrement = false
            }
        }
    }
}