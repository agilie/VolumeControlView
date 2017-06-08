package com.agilie.splinecontroller.animation.controller

import android.graphics.Canvas
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.agilie.splinecontroller.animation.painter.*
import com.agilie.splinecontroller.calculateAngleWithTwoVectors
import com.agilie.splinecontroller.closestValue
import com.agilie.splinecontroller.getPointOnBorderLineOfCircle
import com.agilie.splinecontroller.view.ControllerView
import com.agilie.splinecontroller.view.ControllerView.Companion.CONTROLLER_SPACE
import com.agilie.splinecontroller.view.ControllerView.Companion.INNER_CIRCLE_STROKE_WIDTH
import java.util.*


class ControllerImpl(val innerCircleImpl: InnerCircleImpl,
                     val movableCircleImpl: MovableCircleImpl,
                     val splinePath: SplinePath,
                     val mainCircleImpl: MainCircleImpl,
                     var backgroundShiningImpl: BackgroundShiningImpl) : Controller {

    interface OnTouchControllerListener {
        fun onControllerDown(angle: Int, percent: Int)
        fun onControllerMove(angle: Int, percent: Int)
        fun onAngleChange(angle: Int, percent: Int)
    }

    private var width = 0
    private var height = 0
    private var eventRadius: Float = 0f
    private var distance: Float = 0f
    private var controllerCenter: PointF = PointF()
    private var controllerRadius = 0f
    private var linesList = ArrayList<SimpleLineImpl>()

    var onTouchControllerListener: OnTouchControllerListener? = null

    /** Draw all object after void onSizeChange*/
    override fun onDraw(canvas: Canvas) {
        backgroundShiningImpl.onDraw(canvas)
        mainCircleImpl.onDraw(canvas)
        linesList.forEach { it.onDraw(canvas) }
        splinePath.onDraw(canvas)
        innerCircleImpl.onDraw(canvas)
        movableCircleImpl.onDraw(canvas)
    }

    /** When call onSizeChanged we set init all radius and coordinates of the centers*/
    override fun onSizeChanged(w: Int, h: Int) {
        this.width = w
        this.height = h
        setCircleRadius(w, h)
        setCenterCoordinates(w, h)
        createSplinePath()
        initLines()
    }

    fun onTouchEvent(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                onActionDownAndUp(PointF(event.x, event.y))
            }
            MotionEvent.ACTION_MOVE -> {
                onActionMove(PointF(event.x, event.y))
            }
            MotionEvent.ACTION_UP -> {
                onActionDownAndUp(PointF(event.x, event.y))
            }
        }
    }

    /**Save state of previousAngle*/
    fun onSaveInstanceState(bundle: Bundle) {
        bundle.putInt("previousAngle", previousAngle)
        bundle.putBoolean("onRestore", true)
        bundle.putBoolean("firstLaunch", firstLaunch)
    }

    /**Restore state*/
    fun onRestoreInstanceState(bundle: Bundle) {
        previousAngle = bundle.getInt("previousAngle")
        onRestore = bundle.getBoolean("onRestore")
        firstLaunch = bundle.getBoolean("firstLaunch")
    }

    /** Move shapes to new position*/
    private fun onActionDownAndUp(touchPointF: PointF) {
        //if (firstLaunch) actionDownAngle = startAngle else getClosestAngle(touchPointF)
        actionDownAngle = getClosestAngle(touchPointF)

        val startAngle = getStartAngle(touchPointF)
        val point = getPointOnBorderLineOfCircle(controllerCenter, eventRadius, startAngle)

        previousAngle = actionDownAngle
        direction = Direction.UNDEFINED
        angleDelta = 0

        onTouchControllerListener?.onControllerDown(actionDownAngle, calculatePercent(actionDownAngle))
        onTouchControllerListener?.onAngleChange(actionDownAngle, calculatePercent(actionDownAngle))

        movableCircleImpl.onActionMove(point)
        backgroundShiningImpl.gradientAngle = actionDownAngle
        splinePath.onReset()
        splinePath.onDrawBigSpline(actionDownAngle, startAngle)
    }

    private var actionDownAngle: Int = 0
    private var angleDelta = 0

    private var previousAngle = 0
    private var direction: Direction = Direction.UNDEFINED

    enum class Direction {
        UNDEFINED, CLOCKWISE, CCLOCKWISE
    }


    /**In order to correctly move all the figures relative to the touch point,
     * we need to calculate the nearest sector to the touch point.
     * After calculating the coordinates of the new points to which you must move all the shapes.
     * Also we need to detected full and empty circle*/
    private fun onActionMove(touchPointF: PointF) {
        val currentAngle = getClosestAngle(touchPointF)
        //val startAngle = getStartAngle(touchPointF)
        val moveToPoint = getPointOnBorderLineOfCircle(controllerCenter, eventRadius, currentAngle)
        val startPoint = getPointOnBorderLineOfCircle(controllerCenter, eventRadius, 0)

        if (previousAngle != currentAngle) {
            if (overlappedClockwise(direction, previousAngle, currentAngle)) {
                angleDelta += (360 - previousAngle + currentAngle)
            } else if (overlappedCclockwise(direction, previousAngle, currentAngle)) {
                angleDelta -= (360 - currentAngle + previousAngle)
            } else if (previousAngle < currentAngle) {
                direction = Direction.CLOCKWISE
                angleDelta += (currentAngle - previousAngle)
            } else {
                direction = Direction.CCLOCKWISE
                angleDelta -= (previousAngle - currentAngle)
            }
        }

        val angle = Math.max(Math.min(actionDownAngle + angleDelta, 360), 0)

        if (moveMovableCircle(angle)) {
            movableCircleImpl.onActionMove(moveToPoint)
            backgroundShiningImpl.gradientAngle = currentAngle
        } else {
            movableCircleImpl.onActionMove(startPoint)
        }

        onTouchControllerListener?.onControllerMove(angle, calculatePercent(angle))
        onTouchControllerListener?.onAngleChange(angle, calculatePercent(angle))

        splinePath.onReset()
        splinePath.onDrawBigSpline(angle, currentAngle)

        previousAngle = currentAngle
    }

    private fun moveMovableCircle(angle: Int): Boolean {
        if (angle == 360 || angle == 0) {
            return false
        }
        return true
    }

    private var onRestore = false

    /**Draw spline. If we draw this for the first time than call void onCreateSpiralPath */
    var startAngle = 0
    var firstLaunch = true
    private fun createSplinePath() {
        if (onRestore) {
            Log.d("Restore", "-----------------------------------------------------------")
            val restoreTouchPoint = getPointOnBorderLineOfCircle(controllerCenter, controllerRadius, previousAngle)
            onActionDownAndUp(restoreTouchPoint)
        } else {
            if (!firstLaunch) splinePath.onCreateSpiralPath(drawToAngle = 0, startAngle = 0)
            else {
                splinePath.onCreateSpiralPath(drawToAngle = 0, startAngle = startAngle)
                onActionDownAndUp(getPointOnBorderLineOfCircle(controllerCenter, controllerRadius, startAngle))
                firstLaunch = false
            }
        }
    }

    /**Set all centers coordinates */
    private fun setCenterCoordinates(w: Int, h: Int) {
        controllerCenter.apply {
            x = w / 2f
            y = h / 2f
        }

        innerCircleImpl.center = controllerCenter
        movableCircleImpl.center.apply {
            x = controllerCenter.x
            y = controllerCenter.y - eventRadius
        }

        mainCircleImpl.center = controllerCenter
        backgroundShiningImpl.center = controllerCenter

        splinePath.spiralStartPoint = getPointOnBorderLineOfCircle(controllerCenter,
                innerCircleImpl.radius + INNER_CIRCLE_STROKE_WIDTH, 0)

        splinePath.innerCircleCenter = innerCircleImpl.center
        splinePath.center = controllerCenter
    }

    /** Set all radius.   The area of ​​the controller depends on the variable
     * CONTROLLER_SPACE. The area of ​​the inner circle half of controller radius */
    private fun setCircleRadius(w: Int, h: Int) {
        controllerRadius = if (w > h) h / CONTROLLER_SPACE else w / CONTROLLER_SPACE
        mainCircleImpl.radius = controllerRadius

        backgroundShiningImpl.radius = controllerRadius

        innerCircleImpl.radius = controllerRadius / 2
        movableCircleImpl.radius = ControllerView.MOVABLE_CIRCLE_RADIUS

        splinePath.innerCircleRadius = innerCircleImpl.radius
        splinePath.radius = controllerRadius

        eventRadius = innerCircleImpl.radius - movableCircleImpl.radius * 2
        distance = (controllerRadius - innerCircleImpl.radius) / 360

        splinePath.distance = distance
    }

    /** Draw sector lines */
    private fun initLines() {
        for (i in 0..360 step ControllerView.SECTOR_STEP) {
            val line = SimpleLineImpl(splinePath.splinePaint)
            line.startPoint = controllerCenter
            val endPoint = getPointOnBorderLineOfCircle(controllerCenter.x,
                    controllerCenter.y, controllerRadius, i.toDouble())
            line.endPoint = endPoint
            linesList.add(line)
        }
    }

    private fun overlappedCclockwise(direction: Direction, previousAngle: Int, currentAngle: Int) = direction == Direction.CCLOCKWISE && (currentAngle - previousAngle) > 45

    private fun overlappedClockwise(direction: Direction, previousAngle: Int, currentAngle: Int) = direction == Direction.CLOCKWISE && (previousAngle - currentAngle) > 45

    private fun getClosestAngle(touchPointF: PointF) =
            closestValue(calculateAngleWithTwoVectors(touchPointF, controllerCenter), ControllerView.SECTOR_STEP)

    private fun getStartAngle(touchPointF: PointF) =
            (Math.round(calculateAngleWithTwoVectors(touchPointF, controllerCenter))).toInt()


    private fun calculatePercent(angle: Int) = Math.round(angle / 360f * 100)

}