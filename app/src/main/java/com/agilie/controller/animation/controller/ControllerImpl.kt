package com.agilie.controller.animation.controller

import android.graphics.Canvas
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import com.agilie.controller.animation.painter.*
import com.agilie.controller.calculateAngleWithTwoVectors
import com.agilie.controller.closestValue
import com.agilie.controller.getPointOnBorderLineOfCircle
import com.agilie.controller.view.ControllerView
import com.agilie.controller.view.ControllerView.Companion.INNER_CIRCLE_STROKE_WIDTH
import java.util.*

class ControllerImpl(val innerCircleImpl: InnerCircleImpl,
                     val movableCircleImpl: MovableCircleImpl,
                     val spiralPath: SpiralPath,
                     val mainCircleImpl: MainCircleImpl) : Controller {

    private var width = 0
    private var height = 0
    private var eventRadius: Float = 0f
    private var distance: Float = 0f
    private var mainCenter: PointF = PointF()
    private var mainRadius = 0f
    private var linesList = ArrayList<SimpleLineImpl>()


    override fun onDraw(canvas: Canvas) {
        mainCircleImpl.onDraw(canvas)
        linesList.forEach { it.onDraw(canvas) }
        spiralPath.onDraw(canvas)
        innerCircleImpl.onDraw(canvas)
        movableCircleImpl.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int) {
        this.width = w
        this.height = h
        setCircleRadius(w, h)
        setCenterCoordinates(w, h)
        createSpiralPath()
        initLines()
    }

    fun onTouchEvent(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                onActionDown(PointF(event.x, event.y))
            }
            MotionEvent.ACTION_MOVE -> {
                onActionMove(PointF(event.x, event.y))
            }
            MotionEvent.ACTION_UP -> {
                onActionUp(event)
            }
        }
    }

    private fun onActionDown(touchPointF: PointF) {
        val currentAngle = getClosestAngle(touchPointF)
        val startAngle = getStartAngle(touchPointF)
        val point = getPointOnBorderLineOfCircle(mainCenter, eventRadius, startAngle)

        movableCircleImpl.onActionMove(point)
        spiralPath.onReset()
        spiralPath.onDrawBigSpline(currentAngle, startAngle)
    }


    private var previousAngle = 0
    private var clockwise = true

    private fun onActionMove(touchPointF: PointF) {
        var currentAngle = getClosestAngle(touchPointF)
        val startAngle = getStartAngle(touchPointF)
        val point = getPointOnBorderLineOfCircle(mainCenter, eventRadius, startAngle)

        Log.d("ControllerImpl", "previousAngle = " + previousAngle + " currentAngle = " + currentAngle + " clockwise = " + clockwise)

        if (previousAngle != currentAngle) {
            clockwise = previousAngle < currentAngle
        }

        currentAngle = updateCurrentAngle(previousAngle, currentAngle, clockwise)

        Log.d("ControllerImpl", "previousAngle = " + previousAngle + " currentAngle = " + currentAngle + " clockwise = " + clockwise)
        Log.d("ControllerImpl", "-------------------")

        movableCircleImpl.onActionMove(point)
        spiralPath.onReset()
        spiralPath.onDrawBigSpline(currentAngle, startAngle)

        previousAngle = currentAngle
    }


    private fun onActionUp(event: MotionEvent) {

    }

    private fun createSpiralPath() {
        val startAngle = getStartAngle(mainCenter)
        spiralPath.onCreateSpiralPath(0, startAngle)
    }

    private fun setCenterCoordinates(w: Int, h: Int) {
        mainCenter.apply {
            x = w / 2f
            y = h / 2f
        }

        innerCircleImpl.center = mainCenter
        movableCircleImpl.center.apply {
            x = mainCenter.x
            y = mainCenter.y - eventRadius
        }

        mainCircleImpl.center = mainCenter

        spiralPath.spiralStartPoint = getPointOnBorderLineOfCircle(mainCenter,
                innerCircleImpl.radius + INNER_CIRCLE_STROKE_WIDTH, 0)

        spiralPath.innerCircleCenter = innerCircleImpl.center
        spiralPath.center = mainCenter
    }

    private fun setCircleRadius(w: Int, h: Int) {
        mainRadius = if (w > h) h / 3f else w / 3f
        mainCircleImpl.radius = mainRadius

        innerCircleImpl.radius = mainRadius / 2
        movableCircleImpl.radius = ControllerView.MOVABLE_CIRCLE_RADIUS

        spiralPath.innerCircleRadius = innerCircleImpl.radius
        spiralPath.radius = mainRadius

        eventRadius = innerCircleImpl.radius - movableCircleImpl.radius * 2
        distance = (mainRadius - innerCircleImpl.radius) / 360

        spiralPath.distance = distance
    }

    private fun initLines() {
        for (i in 0..360 step ControllerView.SECTOR_STEP) {
            val line = SimpleLineImpl(spiralPath.pathPaint)
            line.startPoint = mainCenter
            val endPoint = getPointOnBorderLineOfCircle(mainCenter.x,
                    mainCenter.y, mainRadius, i.toDouble())
            line.endPoint = endPoint
            linesList.add(line)
        }
    }


    private fun updateCurrentAngle(previousAngle: Int, currentAngle: Int, clockwise: Boolean): Int {
        if (clockwise && previousAngle > currentAngle) {
            return 360
        }

        if (!clockwise && previousAngle < currentAngle)
            return 0

        return currentAngle
    }

    private fun getClosestAngle(touchPointF: PointF) =
            closestValue(calculateAngleWithTwoVectors(touchPointF, mainCenter), ControllerView.SECTOR_STEP)

    private fun getStartAngle(touchPointF: PointF) =
            (Math.round(calculateAngleWithTwoVectors(touchPointF, mainCenter))).toInt()
}

