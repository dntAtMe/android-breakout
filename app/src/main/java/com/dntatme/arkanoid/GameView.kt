package com.dntatme.arkanoid

import android.content.Context
import android.content.Entity
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.dntatme.arkanoid.components.CollisionDetector
import com.dntatme.arkanoid.entities.*
import com.dntatme.arkanoid.helpers.*
import kotlinx.android.synthetic.main.fragment_game.view.*
import kotlin.math.roundToInt
import kotlin.random.Random

class GameView(context: Context): SurfaceView(context), SurfaceHolder.Callback  {

    private lateinit var touchEventListeners: ArrayList<TouchEventListener>
    private lateinit var updateEventListeners: ArrayList<UpdateEventListener>
    lateinit var ball: Ball
    var gameThread: GameThread? = null

    init {
        holder.addCallback(this)
    }

    fun startGame() {

        val paddleHeight = Entities.blockSize
        val paddleWidth = paddleHeight * 6
        val x = this.width * 0.5
        val y = this.height * 0.88

        Score.initText( Point(10, (this.height * 0.78).toInt(), -1), Entities.blockSize.toFloat() )
        val paddle = Paddle(this, Color.WHITE, 1, Point(x.toInt(), y.toInt(), 0), paddleWidth, paddleHeight)
        ball = Ball(this, Color.GREEN, 2, Point((this.width / 2), (this.height / 2), 0), 20)


        val collisionDetector = CollisionDetector()
        touchEventListeners = ArrayList()
        updateEventListeners = ArrayList()

        Entities.drawableEntities.clear()
        Entities.lastId = 1
        touchEventListeners.add(paddle)
        updateEventListeners.add(ball)
        updateEventListeners.add(paddle)
        updateEventListeners.add(collisionDetector)
        Entities.drawableEntities[0] = ball
        Entities.drawableEntities[1] = paddle
        Entities.putAll(initLevel(80, 20))

    }

    private fun initLevel(blockSize: Int, space: Int): List<DrawableEntity> {
        var entities = ArrayList<DrawableEntity>()
        var hitPoints = 1
        var isDestructible: Boolean
        for (nextY in blockSize / 2 until height / 2 step blockSize + space) {
            for (nextX in blockSize / 2 until width step blockSize + space) {
                if (Random.nextFloat() < 0.2f) {
                    continue
                }
                isDestructible = Random.nextFloat() > 0.1f
                val color = if (isDestructible) {
                    hitPoints = Random.nextInt(1, 4)
                    BlockColors.colors[hitPoints-1]
                } else {
                    Color.GRAY
                }
                entities.add(Block(this, color, 3, Point(nextX, nextY, 0), blockSize,blockSize, hitPoints, isDestructible))
            }
        }

        return entities
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
        ball.velocity.displaySizeSpeedMultiplicator = width / 200.0f
        ball.velocity.speedMultiplicator = ball.velocity.BASE_SPEED_MULTIPLICATOR * ball.velocity.displaySizeSpeedMultiplicator

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