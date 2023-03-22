package com.abrastat.rby

import com.abrastat.general.Messages.Companion.logCriticalHit
import com.abrastat.general.Messages.Companion.logTypeEffectiveness
import com.abrastat.rby.RBYTypeEffectiveness.calcEffectiveness
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.floor

enum class RBYDamageCalc {
    INSTANCE;
    companion object {
        fun calcDamage(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                attack: RBYMove): Int {
            val speedStat: Int = attackingPokemon.baseSpeed
            val crit: Int = critModifier(attack, speedStat)

            var damage: Int = calcDamageUtil(attackingPokemon, defendingPokemon, attack, crit)

            // 84.77% to 100% damage
            damage *= damageRoll()
            damage = floor((damage / 255).toDouble()).toInt()

            damage = damage.coerceAtMost(defendingPokemon.currentHP)
            return damage
        }

        // used for pre-processing
        @JvmStatic
        fun calcDamageEstimate(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                attack: RBYMove,
                isCrit: Boolean): Int {
            val crit = if (isCrit) 1 else 2

            var damage: Int = calcDamageUtil(attackingPokemon, defendingPokemon, attack, crit)

            // always assumes max damage roll
            damage = damage.coerceAtMost(defendingPokemon.currentHP)
            return damage
        }

        private fun calcDamageUtil(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                attack: RBYMove,
                crit: Int): Int {
            val level: Int = attackingPokemon.level
            val basePower: Int = attack.basePower

            // https://gamefaqs.gamespot.com/gameboy/367023-pokemon-red-version/faqs/64175/stat-modifiers
            var attackStat: Int = if (attack.isPhysical) attackingPokemon.statAtk else attackingPokemon.statSp
            var defenseStat: Int = if (attack.isPhysical) defendingPokemon.statDef else defendingPokemon.statSp
            if (attackStat > 255 || defenseStat > 255) {
                attackStat /= 4
                defenseStat /= 4
            }
            val attackModifier: Int = if (attack.isPhysical) attackingPokemon.atkMod else attackingPokemon.spMod
            val defenseModifier: Int = if (attack.isPhysical) defendingPokemon.defMod else defendingPokemon.spMod
            if (crit == 1) {    // no crit
                if (attackModifier != 0) attackStat = modify(attackModifier, attackStat)
                if (defenseModifier != 0) defenseStat = modify(defenseModifier, defenseStat)
            } else {            // crit
                attackStat *= 2
            }
            var typeEffectiveness = calcEffectiveness(attack.type,
                    defendingPokemon.types[0],
                    defendingPokemon.types[1])

            var damage = damageFormula(level, crit, basePower, attackStat, defenseStat, typeEffectiveness)
            if (attackingPokemon.types.contains(attack.type)) { // type bonus
                damage = floor(damage * 1.5).toInt()
            }

            if (typeEffectiveness != 1.0) {
                // nve will be 0 < typeEffectiveness < 1, so multiply by 10 to get around this
                // dirty fix and not optimised, come back and change if performance needs it
                typeEffectiveness *= 10.0
                logTypeEffectiveness(typeEffectiveness.toInt())
            }
            return damage
        }

        private fun damageFormula(
                level: Int,
                critModifier: Int,
                basePower: Int,
                attackStat: Int,
                defenseStat: Int,
                typeEffectiveness: Double): Int {
            // https://bulbapedia.bulbagarden.net/wiki/Damage#Generation_I
            val levelModifier: Double = floor((level * critModifier * 2 / 5).toDouble()) + 2
            val statModifier: Double = if (defenseStat != 0) {
                floor(levelModifier * basePower
                        * 1.coerceAtLeast(attackStat)
                        / 1.coerceAtLeast(defenseStat))
            } else 0.0  // divide by 0
            return ((floor( statModifier / 50) + 2) * typeEffectiveness).toInt()
        }

        private fun critModifier(attack: RBYMove, speed: Int): Int {
            val incCrit: Array<RBYMove> = arrayOf(RBYMove.SLASH, RBYMove.RAZOR_LEAF, RBYMove.CRABHAMMER)
            var crit: Int = speed
            if (incCrit.contains(attack)) crit = (speed * 8).coerceAtMost(511)
            return if (critRoll() <= crit) {
                logCriticalHit()
                2
            } else 1
        }

        private fun critRoll(): Int {
            return ThreadLocalRandom.current().nextInt(512)
        }

        private fun modify(modifier: Int, stat: Int): Int {
            val statModifier: Array<Double> = arrayOf(0.25, 0.28, 0.33,
                    0.4, 0.5, 0.66, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0)
            var modifiedStat = floor(statModifier[modifier + 6] * stat.toDouble()).toInt()
            modifiedStat = modifiedStat.coerceIn(1, 999)
            return modifiedStat
        }

        private fun damageRoll(): Int {
            return ThreadLocalRandom.current().nextInt(217, 256)
        }
    }
}