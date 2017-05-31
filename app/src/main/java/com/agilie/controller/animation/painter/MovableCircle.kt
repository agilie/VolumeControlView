package com.agilie.controller.animation.painter

import android.graphics.PointF

interface MovableCircle : Painter, Circle {

    fun onActionMove(pointF: PointF, center: PointF, eventRadius: Float)
    fun onActionDown(pointF: PointF)
    fun onActionUp(pointF: PointF)
}