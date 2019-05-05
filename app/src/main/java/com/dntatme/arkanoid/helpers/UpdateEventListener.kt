package com.dntatme.arkanoid.helpers

import com.dntatme.arkanoid.GameView

interface UpdateEventListener {
    public fun handleUpdateEvent(view: GameView)
}