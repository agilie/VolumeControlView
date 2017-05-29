package com.agilie.controller.animation.shape

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import com.agilie.controller.animation.Painter
import com.agilie.controller.calculateAngleWithTwoVectors
import com.agilie.controller.getPointOnBorderLineOfCircle

/**
 * Action Flow
 * 1) Draw shape on a borderline of inner circle
 * 2) Move shape around a circle
 *   2.1) Calculate the angle between the point of contact and the circle of the inner circle
 *   2.2) Move shape to new coord
 * */


class MovableCircle : Painter() {

    init {
        paint = getMovableCirclePaint()
    }

    /** Set coordinates of default center */
    fun setCoordinatesOfCenter(point: PointF, innerRadius: Float) {
        startPoint.apply {
            x = point.x
            y = point.y - innerRadius
        }
    }

    fun onActionMove(touchX: Float, touchY: Float, innerX: Float, innerY: Float, innerRadius: Float) {
        val alfa = calculateAngleWithTwoVectors(touchX, touchY, innerX, innerY)
        val point = getPointOnBorderLineOfCircle(innerX, innerY, innerRadius, alfa)
        startPoint.apply {
            x = point.x
            y = point.y
        }
    }

    private fun getMovableCirclePaint(): Paint {
        val paint = Paint().apply {
            color = Color.rgb(239, 77, 30)
            isAntiAlias = true
            style = Paint.Style.FILL
            maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.OUTER)
        }
        return paint
    }
}