package com.dntatme.arkanoid.entities

import android.graphics.Canvas
import android.util.Log
import com.dntatme.arkanoid.GameView
import com.dntatme.arkanoid.drawable.CircleShape
import com.dntatme.arkanoid.drawable.RectShape
import com.dntatme.arkanoid.helpers.Point
import com.dntatme.arkanoid.helpers.UpdateEventListener
import com.dntatme.arkanoid.helpers.Velocity

class Ball(view: GameView,
           color: Int,
           soundId: Int,
           point: Point,
           var radius: Int
): DrawableEntity(view, color, soundId, point, CircleShape(point, radius, colorId = color)), UpdateEventListener  {

    internal var velocity = Velocity(view)

    private var moveX: Float = point.x.toFloat()
    private var moveY: Float = point.y.toFloat()

    fun move() {
        velocity.limit()

        moveX += velocity.xv * velocity.speedMultiplicator * velocity.xDirection
        moveY += velocity.yv * velocity.speedMultiplicator * velocity.yDirection

        point.x = moveX.toInt()
        point.y = moveY.toInt()
    }

    override fun draw(canvas: Canvas?, view: GameView) {
        shape.draw(canvas)
    }

    override fun handleUpdateEvent(view: GameView) {
        move()
    }

}