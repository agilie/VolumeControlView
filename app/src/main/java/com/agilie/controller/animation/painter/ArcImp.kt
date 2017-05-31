package com.agilie.controller.animation.painter

import android.graphics.*
import com.agilie.controller.getPointOnBorderLineOfCircle
import java.util.*
import kotlin.collections.HashMap

class ArcImp(val paint: Paint) : Arc {

    class Line {
        var end: PointF? = null
    }


    private var startPoint = PointF()

    var endPoint = PointF()
    var mainCircleRadius: Float? = null

    private var pathMap = HashMap<Path, Paint>()
    private var linesMap = HashMap<Line, Paint>()

    fun setStartPoint(pointF: PointF) {
        startPoint = pointF
        //initRectPath()
        initLine()
    }

    /*private fun initRectPath() {
        for (i in 0..360 step 10) {
            var path = Path()
            var point1 = getPointOnCircle(i + 0.0, 10f)
            var point2 = getPointOnCircle(i + 0.0, mainCircleRadius!!)
            var point3 = getPointOnCircle(i + 8.5, mainCircleRadius!!)
            var point4 = getPointOnCircle(i + 8.5, 10f)
            path.moveTo(point1.x, point1.y)
            path.lineTo(point2.x, point2.y)
            path.lineTo(point3.x, point3.y)
            path.lineTo(point4.x, point4.y)
            Log.d("ArcImp", "point1 " + point1.toString() + " point2 " + point2.toString() + " point3 " + point3.toString() + " point4 " + point4.toString())
            pathMap.put(path, setPaint())
        }
    }*/

    private fun initLine() {
        for (i in 0..360 step 6) {
            var line = Line()
            var point = getPointOnCircle(i + 0.0, mainCircleRadius!!)
            line.end = point
            linesMap.put(line, setPaint())
        }
    }

    private fun getPointOnCircle(angle: Double, radius: Float) =
            getPointOnBorderLineOfCircle(startPoint.x,
                    startPoint.y, radius, angle)

    override fun onDraw(canvas: Canvas) {
        /*for ((key, value) in pathMap) {
            canvas.drawPath(key, value)
        }*/

        for ((key, value) in linesMap) {
            canvas.drawLine(startPoint.x, startPoint.y, key.end!!.x, key.end!!.y, value)
        }
    }

    override fun onSizeChanged(w: Int, h: Int) {
    }

    fun setPaint() = Paint().apply {
        color = Color.rgb(Random().nextInt(255), Random().nextInt(255), Random().nextInt(255))
        isAntiAlias = true
    }

}

