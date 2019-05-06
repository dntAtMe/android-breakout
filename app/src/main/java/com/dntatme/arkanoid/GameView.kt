package com.dntatme.arkanoid

import android.content.Context
import android.content.Entity
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.dntatme.arkanoid.components.CollisionDetector
import com.dntatme.arkanoid.entities.Ball
import com.dntatme.arkanoid.entities.Block
import com.dntatme.arkanoid.entities.Entities
import com.dntatme.arkanoid.entities.Paddle
import com.dntatme.arkanoid.helpers.Point
import com.dntatme.arkanoid.helpers.TouchEventListener
import com.dntatme.arkanoid.helpers.UpdateEventListener
import kotlin.math.roundToInt

class GameView(context: Context): SurfaceView(context), SurfaceHolder.Callback  {

    private lateinit var touchEventListeners: ArrayList<TouchEventListener>
    private lateinit var updateEventListeners: ArrayList<UpdateEventListener>

    var gameThread: GameThread? = null

    init {
        holder.addCallback(this)
    }

    fun startGame() {

        val paddleHeight = Entities.blockSize
        val paddleWidth = paddleHeight * 6
        val x = this.width * 0.5
        val y = this.height * 0.88

        val paddle = Paddle(this, Color.WHITE, 1, Point(x.roundToInt(), y.roundToInt(), 0), paddleWidth, paddleHeight)
        val ball = Ball(this, Color.GREEN, 2, Point(this.width / 2, this.height / 2, 0), 20)
        val block1 = Block(this, Color.RED, 3, Point(120, 100, 0), 80,80, true)
        val block2 = Block(this, Color.RED, 3, Point(240, 100, 0), 80,80, true)
        val block3 = Block(this, Color.RED, 3, Point(360, 100, 0), 80,80, true)
        val block4 = Block(this, Color.GRAY, 3, Point(480, 100, 0), 80,80, false)

        val collisionDetector = CollisionDetector()
        touchEventListeners = ArrayList()
        updateEventListeners = ArrayList()

        touchEventListeners.add(paddle)
        updateEventListeners.add(ball)
        updateEventListeners.add(paddle)
        updateEventListeners.add(collisionDetector)
        Entities.drawableEntities[0] = ball
        Entities.drawableEntities[1] = paddle
        Entities.drawableEntities[2] = block1
        Entities.drawableEntities[3] = block2
        Entities.drawableEntities[4] = block3
        Entities.drawableEntities[5] = block4

    }

    fun update() {
        for (listener in updateEventListeners) {
            listener.handleUpdateEvent(this)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        for (listener in touchEventListeners) {
            listener.handleTouchEvent(this, event)
        }
        return true
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        startGame()
        gameThread = GameThread(holder, this)
        gameThread?.start()
    }


    fun onPause() {

    }

    fun onDestroy() {
        gameThread?.running = false
    }

}