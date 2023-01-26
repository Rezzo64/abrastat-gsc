package com.abrastat.gsc

import com.abrastat.general.Type
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import org.jetbrains.annotations.Contract

// Copy this chunk into another package for implementation and modify,
// Or just extend this class and override I suppose
object GSCTypeEffectiveness {
    // Below table is a 2D representation of a type effectiveness chart.
    // Row = attacking type
    // Column = defending type
    // Value = damage multiplier
    private var TYPECHART: Table<Type, Type, Double>? = null

    init {
        TYPECHART = HashBasedTable.create()
        for (i in 0..17) {
            for (j in 0..17) {
                (TYPECHART as HashBasedTable<Type, Type, Double>).put(
                        Type.values()[i],
                        Type.values()[j],  // deduce attacking type vs
                        // defending type dmg factor
                        calcEffectivenessHelper(
                                Type.values()[i],
                                Type.values()[j]
                        )
                )
            }
        }
    }

    /*  This is the entry method to using the type effectiveness chart and outputs
    *   the effectiveness given an attacking type and two defending types.
    *   If a Pokémon has no secondary typing, set the secondary type to 'NONE'.
    */
    @JvmStatic
    fun calcEffectiveness(attackingType: Type?, defendingType1: Type?, defendingType2: Type?): Double {

        //TODO fix this warning, maybe even cast it as a 2D array for performance rather than unboxing constantly.
        return (TYPECHART!![attackingType, defendingType1]!!
                *
                TYPECHART!![attackingType, defendingType2]!!)
    }

    private fun calcEffectivenessHelper(attackingType: Type, defendingType: Type): Double {

        // Manually define each type match-up combination now
        val result: Double = when (attackingType) {
            Type.NONE -> 1.0
            Type.NORMAL -> normalEffectiveness(defendingType)
            Type.FIRE -> fireEffectiveness(defendingType)
            Type.WATER -> waterEffectiveness(defendingType)
            Type.ELECTRIC -> electricEffectiveness(defendingType)
            Type.GRASS -> grassEffectiveness(defendingType)
            Type.ICE -> iceEffectiveness(defendingType)
            Type.FIGHTING -> fightingEffectiveness(defendingType)
            Type.POISON -> poisonEffectiveness(defendingType)
            Type.GROUND -> groundEffectiveness(defendingType)
            Type.FLYING -> flyingEffectiveness(defendingType)
            Type.PSYCHIC -> psychicEffectiveness(defendingType)
            Type.BUG -> bugEffectiveness(defendingType)
            Type.ROCK -> rockEffectiveness(defendingType)
            Type.GHOST -> ghostEffectiveness(defendingType)
            Type.DRAGON -> dragonEffectiveness(defendingType)
            Type.DARK -> darkEffectiveness(defendingType)
            Type.STEEL -> steelEffectiveness(defendingType)
            Type.FAIRY -> {
                println("using Fairy type attack in an old gen! " +
                        "is this an error?")
                return 1.0
            }
        }
        return result
    }

    private fun normalEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.ROCK, Type.STEEL -> 0.5
            Type.GHOST -> 0.0
            else -> 1.0
        }
    }

    private fun fireEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.GRASS, Type.ICE, Type.BUG, Type.STEEL -> 2.0
            Type.FIRE, Type.WATER, Type.ROCK, Type.DRAGON -> 0.5
            else -> 1.0
        }
    }

    private fun waterEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.FIRE, Type.GROUND, Type.ROCK -> 2.0
            Type.WATER, Type.GRASS, Type.DRAGON -> 0.5
            else -> 1.0
        }
    }

    private fun electricEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.WATER, Type.FLYING -> 2.0
            Type.ELECTRIC, Type.GRASS, Type.DRAGON -> 0.5
            Type.GROUND -> 0.0
            else -> 1.0
        }
    }

    private fun grassEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.WATER, Type.GROUND, Type.ROCK -> 2.0
            Type.FIRE, Type.GRASS, Type.POISON, Type.FLYING, Type.BUG, Type.DRAGON -> 0.5
            else -> 1.0
        }
    }

    private fun iceEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.GRASS, Type.GROUND, Type.FLYING, Type.DRAGON -> 2.0
            Type.FIRE, Type.WATER, Type.ICE, Type.STEEL -> 0.5
            else -> 1.0
        }
    }

    private fun fightingEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.NORMAL, Type.ICE, Type.ROCK, Type.DARK, Type.STEEL -> 2.0
            Type.POISON, Type.FLYING, Type.PSYCHIC, Type.BUG -> 0.5
            Type.GHOST -> 0.0
            else -> 1.0
        }
    }

    private fun poisonEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.GRASS -> 2.0
            Type.POISON, Type.GROUND, Type.ROCK, Type.GHOST -> 0.5
            Type.STEEL -> 0.0
            else -> 1.0
        }
    }

    private fun groundEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.FIRE, Type.ELECTRIC, Type.POISON, Type.ROCK, Type.STEEL -> 2.0
            Type.GRASS, Type.BUG -> 0.5
            Type.FLYING -> 0.0
            else -> 1.0
        }
    }

    private fun flyingEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.GRASS, Type.FIGHTING, Type.BUG -> 2.0
            Type.ELECTRIC, Type.ROCK, Type.STEEL -> 0.5
            else -> 1.0
        }
    }

    private fun psychicEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.FIGHTING, Type.POISON -> 2.0
            Type.PSYCHIC, Type.STEEL -> 0.5
            Type.DARK -> 0.0
            else -> 1.0
        }
    }

    private fun bugEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.GRASS, Type.PSYCHIC, Type.DARK -> 2.0
            Type.FIRE, Type.FIGHTING, Type.POISON, Type.FLYING, Type.GHOST, Type.STEEL -> 0.5
            else -> 1.0
        }
    }

    private fun rockEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.FIRE, Type.ICE, Type.FLYING, Type.BUG -> 2.0
            Type.FIGHTING, Type.GROUND, Type.STEEL -> 0.5
            else -> 1.0
        }
    }

    private fun ghostEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.PSYCHIC, Type.GHOST -> 2.0
            Type.DARK -> 0.5
            Type.NORMAL -> 0.0
            else -> 1.0
        }
    }

    private fun dragonEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.DRAGON -> 2.0
            Type.DARK, Type.STEEL -> 0.5
            else -> 1.0
        }
    }

    private fun darkEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.PSYCHIC, Type.GHOST -> 2.0
            Type.FIGHTING, Type.DARK, Type.STEEL -> 0.5
            else -> 1.0
        }
    }

    private fun steelEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.ICE, Type.ROCK -> 2.0
            Type.FIRE, Type.WATER, Type.ELECTRIC, Type.STEEL -> 0.5
            else -> 1.0
        }
    }
}