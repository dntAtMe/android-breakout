package com.dntatme.arkanoid.entities

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import com.dntatme.arkanoid.GameView
import com.dntatme.arkanoid.drawable.RectShape
import com.dntatme.arkanoid.helpers.Point
import com.dntatme.arkanoid.helpers.TouchEventListener
import com.dntatme.arkanoid.helpers.UpdateEventListener

class Paddle(
    view: GameView,
    color: Int,
    soundId: Int,
    point: Point,
    var width: Int,
    var height: Int
) :
    DrawableEntity(view, color, soundId, point, RectShape(point, width, height, colorId = color)
    ), TouchEventListener, UpdateEventListener
{

    var touched = false

    override fun draw(canvas: Canvas?, view: GameView) {
        shape.draw(canvas)

    }

    override fun handleTouchEvent(view: GameView, event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val eventX = event.x
            val eventY = event.y

            touched = if (eventX >= point.x - height * 2 && eventX <= point.x + height * 2) {
                eventY >= point.y + height / 2 && eventY <= point.y + height / 2 + 4 * height
            } else {
                false
            }
        }

        if (touched) {
            Log.d("GAME_BAR", "TOUCHED")
            if (event.action == MotionEvent.ACTION_MOVE) {
                point.x = event.x.toInt()
                Log.d("GAME_BAR", point.x.toString())
            }
        }

        if (event.action == MotionEvent.ACTION_UP) {
            touched = false
        }
    }

    override fun handleUpdateEvent(view: GameView) {

    }
}