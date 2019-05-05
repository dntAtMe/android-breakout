package com.dntatme.arkanoid.entities

object Entities {

    var drawableEntities: HashMap<Int, DrawableEntity> = HashMap()
    var blockSize = 50
    val BALL = 0
    val BAR = 1

    public fun getAllEntities(): List<Entity> {
        return ArrayList<Entity>(drawableEntities.values)
    }


}