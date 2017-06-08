package com.agilie.splinecontroller.animation.painter

import android.graphics.PointF

interface MovableCircle : Painter {

    fun onActionMove(mainCenter: PointF)
    fun onActionDown(pointF: PointF)
    fun onActionUp(pointF: PointF)
}