package com.agilie.controller.animation.shape

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Paint
import com.agilie.controller.animation.Painter


class BackGroundCircle : Painter() {

    companion object {
        val DELTA_TIME = 0.000_00_4
        val INCREASE_FACTOR = 4.0
        val DECREASE_FACTOR = 4.0

        var blur_mask_radius = 40f
        var paint: Paint? = null
        var incremenator = Incremenator()
    }


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
            true -> incremenator.changeAction(1)
            false -> incremenator.changeAction(-1)
        }
    }

    /* private fun onIncrease() {
         var nextRadius = blur_mask_radius
         var time = 0.0
         if (!increase)
             return
         thread {
             do {
                 nextRadius += Math.pow(time, INCREASE_FACTOR).toFloat()
                 time += DELTA_TIME
                 paint?.maskFilter = BlurMaskFilter(nextRadius, BlurMaskFilter.Blur.OUTER)
             } while (nextRadius < INNER_CIRCLE_STROKE_WIDTH)
         }
     }*/

    /*private fun onDecrease() {
        var nextRadius = blur_mask_radius
        var time = 0.0

        thread {
            while (nextRadius > 0) {
                nextRadius -= Math.pow(time, DECREASE_FACTOR).toFloat()
                time += DELTA_TIME
                paint?.maskFilter = BlurMaskFilter(nextRadius, BlurMaskFilter.Blur.OUTER)
            }
        }
    }*/

    class Incremenator : Thread() {

        @Volatile
        private var isIncrement = 0
        private var isAction = true

        fun changeAction(increase: Int) {
            isIncrement = increase

        }

        override fun run() {
            do {
                do {
                    when (isIncrement) {
                        1 -> onIncrement()
                        -1 -> onDecrement()
                        else -> isAction = false
                    }

                } while (!Thread.interrupted())
            } while (true)
        }

        private fun onIncrement() {
            var time = 0.0
            do {
                BackGroundCircle.blur_mask_radius += Math.pow(time, DECREASE_FACTOR).toFloat()
                time += DELTA_TIME
                BackGroundCircle.paint?.maskFilter = BlurMaskFilter(BackGroundCircle.blur_mask_radius, BlurMaskFilter.Blur.OUTER)

            } while (BackGroundCircle.blur_mask_radius < INNER_CIRCLE_STROKE_WIDTH && isAction)
        }

        private fun onDecrement() {
            var time = 0.0

            while (BackGroundCircle.blur_mask_radius > 0 && isAction) {
                BackGroundCircle.blur_mask_radius -= Math.pow(time, INCREASE_FACTOR).toFloat()
                time += DELTA_TIME
                paint?.maskFilter = BlurMaskFilter(BackGroundCircle.blur_mask_radius, BlurMaskFilter.Blur.OUTER)
            }
        }
    }

}


