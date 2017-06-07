package com.agilie.controller.animation.painter

import android.graphics.*
import com.agilie.controller.getPointOnBorderLineOfCircle

class BackgroundShiningImpl(val paint: Paint,
                            val paint2: Paint,
                            var colors: IntArray,
                            var colors2: IntArray) : Painter {

    private var incrementer: Incrementer? = null

    init {
        incrementer = Incrementer()
        incrementer?.start()
    }

    @Volatile
    private var currentSplash = 1.3f
    private var minShiningRadius = 1.3f
    private var maxShiningRadius = 1.5f
    private var shiningStep = 0.004f
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

    fun onSetShiningAttrs(minRadius: Float, maxRadius: Float, step: Float) {
        minShiningRadius = minRadius
        maxShiningRadius = maxRadius
        shiningStep = step
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
                time += shiningStep
                currentSplash -= time
            } else {
                time = 0f
                isIncrement = true
            }
        }

        private fun onIncrement() {
            if (currentSplash <= maxShiningRadius) {
                currentSplash += time
                time += shiningStep
            } else {
                time = 0f
                isIncrement = false
            }
        }
    }
}