package com.dntatme.arkanoid

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.SurfaceHolder
import com.dntatme.arkanoid.entities.Block
import com.dntatme.arkanoid.entities.Entities
import com.dntatme.arkanoid.entities.Paddle
import com.dntatme.arkanoid.helpers.Point

val MAX_FPS = 60
val FRAME_PERIOD = 1000 / MAX_FPS

class GameThread(var surfaceHolder: SurfaceHolder, var gameView: GameView): Thread() {
    var running: Boolean = true

    var beginTime: Long = 0
    var timeDiff: Long = 0
    var sleepTime: Int = 0

    var canvas: Canvas? = null
    var block = Block(gameView, Color.RED, 1, Point(10, 10, 10), 100, 100, false)

    override fun run() {

        while (running) {
            canvas = null

            try {

                beginTime = System.currentTimeMillis()

                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    if (canvas != null) {
                        gameView.update()
                        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                        val entities = Entities.drawableEntities.values
                        for (entity in entities) {
                            entity.draw(canvas, gameView)
                        }
                        Log.d("DRAWN", "block")
                    }
                }

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}