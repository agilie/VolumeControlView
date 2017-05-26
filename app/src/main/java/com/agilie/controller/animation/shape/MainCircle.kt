package com.agilie.controller.animation.shape

import android.graphics.*
import com.agilie.controller.animation.Painter
import com.agilie.controller.calculateAngleWithTwoVectors
import com.agilie.controller.getPointOnBorderLineOfCircle
import com.agilie.controller.pointInCircle


/** Action flow
 * Draw circle
 * Draw movable circle
 * Draw line on a border of inner circle
 * On touch event determine the angle is oblique and take five lines that are drawn at a similar angle
 * Define a touch point inside the movable circle
 * Move movable circle only if touch point inside
 * Repaint simple line when circle move
 * */

class MainCircle : Painter() {

    private var movableCircle = MovableCircle()
    private var linesList = ArrayList<SimpleLine>()

    init {
        paint = getMainCirclePaint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawAllShapes(canvas)
    }

    override fun onSizeChanged(width: Int, height: Int) {
        startPoint.apply {
            x = width / 2f
            y = height / 2f
        }
        setRadius(width, height)

        updateMovableShape()
        drawSimpleLineOnCircleBorderLine()
    }

    override fun setRadius(width: Int, height: Int) {
        if (width > height) {
            radius = height / 4f
        } else {
            radius = width / 4f
        }
    }

    fun onActionMove(touchX: Float, touchY: Float) {
        if (!pointInCircle(PointF(touchX, touchY), movableCircle.startPoint, movableCircle.radius * 3))
            return
        linesList.forEach { it.updateLinePaint(false) }

        getLinesArea(touchX, touchY).forEach { it.updateLinePaint(true) }

        movableCircle.onActionMove(touchX, touchY, startPoint.x, startPoint.y, radius)
    }

    fun onActionDown(touchX: Float, touchY: Float) {
        if (!pointInCircle(PointF(touchX, touchY), movableCircle.startPoint, movableCircle.radius))
            return

        getLinesArea(touchX, touchY).forEach { it.updateLinePaint(true) }
    }

    fun onActionUp(touchX: Float, touchY: Float) {
        linesList.forEach { it.updateLinePaint(false) }
    }

    private fun getLinesArea(touchX: Float, touchY: Float): List<SimpleLine> {
        val alfa = Math.round(calculateAngleWithTwoVectors(touchX, touchY, startPoint.x, startPoint.y))

        return linesList.filter { filterLinesList(it, alfa) }
    }

    private fun drawAllShapes(canvas: Canvas) {
        movableCircle.onDraw(canvas)
        linesList.forEach {
            it.onDraw(canvas)
        }
    }

    private fun getMainCirclePaint() = Paint().apply {
        color = Color.rgb(17, 10, 35)
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = INNER_CIRCLE_STROKE_WIDTH
    }

    private fun updateMovableShape() {
        movableCircle.setCoordinatesOfCenter(startPoint, radius)
        movableCircle.radius = INNER_CIRCLE_STROKE_WIDTH / 2
    }


    /**    The starting point is the point lying on the circle with the radius of the original circle plus his stroke width.
     *     The end point is the point lying on the circle with the radius of the original circle, the length of the line */
    private fun drawSimpleLineOnCircleBorderLine() {

        for (i in 2..360 step 2) {
            val line = SimpleLine(i.toDouble())
            val point1 = getPointOnBorderLineOfCircle(startPoint.x,
                    startPoint.y, radius + INNER_CIRCLE_STROKE_WIDTH / 2 + 5, i.toDouble())
            line.startPoint = point1

            val point2 = getPointOnBorderLineOfCircle(startPoint.x,
                    startPoint.y, radius + LINE_LENGTH, i.toDouble())
            line.endPoint = point2
            linesList.add(line)
        }
    }

    private fun filterLinesList(it: SimpleLine, alfa: Long): Boolean {
        if (alfa - CAPTURE_ANGLE < 0) {
            val alfaX = FULL_CIRCLE + alfa - CAPTURE_ANGLE
            if (it.angle >= alfaX)
                return true
        }

        if (alfa + CAPTURE_ANGLE > FULL_CIRCLE) {
            val alfaX = alfa + CAPTURE_ANGLE - FULL_CIRCLE
            if (it.angle <= alfaX)
                return true
        }

        if (it.angle >= alfa - CAPTURE_ANGLE && it.angle <= alfa + CAPTURE_ANGLE)
            return true

        return false
    }
}