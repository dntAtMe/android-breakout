package com.dntatme.arkanoid.drawable

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Region
import android.util.Log
import com.dntatme.arkanoid.helpers.Point

class CircleShape(var point: Point, var radius: Int, paint: Paint = Paint(), val colorId: Int): Shape(paint) {

    init {
        paint.color = colorId
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle((point.x.toFloat()), (point.y.toFloat()), radius.toFloat(), paint)

    }

    override fun changeColor(color: Int) {
        paint.color = color
    }
}