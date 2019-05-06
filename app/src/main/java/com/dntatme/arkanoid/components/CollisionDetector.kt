package com.dntatme.arkanoid.components

import com.dntatme.arkanoid.GameView
import com.dntatme.arkanoid.helpers.Component
import com.dntatme.arkanoid.helpers.UpdateEventListener
import android.R.attr.y
import android.R.attr.x
import android.graphics.Rect
import android.graphics.Region
import android.support.v4.math.MathUtils.clamp
import android.util.Log
import com.dntatme.arkanoid.drawable.RectShape
import com.dntatme.arkanoid.entities.*
import com.dntatme.arkanoid.helpers.Velocity
import java.lang.Math.pow
import java.util.HashMap
import kotlin.math.abs
import android.view.View.Y
import android.view.View.X




class CollisionDetector: UpdateEventListener, Component {

    private val NO_HIT = 0
    private val TOP = 1
    private val RIGHT = 2
    private val BOTTOM = 3
    private val LEFT = 4
    private val TOP_LEFT = 5
    private val TOP_RIGHT = 6
    private val BOTTOM_RIGHT = 7
    private val BOTTOM_LEFT = 8

    private var recentBarCollisionHelper = 0

    fun checkGameSpaceCollisions(view: GameView) {

        val ball = Entities.drawableEntities[Entities.BALL] as Ball

        // check collision with right wall if heading right
        if (ball.velocity.xDirection === 1 && ball.point.x + ball.radius >= view.width) {
            ball.velocity.xDirection *= -1

        }
        // check collision with left wall if heading left
        if (ball.velocity.xDirection === -1 && ball.point.x - ball.radius <= 0) {
            ball.velocity.xDirection *= -1

        }
        // check collision with bottom wall if heading down
        if (ball.velocity.yDirection === 1 && ball.point.y + ball.radius >= view.height) {
            ball.velocity.yDirection *= -1

            //GAME OVER
            view.startGame()

        }
        // check collision with top wall if heading up
        if (ball.velocity.yDirection === -1 && ball.point.y - ball.radius <= 0) {
            ball.velocity.yDirection *= -1
        }
    }

    private fun checkBarCollision(view: GameView) {
        val ball = Entities.drawableEntities[Entities.BALL] as Ball
        val paddle = Entities.drawableEntities[Entities.BAR] as Paddle

        if (recentBarCollisionHelper <= 0) {

            val intersection = intersects(ball, paddle)
            if (intersection) {
                Log.d("GAME_COLLISION", "INTERSECTION")
                val diffX = paddle.point.x - ball.point.x
                val maxDiff = paddle.width / 2

                val diffRatio = Math.abs(diffX) / maxDiff.toFloat() * 2f
                ball.velocity.xv = diffRatio
                ball.velocity.yv = 2 - diffRatio
                Log.d("GAME_COLLISION", diffRatio.toString())
                ball.velocity.yDirection *= -1

                if (diffX < 0) {
                    ball.velocity.xDirection = 1
                } else {
                    ball.velocity.xDirection = -1
                }

                recentBarCollisionHelper = 10
            }
        } else {
            recentBarCollisionHelper--
        }
    }

    private fun checkBlockCollisions(view: GameView) {
        val detectedCollisions = HashMap<Int, Block>()
        val ball = Entities.drawableEntities[Entities.BALL] as Ball
        val blocks = Entities.drawableEntities.values.drop(2) as List<Block>

        for (block in blocks) {
            if (block.hitPoints > 0) {
                if (intersects(ball, block)) {
                    block.onBlockHit()
                    Log.d("GAME_COLLISION", "INTERSECTION")
                    val hitDirection = checkHitDirection(ball, block)
                    Log.d("GAME_COLLISION", hitDirection.toString())

                    if (hitDirection != NO_HIT) {
                        detectedCollisions[hitDirection] = block
                    }
                }

            }
        }
        if (detectedCollisions.size == 0) {
            return
        } else {
            val directions = calculatePossibleBallDirections(detectedCollisions)
            setBallDirectionBasedOnPossibleDirections(ball, directions)

        }
    }

    private fun calculatePossibleBallDirections(collisionDetected: Map<Int, Block>): BooleanArray {

        // Because top =1, right = 4
        val directionArray = BooleanArray(5)
        directionArray[TOP] = true
        directionArray[LEFT] = true
        directionArray[BOTTOM] = true
        directionArray[RIGHT] = true

        val hitTypes = collisionDetected.keys

        for (hitType in hitTypes) {

            when (hitType) {
                TOP -> {
                    directionArray[BOTTOM] = false
                }
                RIGHT -> {
                    directionArray[LEFT] = false
                }
                BOTTOM -> {
                    directionArray[TOP] = false
                }
                LEFT -> {
                    directionArray[RIGHT] = false
                }
                TOP_LEFT -> {
                    directionArray[BOTTOM] = false
                    directionArray[RIGHT] = false
                }
                TOP_RIGHT -> {
                    directionArray[BOTTOM] = false
                    directionArray[LEFT] = false
                }
                BOTTOM_LEFT -> {
                    directionArray[TOP] = false
                    directionArray[RIGHT] = false
                }
                BOTTOM_RIGHT -> {
                    directionArray[TOP] = false
                    directionArray[LEFT] = false
                }
            }
        }
        return directionArray
    }

    private fun setBallDirectionBasedOnPossibleDirections(ball: Ball, directions: BooleanArray) {


        if (directions[TOP] && !directions[BOTTOM]) {
            ball.velocity.yDirection = 1
        }

        if (directions[BOTTOM] && !directions[TOP]) {
            ball.velocity.yDirection = -1
        }

        if (directions[RIGHT] && !directions[LEFT]) {
            ball.velocity.xDirection = -1
        }

        if (directions[LEFT] && !directions[RIGHT]) {
            ball.velocity.xDirection = 1
        }
    }


    private fun intersects(ball: Ball, paddle: Paddle): Boolean {
        // Find the closest point to the circle within the rectangle
        val closestX = clamp(ball.point.x, paddle.point.x - paddle.width / 2, paddle.point.x + paddle.width / 2)
        val closestY = clamp(ball.point.y, paddle.point.y - paddle.height / 2, paddle.point.y + paddle.height / 2)

        // Calculate the distance between the circle's center and this closest point
        val distanceX = ball.point.x - closestX
        val distanceY = ball.point.y - closestY

        // If the distance is less than the circle's radius, an intersection occurs
        val distanceSquared = distanceX * distanceX + distanceY * distanceY
        return distanceSquared < ball.radius * ball.radius

    }

    private fun intersects(ball: Ball, block: Block): Boolean {
        // Find the closest point to the circle within the rectangle
        val closestX = clamp(ball.point.x, block.point.x - block.width / 2, block.point.x + block.width / 2)
        val closestY = clamp(ball.point.y, block.point.y - block.height / 2, block.point.y + block.height / 2)

        // Calculate the distance between the circle's center and this closest point
        val distanceX = ball.point.x - closestX
        val distanceY = ball.point.y - closestY

        // If the distance is less than the circle's radius, an intersection occurs
        val distanceSquared = distanceX * distanceX + distanceY * distanceY
        return distanceSquared < ball.radius * ball.radius
    }

    private fun checkHitDirection(ball: Ball, block: Block): Int {

        val distX = (ball.point.x - block.point.x)
        val distY = (ball.point.y - block.point.y)

        // Top hit
        if (distY > 0 && Math.abs(distX) < Math.abs(distY)) {
            return TOP
        }

        // Bottom hit
        if (distY < 0 && Math.abs(distX) < Math.abs(distY)) {
            return BOTTOM
        }

        // Left hit
        if (distX > 0 && Math.abs(distX) > Math.abs(distY)) {
            return LEFT

        }

        // Right hit
        if (distX < 0 && Math.abs(distX) > Math.abs(distY)) {
            return RIGHT
        }

        if (Math.abs(distX) != Math.abs(distY)) {
            throw RuntimeException("NOT SO CORNER HIT ARE YOU ?")
        }

        // TopLeft hit
        if (distX > 0 && distY > 0) {
            return TOP_LEFT
        }

        // TopRight hit
        if (distX < 0 && distY > 0) {
            return TOP_RIGHT
        }

        // BottomLeft hit
        if (distX > 0 && distY < 0) {
            return BOTTOM_LEFT
        }

        // BottomRight hit
        if (distX < 0 && distY < 0) {
            return BOTTOM_RIGHT
        }

        return NO_HIT

    }



    override fun handleUpdateEvent(view: GameView) {
        checkBarCollision(view)
        checkBlockCollisions(view)
        checkGameSpaceCollisions(view)
    }

}