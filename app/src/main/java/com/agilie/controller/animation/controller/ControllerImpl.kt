package com.agilie.controller.animation.controller

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import android.view.MotionEvent
import com.agilie.controller.animation.painter.InnerCircleImpl
import com.agilie.controller.animation.painter.MovableCircleImpl
import com.agilie.controller.animation.painter.SimpleLineImpl
import com.agilie.controller.animation.painter.SpiralPath
import com.agilie.controller.getPointOnBorderLineOfCircle
import com.agilie.controller.view.ControllerView
import com.agilie.controller.view.ControllerView.Companion.INNER_CIRCLE_STROKE_WIDTH
import java.util.*

class ControllerImpl(val innerCircleImpl: InnerCircleImpl,
                     val movableCircleImpl: MovableCircleImpl,
                     val spiralPath: SpiralPath,
                     val bitmap: Bitmap) : Controller {

    private var width = 0
    private var height = 0
    private var eventRadius: Float = 0f
    private var distance: Float = 0f
    private var mainCenter: PointF = PointF()
    private var mainRadius = 0f
    private var linesList = ArrayList<SimpleLineImpl>()


    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, null, getRect(), null)
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
                onAction(PointF(event.x, event.y))
            }
            MotionEvent.ACTION_MOVE -> {
                //if (event.eventTime - event.downTime > 200) {
                onAction(PointF(event.x, event.y))
                // }
            }
            MotionEvent.ACTION_UP -> {
                onActionUp(event)
            }
        }
    }

    private fun onAction(touchPointF: PointF) {
        movableCircleImpl.onActionMove(touchPointF, mainCenter, eventRadius)
        spiralPath.onReset()
        spiralPath.onDrawBigSpline(touchPointF)
    }

    private fun onActionUp(event: MotionEvent) {

    }

    private fun createSpiralPath() {
        spiralPath.onCreateSpiralPath()
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

        spiralPath.spiralStartPoint = getPointOnBorderLineOfCircle(mainCenter,
                innerCircleImpl.radius + INNER_CIRCLE_STROKE_WIDTH, 0)

        spiralPath.innerCircleCenter = innerCircleImpl.center
        spiralPath.center = mainCenter
    }

    private fun setCircleRadius(w: Int, h: Int) {
        mainRadius = if (w > h) h / 3f else w / 3f

        innerCircleImpl.radius = mainRadius / 2
        movableCircleImpl.radius = ControllerView.MOVABLE_CIRCLE_RADIUS

        spiralPath.innerCircleRadius = innerCircleImpl.radius
        spiralPath.radius = mainRadius

        eventRadius = innerCircleImpl.radius - movableCircleImpl.radius * 2
        distance = (mainRadius - innerCircleImpl.radius) / 360

        spiralPath.distance = distance
    }

    private fun getRect() =
            Rect((mainCenter.x - mainRadius).toInt(),
                    ((mainCenter.y - mainRadius).toInt()),
                    (mainCenter.x + mainRadius).toInt(),
                    ((mainCenter.y + mainRadius).toInt()))


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

}

