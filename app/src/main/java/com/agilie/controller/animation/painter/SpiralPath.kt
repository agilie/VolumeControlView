package com.agilie.controller.animation.painter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import com.agilie.controller.calculateAngleWithTwoVectors
import com.agilie.controller.getPointOnBorderLineOfCircle

class SpiralPath(val spiralPath: Path, val pathPaint: Paint) : Painter {

    var spiralStartPoint: PointF? = null
    var mainCircleCenter: PointF? = null
    var innerCircleCenter: PointF? = null
    var mainCircleRadius = 0f
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

    fun drawBigSpline(touchPointF: PointF) {

        val alfa = Math.round(calculateAngleWithTwoVectors(touchPointF, mainCircleCenter))
        val startPoint = getPointOnBorderLineOfCircle(innerCircleCenter, innerCircleRadius + 2, alfa.toDouble())
        val controlPoint2 = getPointOnBorderLineOfCircle(mainCircleCenter, mainCircleRadius, alfa.toDouble())

        val radius = innerCircleRadius + distance * alfa
        val controlPoint3 = getPointOnBorderLineOfCircle(mainCircleCenter, radius, alfa.toDouble())

        spiralPath.moveTo(startPoint.x, startPoint.y)
        spiralPath.lineTo(controlPoint2.x, controlPoint2.y)

        // Движение по внешней окружности до точки касания на внешней окружности
        for (i in alfa..360 + alfa step 2) {
            val point = getPointOnBorderLineOfCircle(mainCircleCenter, mainCircleRadius, i.toDouble())
            spiralPath.lineTo(point.x, point.y)
        }
        //Move to control point 3
        spiralPath.lineTo(controlPoint3.x, controlPoint3.y)


        alfa.downTo(0).forEach {
            val radius = innerCircleRadius + distance * it
            val point = getPointOnBorderLineOfCircle(mainCircleCenter, radius, it.toDouble())
            //Log.d("NextControllerImpl", alfa.toString())
            spiralPath.lineTo(point.x, point.y)
        }

        //Движение по внутренней окружности , предусмотреть возможность стыковке с точкой №1
        var startAlfa = Math.round(calculateAngleWithTwoVectors(touchPointF, innerCircleCenter))
        for (i in 0..360 step 2) {
            val point = getPointOnBorderLineOfCircle(innerCircleCenter, innerCircleRadius + 2, i.toDouble())
            if (i.toLong() == startAlfa) {
                spiralPath.lineTo(point.x, point.y)
            }
            spiralPath.lineTo(point.x, point.y)
        }

        spiralPath.close()
    }
}