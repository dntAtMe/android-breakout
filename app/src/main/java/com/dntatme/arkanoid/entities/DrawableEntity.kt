package com.dntatme.arkanoid.entities

import android.graphics.Canvas
import com.dntatme.arkanoid.GameView
import com.dntatme.arkanoid.helpers.Point
import com.dntatme.arkanoid.drawable.Shape

abstract class DrawableEntity(view: GameView, var color: Int, soundId: Int, point: Point, var shape: Shape): Entity(view, soundId, point) {

    override fun update(interval: Double) {

    }

    abstract fun draw(canvas: Canvas?, view: GameView)

}