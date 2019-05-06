package com.dntatme.arkanoid.drawable

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Region
import android.view.WindowManager
import com.dntatme.arkanoid.helpers.Point

class RectShape(var point: Point, var width: Int, var height: Int, paint: Paint = Paint(), val colorId: Int): Shape(paint) {

    init {
        paint.color = colorId
    }

    override fun changeColor(color: Int) {
        paint.color = color
    }

    override fun draw(canvas: Canvas?) {
        val rect = Rect((point.x - width / 2 ), (point.y - height / 2 ), (point.x + width / 2), (point.y + height / 2) )

        canvas?.drawRect(rect, paint)
    }
}