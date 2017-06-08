package com.agilie.splinecontroller.animation.painter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import com.agilie.splinecontroller.getPointOnBorderLineOfCircle

class SplinePath(val splinePath: Path, val splinePaint: Paint) : Painter {

    var spiralStartPoint: PointF? = null
    var center: PointF? = null
    var innerCircleCenter: PointF? = null
    var radius = 0f
    var innerCircleRadius = 0f
    var distance = 0f

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(splinePath, splinePaint)
    }

    override fun onSizeChanged(w: Int, h: Int) {
    }

    fun onReset() {
        splinePath.reset()
    }

    fun onCreateSpiralPath(drawToAngle: Int, startAngle: Int) {
        drawBigSpline(drawToAngle, startAngle)
    }

    fun onDrawBigSpline(angle: Int, startAngle: Int) {
        drawBigSpline(angle, startAngle)
    }

    /** Our custom spline consists of line path.
     *In order to correctly draw a spline it is necessary to pass four control points.
    From the starting point, which is a point on the inner circle lying at an angle of
    zero degrees relative to the circle circle.
    Second point it is on the outer circle lies at the same angle as the first point.
    The third point determines which area is to be shown
    The fourth point closes our circle     * */
    private fun drawBigSpline(angle: Int, startAngle: Int) {

        val startPoint = getPointOnBorderLineOfCircle(innerCircleCenter, innerCircleRadius, angle)
        val controlPoint2 = getPointOnBorderLineOfCircle(center, radius, angle)

        val radius = innerCircleRadius + distance * angle
        val controlPoint3 = getPointOnBorderLineOfCircle(center, radius, angle)
        //Move to point 1,2
        splinePath.moveTo(startPoint.x, startPoint.y)
        splinePath.lineTo(controlPoint2.x, controlPoint2.y)
        //Describe the outer circle to point 2
        (angle..360 + angle step 6)
                .map { getPointOnBorderLineOfCircle(center, this.radius + 5, it) }
                .forEach { splinePath.lineTo(it.x, it.y) }
        //Move to control point 3
        splinePath.lineTo(controlPoint3.x, controlPoint3.y)
        angle.downTo(0).forEach {
            val radius = innerCircleRadius + distance * it
            val point = getPointOnBorderLineOfCircle(center, radius, it)
            splinePath.lineTo(point.x, point.y)
        }
        //Move to point 4
        for (i in 0..360 step 20) {
            val point = getPointOnBorderLineOfCircle(innerCircleCenter, innerCircleRadius, i)
            if (i == startAngle) {
                splinePath.lineTo(point.x, point.y)
            }
            splinePath.lineTo(point.x, point.y)
        }

        splinePath.close()
    }
}