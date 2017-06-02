package com.agilie.controller.animation.painter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.Log
import com.agilie.controller.getPointOnBorderLineOfCircle

class SpiralPath(val spiralPath: Path, val pathPaint: Paint) : Painter {

    var spiralStartPoint: PointF? = null
    var center: PointF? = null
    var innerCircleCenter: PointF? = null
    var radius = 0f
    var innerCircleRadius = 0f
    var distance = 0f


    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(spiralPath, pathPaint)
    }

    override fun onSizeChanged(w: Int, h: Int) {
    }

    fun onReset() {
        spiralPath.reset()
    }

    fun onCreateSpiralPath(angle: Int, startAngle: Int) {
        drawBigSpline(angle, startAngle)
    }

    fun onDrawBigSpline(angle: Int, startAngle: Int) {
        drawBigSpline(angle, startAngle)
    }

    private fun drawBigSpline(angle: Int, startAngle: Int) {

        Log.d("SpiralPath360", angle.toString())

        val startPoint = getPointOnBorderLineOfCircle(innerCircleCenter, innerCircleRadius , angle)
        val controlPoint2 = getPointOnBorderLineOfCircle(center, radius, angle)

        val radius = innerCircleRadius + distance * angle
        val controlPoint3 = getPointOnBorderLineOfCircle(center, radius, angle)

        spiralPath.moveTo(startPoint.x, startPoint.y)
        spiralPath.lineTo(controlPoint2.x, controlPoint2.y)

        (angle..360 + angle step 6)
                .map { getPointOnBorderLineOfCircle(center, this.radius + 5, it) }
                .forEach { spiralPath.lineTo(it.x, it.y) }
        //Move to control point 3
        spiralPath.lineTo(controlPoint3.x, controlPoint3.y)

        angle.downTo(0).forEach {
            val radius = innerCircleRadius + distance * it
            val point = getPointOnBorderLineOfCircle(center, radius, it)
            spiralPath.lineTo(point.x, point.y)
        }

        for (i in 0..360 step 6) {
            val point = getPointOnBorderLineOfCircle(innerCircleCenter, innerCircleRadius , i)
            if (i == startAngle) {
                spiralPath.lineTo(point.x, point.y)
            }
            spiralPath.lineTo(point.x, point.y)
        }

        spiralPath.close()
    }
}