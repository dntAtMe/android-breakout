package com.dntatme.arkanoid.entities

import android.graphics.Canvas
import com.dntatme.arkanoid.GameView
import com.dntatme.arkanoid.helpers.Point
import com.dntatme.arkanoid.drawable.RectShape
import com.dntatme.arkanoid.helpers.BlockColors
import com.dntatme.arkanoid.helpers.Score
import java.util.*

class Block(
    view: GameView,
    color: Int,
    soundId: Int,
    point: Point,
    var width: Int,
    var height: Int,
    var hitPoints: Int,
    var isDestructible: Boolean
) :
    DrawableEntity(view, color, soundId, point,
        RectShape(point, width, height, colorId = color)
    ) {

    var isHittable = true
    var originalHitPoints = hitPoints

    override fun update(interval: Double) {
        super.update(interval)

    }

    fun onBlockHit() {
        if (isHittable && isDestructible) {
            hitPoints--
            Score.increaseScore(10)
            if (hitPoints == 0) {
                isHittable = false
                refreshBlock(20)
            } else {
                shape.changeColor(BlockColors.colors[hitPoints-1])
            }
        }
    }

    override fun draw(canvas: Canvas?, view: GameView) {
        if (isHittable) {
            shape.draw(canvas)
        }
    }

    private fun refreshBlock(delay: Int) {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                isHittable = true
                point.z = 0
                hitPoints = originalHitPoints
                shape.changeColor(BlockColors.colors[hitPoints-1])
            }
        }, (delay * 1000).toLong())

    }
}