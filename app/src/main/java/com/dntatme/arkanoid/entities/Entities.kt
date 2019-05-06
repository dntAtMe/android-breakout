package com.dntatme.arkanoid.entities

object Entities {

    var drawableEntities: HashMap<Int, DrawableEntity> = HashMap()
    var blockSize = 50
    val BALL = 0
    val BAR = 1
    var lastId = 1

    public fun getAllEntities(): List<Entity> {
        return ArrayList<Entity>(drawableEntities.values)
    }

    fun putAll(other: List<DrawableEntity>) {
        for (o in other) {
            drawableEntities.put(++lastId, o)
        }
    }


}