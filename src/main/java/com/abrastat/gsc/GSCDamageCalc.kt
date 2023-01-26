package com.abrastat.gsc

import com.abrastat.general.Item
import com.abrastat.general.Messages.Companion.logCriticalHit
import com.abrastat.general.Messages.Companion.logTypeEffectiveness
import com.abrastat.general.Pokemon
import com.abrastat.general.Type
import com.abrastat.gsc.GSCTypeEffectiveness.calcEffectiveness
import java.util.Map
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.floor

enum class GSCDamageCalc {
    INSTANCE;

    companion object { // TODO convert this to Kotlin map
        private val damageBoostingItems = Map.ofEntries(
                Map.entry(Item.BLACKGLASSES, Type.DARK),
                Map.entry(Item.BLACK_BELT, Type.FIGHTING),
                Map.entry(Item.CHARCOAL, Type.FIRE),
                Map.entry(Item.DRAGON_SCALE, Type.DRAGON),
                Map.entry(Item.HARD_STONE, Type.ROCK),
                Map.entry(Item.MAGNET, Type.ELECTRIC),
                Map.entry(Item.MIRACLE_SEED, Type.GRASS),
                Map.entry(Item.MYSTIC_WATER, Type.WATER),
                Map.entry(Item.NEVERMELTICE, Type.ICE),
                Map.entry(Item.PINK_BOW, Type.NORMAL),
                Map.entry(Item.POISON_BARB, Type.POISON),
                Map.entry(Item.POLKADOT_BOW, Type.NORMAL),
                Map.entry(Item.SHARP_BEAK, Type.FLYING),
                Map.entry(Item.SILVERPOWDER, Type.BUG),
                Map.entry(Item.SOFT_SAND, Type.GROUND),
                Map.entry(Item.SPELL_TAG, Type.GHOST),
                Map.entry(Item.TWISTEDSPOON, Type.PSYCHIC),
                Map.entry(Item.METAL_COAT, Type.STEEL)
        )

        private fun sameTypeAttackBonus(pokemon: Pokemon, type: Type): Boolean {
            return pokemon.types[0] == type || pokemon.types[1] == type
        }

        private fun calcItemBoost(itemBoostsDamage: Boolean): Double {
            return if (itemBoostsDamage) {
                1.1
            } else {
                1.0
            }
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
                attackingPokemon: GSCPokemon,
                defendingPokemon: GSCPokemon,
                attack: GSCMove): Int {
            val level = attackingPokemon.level
            val basePower = attack.basePower()
            val heldItem = attackingPokemon.heldItem
            var attackStat: Int = if (attack.isPhysical) attackingPokemon.statAtk else attackingPokemon.statSpA
            var defenseStat: Int = if (attack.isPhysical) defendingPokemon.statDef else defendingPokemon.statSpD
            if (attackStat > 255 || defenseStat > 255) {
                attackStat /= 4
                defenseStat /= 4
            }
            // comparing item's boosting type against selected attack's type
            val doesItemBoostDamage: Boolean = if (damageBoostingItems[heldItem] != null) {
                damageBoostingItems[heldItem] == attack.type()
            } else {
                false
            }

            // Modifiers for effects from Growl, Screech, Focus Energy, etc.
            var attackModifier: Int
            var defenseModifier: Int
            var typeEffectiveness = calcEffectiveness(attack.type(),
                    defendingPokemon.types[0],
                    defendingPokemon.types[1])
            var damage = damageFormula(level, attackStat, basePower, defenseStat, critModifier(), doesItemBoostDamage, typeEffectiveness)
            if (sameTypeAttackBonus(attackingPokemon, attack.type()!!)) {
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
                attackingPokemon: GSCPokemon,
                defendingPokemon: GSCPokemon,
                attack: GSCMove,
                isCrit: Boolean): Int {
            val level = attackingPokemon.level
            val basePower = attack.basePower()
            val critValue = if (isCrit) 1 else 255 // any crit mod value < 16 forces critical hit
            val heldItem = attackingPokemon.heldItem
            var attackStat: Int = if (attack.isPhysical) attackingPokemon.statAtk else attackingPokemon.statSpA
            var defenseStat: Int = if (attack.isPhysical) defendingPokemon.statDef else defendingPokemon.statSpD
            if (attackStat > 255 || defenseStat > 255) {
                attackStat /= 4
                defenseStat /= 4
            }
            // comparing item's boosting type against selected attack's type
            val doesItemBoostDamage: Boolean = if (damageBoostingItems[heldItem] != null) {
                damageBoostingItems[heldItem] == attack.type()
            } else {
                false
            }

            // Modifiers for effects from Growl, Screech, Focus Energy, etc.
            var attackModifier: Int
            var defenseModifier: Int
            var typeEffectiveness = calcEffectiveness(attack.type(),
                    defendingPokemon.types[0],
                    defendingPokemon.types[1])
            var damage = damageFormula(level, attackStat, basePower, defenseStat, critValue, doesItemBoostDamage, typeEffectiveness)
            if (sameTypeAttackBonus(attackingPokemon, attack.type())) {
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

        @JvmStatic
        fun calcHiddenPowerDamage(attackingPokemon: GSCPokemon, defendingPokemon: GSCPokemon): Int {
            val level = attackingPokemon.level
            val basePower = attackingPokemon.gscHiddenPowerBasePower
            val heldItem = attackingPokemon.heldItem
            var attackStat: Int = if (GSCMove.isPhysical(attackingPokemon.gscHiddenPowerType)) attackingPokemon.statAtk else attackingPokemon.statSpA
            var defenseStat: Int = if (GSCMove.isPhysical(attackingPokemon.gscHiddenPowerType)) defendingPokemon.statDef else defendingPokemon.statSpD
            if (attackStat > 255 || defenseStat > 255) {
                attackStat /= 4
                defenseStat /= 4
            }
            // comparing item's boosting type against selected attack's type
            val doesItemBoostDamage: Boolean = if (damageBoostingItems[heldItem] != null) {
                damageBoostingItems[heldItem] == attackingPokemon.gscHiddenPowerType
            } else {
                false
            }

            // Modifiers for effects from Growl, Screech, Focus Energy, etc.
            var attackModifier: Int
            var defenseModifier: Int
            var typeEffectiveness = calcEffectiveness(attackingPokemon.gscHiddenPowerType,
                    defendingPokemon.types[0],
                    defendingPokemon.types[1])
            var damage = damageFormula(level, attackStat, basePower, defenseStat, critModifier(), doesItemBoostDamage, typeEffectiveness)
            if (sameTypeAttackBonus(attackingPokemon, attackingPokemon.gscHiddenPowerType)) {
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

        private fun damageFormula(
                level: Int,
                attackStat: Int,
                basePower: Int,
                defenseStat: Int,
                critModifier: Int,
                doesItemBoostDamage: Boolean,
                typeEffectiveness: Double): Int {
            return ((floor(floor((floor((level * 2 / 5).toDouble()) + 2) * 1.coerceAtLeast(attackStat) * basePower / 1.coerceAtLeast(defenseStat)) / 50) * critModifier * calcItemBoost(doesItemBoostDamage) + 2) * typeEffectiveness).toInt()
        }
    }
}