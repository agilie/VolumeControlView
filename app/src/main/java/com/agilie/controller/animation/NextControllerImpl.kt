package com.agilie.controller.animation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import android.view.MotionEvent
import com.agilie.controller.animation.painter.*
import com.agilie.controller.animation.painter.Painter
import com.agilie.controller.getPointOnBorderLineOfCircle
import com.agilie.controller.view.NextControllerView
import com.agilie.controller.view.NextControllerView.Companion.INNER_CIRCLE_STROKE_WIDTH

class NextControllerImpl(val innerCircleImp: InnerCircleImp,
                         val mainCircleImp: MainCircleImp,
                         val movableCircleImp: MovableCircleImp,
                         val arcImp: ArcImp,
                         val spiralPath: SpiralPath,
                         val bitmap: Bitmap) : Painter {


    private var width = 0
    private var height = 0
    private var eventRadius: Float = 0f
    private var distance: Float = 0f


    override fun onDraw(canvas: Canvas) {

        mainCircleImp.onDraw(canvas)

        canvas.drawBitmap(bitmap, null, Rect((mainCircleImp.center.x - mainCircleImp.radius).toInt(),
                ((mainCircleImp.center.y - mainCircleImp.radius).toInt()),
                (mainCircleImp.center.x + mainCircleImp.radius).toInt(),
                ((mainCircleImp.center.y + mainCircleImp.radius).toInt())), null)
        //arcImp.onDraw(canvas)

        spiralPath.onDraw(canvas)
        //canvas.drawPath(spiralPath, pathPaint)
        innerCircleImp.onDraw(canvas)
        movableCircleImp.onDraw(canvas)


    }

    override fun onSizeChanged(w: Int, h: Int) {
        this.width = w
        this.height = h

        setCircleRadius(w, h)
        setCenterCoordinates(w, h)
        createSpiralPath()
    }

    private fun createSpiralPath() {

    }

    private fun setCenterCoordinates(w: Int, h: Int) {
        val mainCircleCenter = PointF().apply {
            x = w / 2f
            y = h / 2f
        }
        mainCircleImp.center = mainCircleCenter

        innerCircleImp.center = mainCircleCenter
        movableCircleImp.center.apply {
            x = mainCircleCenter.x
            y = mainCircleCenter.y - eventRadius
        }
        arcImp.setStartPoint(mainCircleCenter)

        spiralPath.spiralStartPoint = getPointOnBorderLineOfCircle(mainCircleCenter, innerCircleImp.radius + INNER_CIRCLE_STROKE_WIDTH, 0.0)
        spiralPath.innerCircleCenter = innerCircleImp.center
        spiralPath.mainCircleCenter = mainCircleCenter

    }


    private fun setCircleRadius(w: Int, h: Int) {
        val mainCircleRadius = if (w > h) h / 3f else w / 3f

        mainCircleImp.radius = mainCircleRadius
        innerCircleImp.radius = mainCircleRadius / 2
        movableCircleImp.radius = NextControllerView.MOVABLE_CIRCLE_RADIUS
        arcImp.mainCircleRadius = mainCircleRadius

        spiralPath.innerCircleRadius = innerCircleImp.radius
        spiralPath.mainCircleRadius = mainCircleRadius

        eventRadius = innerCircleImp.radius - movableCircleImp.radius * 2
        distance = (mainCircleImp.radius - innerCircleImp.radius) / 360

        spiralPath.distance = distance
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
                //spiralPath.reset()
            }
        }
    }


    private fun onAction(touchPointF: PointF) {
        movableCircleImp.onActionMove(touchPointF, mainCircleImp.center, eventRadius)
        spiralPath.onReset()
        spiralPath.drawBigSpline(touchPointF)

    }

    private fun onActionUp(event: MotionEvent) {

    }


}

