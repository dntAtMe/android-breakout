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
import com.dntatme.arkanoid.helpers.Score

const val MAX_FPS = 30
const val FRAME_PERIOD = 1000 / MAX_FPS

class GameThread(var surfaceHolder: SurfaceHolder, var gameView: GameView): Thread() {
    var running: Boolean = true

    var beginTime: Long = 0
    var timeDiff: Long = 0
    var sleepTime: Long = 0

    var canvas: Canvas? = null

    override fun run() {

        while (running) {
            canvas = null
            beginTime = System.nanoTime()
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    if (canvas != null) {
                        gameView.update()
                        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        val entities = Entities.drawableEntities.values
                        for (entity in entities) {
                            entity.draw(canvas, gameView)
                        }
                        Score.textShape.draw(canvas)
                        Log.d("DRAWN", "block")
                    }
                    timeDiff = (System.nanoTime() - beginTime) / 1000000

                    sleepTime = (FRAME_PERIOD - timeDiff)


                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime)
                        } catch (e: InterruptedException) {
                        }

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