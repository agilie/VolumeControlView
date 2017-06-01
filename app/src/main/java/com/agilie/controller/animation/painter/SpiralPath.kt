package com.agilie.controller.animation.painter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import com.agilie.controller.calculateAngleWithTwoVectors
import com.agilie.controller.closestValue
import com.agilie.controller.getPointOnBorderLineOfCircle
import com.agilie.controller.view.ControllerView

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

    fun onCreateSpiralPath() {
        val point = getPointOnBorderLineOfCircle(center, radius, 0)
        drawBigSpline(point)
    }

    fun onDrawBigSpline(touchPointF: PointF) {
        drawBigSpline(touchPointF)
    }

    private fun drawBigSpline(touchPointF: PointF) {

        val alfa = closestValue(calculateAngleWithTwoVectors(touchPointF, center), ControllerView.SECTOR_STEP)
        val startPoint = getPointOnBorderLineOfCircle(innerCircleCenter, innerCircleRadius + 2, alfa)
        val controlPoint2 = getPointOnBorderLineOfCircle(center, radius, alfa)

        val radius = innerCircleRadius + distance * alfa
        val controlPoint3 = getPointOnBorderLineOfCircle(center, radius, alfa)

        spiralPath.moveTo(startPoint.x, startPoint.y)
        spiralPath.lineTo(controlPoint2.x, controlPoint2.y)

        // Движение по внешней окружности до точки касания на внешней окружности
        (alfa..360 + alfa step 6)
                .map { getPointOnBorderLineOfCircle(center, this.radius + 5, it) }
                .forEach { spiralPath.lineTo(it.x, it.y) }
        //Move to control point 3
        spiralPath.lineTo(controlPoint3.x, controlPoint3.y)


        alfa.downTo(0).forEach {
            val radius = innerCircleRadius + distance * it
            val point = getPointOnBorderLineOfCircle(center, radius, it)
            //Log.d("ControllerImpl", alfa.toString())
            spiralPath.lineTo(point.x, point.y)
        }

        //Движение по внутренней окружности , предусмотреть возможность стыковке с точкой №1
        var startAlfa = Math.round(calculateAngleWithTwoVectors(touchPointF, innerCircleCenter))
        for (i in 0..360 step 6) {
            val point = getPointOnBorderLineOfCircle(innerCircleCenter, innerCircleRadius + 2, i)
            if (i.toLong() == startAlfa) {
                spiralPath.lineTo(point.x, point.y)
            }
            spiralPath.lineTo(point.x, point.y)
        }

        spiralPath.close()
    }
}