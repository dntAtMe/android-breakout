package com.dntatme.arkanoid.helpers

import android.content.Context
import android.view.View

class Velocity(val view: View): Component {

    val BASE_SPEED_MULTIPLICATOR = 4.0f


    val DIRECTION_RIGHT = 1
    val DIRECTION_LEFT = -1
    val DIRECTION_UP = -1
    val DIRECTION_DOWN = 1

    val X_MIN_SPEED = 0.05f
    val X_MAX_SPEED = 1.95f
    val Y_MIN_SPEED = 0.5f
    val Y_MAX_SPEED = 1.5f

    internal var xv: Float = 0.0f
    var yv: Float = 0.0f

    internal var xDirection: Int = 0
    var yDirection: Int = 0

    internal var speedMultiplicator: Float = 0.toFloat()
    var displaySizeSpeedMultiplicator = 1f

    init {
        displaySizeSpeedMultiplicator = view.width / 200.0f

        speedMultiplicator = BASE_SPEED_MULTIPLICATOR * displaySizeSpeedMultiplicator

        xv = 0.5f
        yv = (1.3f + Math.random() * 0.3f).toFloat()

        xDirection = if (Math.random() >= 0.5) {
            DIRECTION_RIGHT
        } else {
            DIRECTION_LEFT
        }
        yDirection = DIRECTION_DOWN
    }

    fun limit() {

        if (xv < X_MIN_SPEED) {
            xv = X_MIN_SPEED
            yv = X_MAX_SPEED
        }

        if (yv < Y_MIN_SPEED) {
            yv = Y_MIN_SPEED
            xv = Y_MAX_SPEED
        }
    }
}