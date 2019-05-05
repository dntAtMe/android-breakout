package com.dntatme.arkanoid.helpers

import android.view.MotionEvent
import com.dntatme.arkanoid.GameView

interface TouchEventListener {
    public fun handleTouchEvent(view: GameView, event: MotionEvent)
}