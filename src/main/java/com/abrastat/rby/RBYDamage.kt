package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.logCriticalHit
import com.abrastat.general.Messages.Companion.logTypeEffectiveness
import com.abrastat.rby.RBYTypeEffectiveness.calcEffectiveness
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.floor

class RBYDamage {
    // this program implements all damage
    companion object {
        fun calcDamage(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                attack: RBYMove,
                allowCrits: Boolean = true): Int {
            val speedStat: Int = attackingPokemon.baseSpeed
            val focusEnergy: Boolean = attackingPokemon.volatileStatus.contains(Status.FOCUSENERGY)
            val crit: Int = if (allowCrits)
                critModifier(attack, speedStat, focusEnergy)
            else 1

            var damage: Int = calcDamageUtil(attackingPokemon, defendingPokemon, attack, crit)

            // 84.77% to 100% damage
            damage *= if (attack != RBYMove.CONFUSE_DAMAGE) damageRoll() else 255
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
            when (attack.effect) {
                MoveEffect.DOUBLEATTACK,
                MoveEffect.TWINNEEDLE,
                -> damage *= 2

                MoveEffect.MULTIHIT -> damage *= 5

                else -> {}
            }
            damage = damage.coerceAtMost(defendingPokemon.currentHP)
            return damage
        }

        fun calcAccuracy(attackingPokemon: RBYPokemon,
                         defendingPokemon: RBYPokemon,
                         moveAccuracy: Int): Int {
            if (moveAccuracy == Int.MAX_VALUE) return moveAccuracy

            // https://gamefaqs.gamespot.com/gameboy/367023-pokemon-red-version/faqs/64175/evade-and-accuracy
            val accuracyMultiplier = attackingPokemon.multiplier(attackingPokemon.accMod)
            val evasionMultiplier = defendingPokemon.multiplier(-defendingPokemon.evaMod)
            return floor( floor(moveAccuracy.toDouble()
                    * accuracyMultiplier) * evasionMultiplier).toInt()
        }

        fun applyDamage(pokemon: RBYPokemon, rawDamage: Int): Int {
            if (rawDamage == Int.MIN_VALUE) return 0

            val damage = rawDamage.coerceAtMost(pokemon.currentHP)
            pokemon.applyDamage(damage)
            pokemon.lastDamageTaken = damage
            Messages.logDamageTaken(pokemon, damage)
            return damage
        }

        fun toxicDamage(pokemon: RBYPokemon): Int {
            val damage = (pokemon.toxicCounter * pokemon.statHP / 16).coerceAtLeast(1)
            if (pokemon.toxicCounter > 1
                    || pokemon.nonVolatileStatus == Status.TOXIC)
                pokemon.toxicCounter++
            return applyDamage(pokemon, damage)
        }

        private fun calcDamageUtil(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                attack: RBYMove,
                crit: Int): Int {
            val level: Int = attackingPokemon.level
            val basePower: Int = attack.basePower

            // https://gamefaqs.gamespot.com/gameboy/367023-pokemon-red-version/faqs/64175/damage-calculation
            var attackStat: Int
            var defenseStat: Int
            val modifiedAttack: Stat
            val modifiedDefense: Stat
            if (attack.isPhysical) {
                attackStat = attackingPokemon.originalAttack
                defenseStat = defendingPokemon.statDef
                modifiedAttack = Stat.ATTACK
                modifiedDefense = Stat.DEFENSE
            } else {
                attackStat = attackingPokemon.statSp
                defenseStat = defendingPokemon.statSp
                modifiedAttack = Stat.SPECIAL
                modifiedDefense = Stat.SPECIAL
            }

            if (crit == 1) {        // no crit
                attackStat = attackingPokemon.modifiedStat(modifiedAttack)
                defenseStat = defendingPokemon.modifiedStat(modifiedDefense)

                // https://bulbapedia.bulbagarden.net/wiki/Reflect_(move)#Generation_I
                // https://bulbapedia.bulbagarden.net/wiki/Light_Screen_(move)#Generation_I
                if ((attack.isPhysical && defendingPokemon.volatileStatus.contains(Status.REFLECT)
                                && attack != RBYMove.CONFUSE_DAMAGE)
                        || !attack.isPhysical && defendingPokemon.volatileStatus.contains(Status.LIGHTSCREEN)) {
                    defenseStat *= 2
                    defenseStat %= 1024
                }
            } else attackStat *= 2  // crit

            if (attackStat > 255 || defenseStat > 255) {
                attackStat /= 4
                defenseStat /= 4
            }

            val typeEffectiveness = calcEffectiveness(attack.type,
                    defendingPokemon.types[0],
                    defendingPokemon.types[1])
            var damage = damageFormula(level, crit, basePower, attackStat, defenseStat, typeEffectiveness)

            if (attackingPokemon.types.contains(attack.type)
                    && attack.type != Type.NONE)
                damage = floor(damage * 1.5).toInt()

            if (typeEffectiveness != 1.0 && attack.isAttack)
                logTypeEffectiveness(typeEffectiveness.toInt())
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
            val statModifier: Double = floor(levelModifier * basePower
                    * attackStat.coerceAtLeast(1)
                    / defenseStat.coerceAtLeast(1)) // original crashed dividing by 0
            return ((floor( statModifier / 50) + 2) * typeEffectiveness).toInt()
        }

        private fun critModifier(attack: RBYMove,
                                 speed: Int,
                                 focus: Boolean): Int {
            var crit: Int = speed
            if (focus) crit = speed / 4
            if (attack.effect == MoveEffect.CRITRATE)
                crit = (crit * 8).coerceAtMost(511)
            return if (critRoll() <= crit) {
                logCriticalHit()
                2
            } else 1
        }

        private fun critRoll(): Int {
            return ThreadLocalRandom.current().nextInt(512)
        }

        private fun damageRoll(): Int {
            return ThreadLocalRandom.current().nextInt(217, 256)
        }
    }
}