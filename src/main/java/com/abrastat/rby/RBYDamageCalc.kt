package com.abrastat.rby

import com.abrastat.general.Messages.Companion.logCriticalHit
import com.abrastat.general.Messages.Companion.logTypeEffectiveness
import com.abrastat.general.Pokemon
import com.abrastat.general.Type
import com.abrastat.rby.RBYTypeEffectiveness.calcEffectiveness
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.floor

enum class RBYDamageCalc {
    INSTANCE;
    companion object {
        private fun sameTypeAttackBonus(pokemon: Pokemon, type: Type): Boolean {
            return pokemon.types[0] == type || pokemon.types[1] == type
        }

        private fun critModifier(): Int {
            return if (critRoll() < 16) {
                logCriticalHit()
                2
            } else {
                1
            }
        }

        private fun critRoll(): Int {
            return ThreadLocalRandom.current().nextInt(256)
        }

        private fun damageRoll(): Int {
            return ThreadLocalRandom.current().nextInt(217, 256)
        }

        fun calcDamage(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                attack: RBYMove): Int {
            val level = attackingPokemon.level
            val basePower = attack.basePower
            var attackStat: Int = if (attack.isPhysical) attackingPokemon.statAtk else attackingPokemon.statSpA
            var defenseStat: Int = if (attack.isPhysical) defendingPokemon.statDef else defendingPokemon.statSpD
            if (attackStat > 255 || defenseStat > 255) {
                attackStat /= 4
                defenseStat /= 4
            }

            // Modifiers for effects from Growl, Screech, Focus Energy, etc.
            var attackModifier: Int
            var defenseModifier: Int
            var typeEffectiveness = calcEffectiveness(attack.type,
                    defendingPokemon.types[0],
                    defendingPokemon.types[1])
            var damage = damageFormula(level, attackStat, basePower, defenseStat, critModifier(), typeEffectiveness)
            if (sameTypeAttackBonus(attackingPokemon, attack.type!!)) {
                damage = floor(damage * 1.5).toInt()
            }
            if (typeEffectiveness != 1.0) {

                // nve will be 0 < typeEffectiveness < 1, so multiply by 10 to get around this
                // dirty fix and not optimised, come back and change if performance needs it
                typeEffectiveness *= 10.0
                logTypeEffectiveness(typeEffectiveness.toInt())
            }
            damage *= damageRoll()
            damage = floor((damage / 255).toDouble()).toInt()
            damage = damage.coerceAtMost(defendingPokemon.currentHP)
            return damage
        }

        // This is used for pre-processing - it always assumes max damage roll
        @JvmStatic
        fun calcDamageEstimate(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                attack: RBYMove,
                isCrit: Boolean): Int {
            val level = attackingPokemon.level
            val basePower = attack.basePower
            val critValue = if (isCrit) 1 else 255 // any crit mod value < 16 forces critical hit
            var attackStat: Int = if (attack.isPhysical) attackingPokemon.statAtk else attackingPokemon.statSpA
            var defenseStat: Int = if (attack.isPhysical) defendingPokemon.statDef else defendingPokemon.statSpD
            if (attackStat > 255 || defenseStat > 255) {
                attackStat /= 4
                defenseStat /= 4
            }

            // Modifiers for effects from Growl, Screech, Focus Energy, etc.
            var attackModifier: Int
            var defenseModifier: Int
            var typeEffectiveness = calcEffectiveness(attack.type,
                    defendingPokemon.types[0],
                    defendingPokemon.types[1])
            var damage = damageFormula(level, attackStat, basePower, defenseStat, critValue, typeEffectiveness)
            if (sameTypeAttackBonus(attackingPokemon, attack.type)) {
                damage = floor(damage * 1.5).toInt()
            }
            if (typeEffectiveness != 1.0) {

                // nve will be 0 < typeEffectiveness < 1, so multiply by 10 to get around this
                // dirty fix and not optimised, come back and change if performance needs it
                typeEffectiveness *= 10.0
                logTypeEffectiveness(typeEffectiveness.toInt())
            }
            damage = damage.coerceAtMost(defendingPokemon.currentHP)
            return damage
        }

        private fun damageFormula(
                level: Int,
                attackStat: Int,
                basePower: Int,
                defenseStat: Int,
                critModifier: Int,
                typeEffectiveness: Double): Int {
            return ((floor(floor((floor((level * 2 / 5).toDouble()) + 2) * 1.coerceAtLeast(attackStat) * basePower / 1.coerceAtLeast(defenseStat)) / 50) * critModifier + 2) * typeEffectiveness).toInt()
        }
    }
}