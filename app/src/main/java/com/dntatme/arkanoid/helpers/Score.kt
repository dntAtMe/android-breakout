package com.dntatme.arkanoid.helpers

import android.graphics.Color
import com.dntatme.arkanoid.drawable.TextShape
import org.w3c.dom.Text

object Score {
    var score: Int = 0
    lateinit var textShape: TextShape

    fun initText(point: Point, size: Float) {
        textShape = TextShape(point, colorId = Color.WHITE)
        textShape.paint.textSize = size
        textShape.text = score.toString()
    }

    fun increaseScore(amount: Int) {
        score += amount
        textShape.text = score.toString()
    }


}