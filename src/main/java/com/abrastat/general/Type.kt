package com.abrastat.general

import com.abrastat.general.Messages.Companion.notImplementedYet

enum class Type {
    NONE,
    BUG,
    DARK,
    DRAGON,
    ELECTRIC,
    FAIRY,
    FIGHTING,
    FIRE,
    FLYING,
    GHOST,
    GRASS,
    GROUND,
    ICE,
    NORMAL,
    POISON,
    PSYCHIC,
    ROCK,
    STEEL,
    WATER;

    override fun toString(): String {
        return name
    }
}