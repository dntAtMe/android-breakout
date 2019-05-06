package com.dntatme.arkanoid.drawable;

import android.graphics.Canvas;
import android.graphics.Paint

abstract class Shape(val paint: Paint) {

    abstract fun draw(canvas: Canvas?)
    abstract fun changeColor(color: Int)
}
