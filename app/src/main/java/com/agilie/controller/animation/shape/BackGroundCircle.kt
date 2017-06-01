/*
package com.agilie.controller.animation.shape

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Paint


class BackGroundCircle : Painter() {

    val incrementer = Incrementer()

    init {
        paint = setCirclePaint()
        incrementer.start()
    }

    override
    fun onSizeChanged(width: Int, height: Int) {
        startPoint.apply {
            x = width / 2f
            y = height / 2f
        }
        setRadius(width, height)
    }

    override fun setRadius(width: Int, height: Int) {
        if (width > height) {
            radius = height / 4f
        } else {
            radius = width / 4f
        }
    }

    private fun setCirclePaint(): Paint {
        paint = Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = INNER_CIRCLE_STROKE_WIDTH / 2
            maskFilter = BlurMaskFilter(BLUR_MASK_RADIUS, BlurMaskFilter.Blur.OUTER)
        }
        return paint as Paint
    }

    fun increaseBackLight(increase: Boolean) {
        when (increase) {
            true ->
                incrementer.changeAction(1)
            false ->
                incrementer.changeAction(-1)
        }
    }

    inner class Incrementer : Thread() {

        @Volatile
        private var isIncrement = 0
        private var mask_radius = BLUR_MASK_RADIUS
        private var time = 0.0

        fun changeAction(increase: Int) {
            isIncrement = increase
        }

        override fun run() {
            do {
                sleep(16)

                when (isIncrement) {
                    1 -> onIncrement()
                    -1 -> onDecrement()
                }

            } while (!Thread.interrupted())
        }

        private fun onIncrement() {

            if (mask_radius < OUTER_BLUR_MASK_RADIUS) {
                mask_radius += Math.pow(time, INCREASE_FACTOR).toFloat()
                time += DELTA_TIME
                paint?.maskFilter = BlurMaskFilter(mask_radius, BlurMaskFilter.Blur.OUTER)
            } else {
                time = 0.0
            }
        }

        private fun onDecrement() {

            if (mask_radius > BLUR_MASK_RADIUS) {
                time += DELTA_TIME
                mask_radius -= Math.pow(time, DECREASE_FACTOR).toFloat()
                paint?.maskFilter = BlurMaskFilter(mask_radius, BlurMaskFilter.Blur.OUTER)
            } else {
                time = 0.0
            }
        }
    }
}


*/
