package com.dntatme.arkanoid.drawable

import android.graphics.Canvas
import android.graphics.Paint
import com.dntatme.arkanoid.helpers.Point

class TextShape(val point: Point, paint: Paint = Paint(), val colorId: Int): Shape(paint) {

    init {
        paint.color = colorId
    }

    var text: String = ""
    override fun draw(canvas: Canvas?) {
        canvas?.drawText(text, point.x.toFloat(), point.y.toFloat(), paint)
    }

    fun changeText(string: String) {
        text = string
    }

    override fun changeColor(color: Int) {
        paint.color = color
    }

}