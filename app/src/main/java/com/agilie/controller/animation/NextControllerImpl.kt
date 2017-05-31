package com.agilie.controller.animation

import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import com.agilie.controller.animation.painter.*
import com.agilie.controller.animation.painter.Painter
import com.agilie.controller.calculateAngleWithTwoVectors
import com.agilie.controller.getPointOnBorderLineOfCircle
import com.agilie.controller.view.NextControllerView

class NextControllerImpl(val innerCircleImp: InnerCircleImp,
                         val mainCircleImp: MainCircleImp,
                         val movableCircleImp: MovableCircleImp,
                         val arcImp: ArcImp) : Painter {


    private var width = 0
    private var height = 0
    private var eventRadius: Float = 0f
    private var distance: Float = 0f

    private var spiralPath = Path()
    private var mainCirclePath = Path()
    private var innerCirclePath = Path()


    private var spiralStartPoint: PointF? = null
    private var pathPaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
        style = Paint.Style.FILL

    }

    override fun onDraw(canvas: Canvas) {

        //mainCircleImp.onDraw(canvas)

        //arcImp.onDraw(canvas)
        //innerCircleImp.onDraw(canvas)

        canvas.drawPath(mainCirclePath, mainCircleImp.paint)
        canvas.drawPath(innerCirclePath, innerCircleImp.paint)
        canvas.drawPath(spiralPath, pathPaint)

        movableCircleImp.onDraw(canvas)

    }

    override fun onSizeChanged(w: Int, h: Int) {
        this.width = w
        this.height = h

        setCircleRadius(w, h)
        setCenterCoordinates(w, h)

        mainCirclePath.addCircle(mainCircleImp.center.x, mainCircleImp.center.y, mainCircleImp.radius, Path.Direction.CW)
        innerCirclePath.addCircle(innerCircleImp.center.x, innerCircleImp.center.y, innerCircleImp.radius, Path.Direction.CW)

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
        spiralStartPoint = getPointOnBorderLineOfCircle(mainCircleCenter, innerCircleImp.radius, 0.0)

    }


    private fun setCircleRadius(w: Int, h: Int) {
        val mainCircleRadius = if (w > h) h / 3f else w / 3f
        mainCircleImp.radius = mainCircleRadius
        innerCircleImp.radius = mainCircleRadius / 2
        movableCircleImp.radius = NextControllerView.MOVABLE_CIRCLE_RADIUS
        arcImp.mainCircleRadius = mainCircleRadius
        eventRadius = innerCircleImp.radius - movableCircleImp.radius * 2
        distance = (mainCircleImp.radius - innerCircleImp.radius) / 360
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
                spiralPath.reset()
            }
        }
    }


    private fun onAction(touchPointF: PointF) {
        movableCircleImp.onActionMove(touchPointF, mainCircleImp.center, eventRadius)
        spiralPath.reset()
        spiralPath.fillType = Path.FillType.EVEN_ODD
        spiralPath.moveTo(spiralStartPoint!!.x, spiralStartPoint!!.y)
        getPointLineTo(touchPointF)
    }

    private fun onActionUp(event: MotionEvent) {

    }

    private fun getPointLineTo(touchPointF: PointF) {
        val alfa = Math.round(calculateAngleWithTwoVectors(touchPointF, mainCircleImp.center))
        for (i in 0..alfa) {
            val radius = innerCircleImp.radius+2 + distance * i
            val point = getPointOnBorderLineOfCircle(mainCircleImp.center, radius, i.toDouble())
            spiralPath.lineTo(point.x, point.y)
            Log.d("NextControllerImpl", radius.toString())
        }
        for (i in alfa..360 step 3) {
            val nextPoint = getPointOnBorderLineOfCircle(mainCircleImp.center, innerCircleImp.radius+2, i.toDouble())
            spiralPath.lineTo(nextPoint.x, nextPoint.y)
        }
        spiralPath.moveTo(spiralStartPoint!!.x, spiralStartPoint!!.y)

    }
}

