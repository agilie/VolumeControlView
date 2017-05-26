package com.agilie.controller.animation.shape

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.agilie.controller.animation.Painter
import kotlin.concurrent.thread


class BackGroundCircle : Painter() {

    companion object {
        val DELTA_TIME = 2.0f
        val INCREASE_FACTOR = 4.0
        val DECREASE_FACTOR = 4.0
    }

    var blur_mask_radius = 40f
    var incremenator = Incremenator()

    init {
        paint = setCirclePaint()
        incremenator.start()
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
        blurMaskFilter = BlurMaskFilter(blur_mask_radius, BlurMaskFilter.Blur.OUTER)
        paint = Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = INNER_CIRCLE_STROKE_WIDTH / 2
            maskFilter = blurMaskFilter
        }
        return paint as Paint
    }

    fun increaseBackLight(increase: Boolean) {
        when (increase) {
            true ->
                // onIncrease()
                incremenator.changeAction(1)
            false ->
                // onDecrease()
                incremenator.changeAction(-1)
        }
    }

    private fun onIncrease() {
        var nextRadius = blur_mask_radius
        var time = 0.0
        thread {
            do {
                nextRadius += Math.pow(time, INCREASE_FACTOR).toFloat()
                time += DELTA_TIME
                paint?.maskFilter = BlurMaskFilter(nextRadius, BlurMaskFilter.Blur.OUTER)

                Log.d("Incremenator", "onIncrease nextRadius = " + nextRadius)
            } while (nextRadius < INNER_CIRCLE_STROKE_WIDTH)
        }
    }

    private fun onDecrease() {
        var nextRadius = blur_mask_radius
        var time = 0.0

        thread {
            while (nextRadius > 0) {
                time += DELTA_TIME
                paint?.maskFilter = BlurMaskFilter(nextRadius, BlurMaskFilter.Blur.OUTER)
                nextRadius -= Math.pow(time, DECREASE_FACTOR).toFloat()

                Log.d("Incremenator", "onDecrease nextRadius = " + nextRadius)
            }
        }
    }


    inner class Incremenator : Thread() {

        @Volatile
        private var isIncrement = 0
        private var isAction = true

        fun changeAction(increase: Int) {
            isIncrement = increase

        }

        override fun run() {
            do {
                sleep(16)

                when (isIncrement) {
                    1 -> onIncrement()
                    -1 -> onDecrement()
                    else -> isAction = false
                }

            } while (!Thread.interrupted())
        }

        private fun onIncrement() {
            blur_mask_radius += Math.pow(DELTA_TIME.toDouble(), INCREASE_FACTOR).toFloat()

            if (blur_mask_radius < 320) {
                paint?.maskFilter = BlurMaskFilter(blur_mask_radius, BlurMaskFilter.Blur.OUTER)
                Log.d("Incremenator", "onIncrement BackGroundCircle.blur_mask_radius = " + blur_mask_radius)
            } else {
                isAction = false
            }
        }

        private fun onDecrement() {
            blur_mask_radius -= Math.pow(DELTA_TIME.toDouble(), INCREASE_FACTOR).toFloat()

            if (blur_mask_radius > 0) {
                paint?.maskFilter = BlurMaskFilter(blur_mask_radius, BlurMaskFilter.Blur.OUTER)
                Log.d("Incremenator", "onDecrement BackGroundCircle.blur_mask_radius = " + blur_mask_radius)
            } else {
                isAction = false
            }
        }
    }
}


