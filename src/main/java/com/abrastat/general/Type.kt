package com.abrastat.general

import com.abrastat.general.Messages.Companion.notImplementedYet

enum class Type {
    NONE, NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY;

    //      Not convinced I'll ever need this...
    //      Set "None" as secondary typing if Mon has only one type
    override fun toString(): String {
        return name
    }

    fun setTypes(type1: Type, type2: Type) {
        setType(type1)
        setType(type2)
    }

    private fun setType(type: Type) {
        var type: Type = type
        when (type) {
            NONE -> type = NONE
            NORMAL -> type = NORMAL
            FIRE -> type = FIRE
            WATER -> type = WATER
            ELECTRIC -> type = ELECTRIC
            GRASS -> type = GRASS
            ICE -> type = ICE
            FIGHTING -> type = FIGHTING
            POISON -> type = POISON
            GROUND -> type = GROUND
            FLYING -> type = FLYING
            PSYCHIC -> type = PSYCHIC
            BUG -> type = BUG
            ROCK -> type = ROCK
            GHOST -> type = GHOST
            DRAGON -> type = DRAGON
            DARK -> type = DARK
            STEEL -> type = STEEL
            FAIRY -> type = FAIRY
        }
    }
}