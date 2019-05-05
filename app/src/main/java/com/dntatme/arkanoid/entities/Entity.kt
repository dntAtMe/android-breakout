package com.dntatme.arkanoid.entities

import com.dntatme.arkanoid.GameView
import com.dntatme.arkanoid.helpers.Point

abstract class Entity(var view: GameView, var soundId: Int, var point: Point) {

    abstract fun update(interval: Double)

    fun playSound() {
        view.playSoundEffect(soundId)
    }

}