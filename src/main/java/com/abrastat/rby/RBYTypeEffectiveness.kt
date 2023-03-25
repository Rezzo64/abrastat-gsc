package com.abrastat.rby

import com.abrastat.general.Type
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table

object RBYTypeEffectiveness {
    // Below table is a 2D representation of a type effectiveness chart.
    // Row = attacking type
    // Column = defending type
    // Value = damage multiplier
    private var TYPECHART: Table<Type, Type, Double>? = null
    init {
        TYPECHART = HashBasedTable.create()
        for (i in 0..18) {
            for (j in 0..18) {
                (TYPECHART as HashBasedTable<Type,  Type, Double>).put(
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
        // https://bulbapedia.bulbagarden.net/wiki/Type/Type_chart#Generation_I
        val result: Double = when (attackingType) {
            Type.NONE -> 1.0
            Type.BUG -> bugEffectiveness(defendingType)
            Type.DARK -> 1.0
            Type.DRAGON -> dragonEffectiveness(defendingType)
            Type.ELECTRIC -> electricEffectiveness(defendingType)
            Type.FAIRY -> 1.0
            Type.FIGHTING -> fightingEffectiveness(defendingType)
            Type.FIRE -> fireEffectiveness(defendingType)
            Type.FLYING -> flyingEffectiveness(defendingType)
            Type.GHOST -> ghostEffectiveness(defendingType)
            Type.GRASS -> grassEffectiveness(defendingType)
            Type.GROUND -> groundEffectiveness(defendingType)
            Type.ICE -> iceEffectiveness(defendingType)
            Type.NORMAL -> normalEffectiveness(defendingType)
            Type.POISON -> poisonEffectiveness(defendingType)
            Type.PSYCHIC -> psychicEffectiveness(defendingType)
            Type.ROCK -> rockEffectiveness(defendingType)
            Type.STEEL -> 1.0
            Type.WATER -> waterEffectiveness(defendingType)
        }
        return result
    }

    private fun bugEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.GRASS,
            Type.POISON,
            Type.PSYCHIC,
            -> 2.0

            Type.FIGHTING,
            Type.FIRE,
            Type.FLYING,
            Type.GHOST,
            -> 0.5

            else -> 1.0
        }
    }

    private fun dragonEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.DRAGON -> 2.0

            else -> 1.0
        }
    }

    private fun electricEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.FLYING,
            Type.WATER,
            -> 2.0

            Type.DRAGON,
            Type.ELECTRIC,
            Type.GRASS,
            -> 0.5

            Type.GROUND -> 0.0

            else -> 1.0
        }
    }

    private fun fightingEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.ICE,
            Type.NORMAL,
            Type.ROCK,
            -> 2.0

            Type.BUG,
            Type.FLYING,
            Type.POISON,
            Type.PSYCHIC,
            -> 0.5

            Type.GHOST -> 0.0

            else -> 1.0
        }
    }

    private fun fireEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.BUG,
            Type.GRASS,
            Type.ICE,
            -> 2.0

            Type.DRAGON,
            Type.FIRE,
            Type.ROCK,
            Type.WATER,
            -> 0.5

            else -> 1.0
        }
    }

    private fun flyingEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.BUG,
            Type.FIGHTING,
            Type.GRASS,
            -> 2.0

            Type.ELECTRIC,
            Type.ROCK,
            -> 0.5

            else -> 1.0
        }
    }

    private fun ghostEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.GHOST -> 2.0

            Type.NORMAL,
            Type.PSYCHIC,
            -> 0.0

            else -> 1.0
        }
    }

    private fun grassEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.GROUND,
            Type.ROCK,
            Type.WATER,
            -> 2.0

            Type.BUG,
            Type.DRAGON,
            Type.FIRE,
            Type.FLYING,
            Type.GRASS,
            Type.POISON,
            -> 0.5

            else -> 1.0
        }
    }

    private fun groundEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.ELECTRIC,
            Type.FIRE,
            Type.POISON,
            Type.ROCK,
            -> 2.0

            Type.BUG,
            Type.GRASS,
            -> 0.5

            Type.FLYING -> 0.0

            else -> 1.0
        }
    }

    private fun iceEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.DRAGON,
            Type.FLYING,
            Type.GRASS,
            Type.GROUND,
            -> 2.0

            Type.ICE,
            Type.WATER,
            -> 0.5

            else -> 1.0
        }
    }

    private fun normalEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.ROCK-> 0.5

            Type.GHOST -> 0.0

            else -> 1.0
        }
    }

    private fun poisonEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.BUG,
            Type.GRASS,
            -> 2.0

            Type.GHOST,
            Type.GROUND,
            Type.POISON,
            Type.ROCK,
            -> 0.5

            else -> 1.0
        }
    }

    private fun psychicEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.FIGHTING,
            Type.POISON,
            -> 2.0

            Type.PSYCHIC -> 0.5

            else -> 1.0
        }
    }

    private fun rockEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.BUG,
            Type.FIRE,
            Type.FLYING,
            Type.ICE,
            -> 2.0

            Type.FIGHTING,
            Type.GROUND,
            -> 0.5

            else -> 1.0
        }
    }

    private fun waterEffectiveness(defendingType: Type): Double {
        return when (defendingType) {
            Type.FIRE,
            Type.GROUND,
            Type.ROCK,
            -> 2.0

            Type.DRAGON,
            Type.GRASS,
            Type.WATER,
            -> 0.5

            else -> 1.0
        }
    }
}