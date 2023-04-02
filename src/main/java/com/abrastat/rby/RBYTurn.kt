package com.abrastat.rby

import com.abrastat.general.Game.Companion.isPokemonFainted
import com.abrastat.general.Messages.Companion.cantAttack
import com.abrastat.general.Messages.Companion.logAttack
import com.abrastat.general.Messages.Companion.logEffect
import com.abrastat.general.Messages.Companion.logHeal
import com.abrastat.general.Messages.Companion.logMissedAttack
import com.abrastat.general.Messages.Companion.statusChanged
import com.abrastat.general.MoveEffect
import com.abrastat.general.Status
import com.abrastat.rby.RBYDamage.Companion.applyDamage
import com.abrastat.rby.RBYDamage.Companion.calcAccuracy
import com.abrastat.rby.RBYDamage.Companion.calcDamage
import com.abrastat.rby.RBYDamage.Companion.toxicDamage
import com.abrastat.rby.RBYMoveEffects.Companion.missEffect
import com.abrastat.rby.RBYMoveEffects.Companion.secondaryEffect
import java.util.concurrent.ThreadLocalRandom

class RBYTurn(attackingPokemon: RBYPokemon, defendingPokemon: RBYPokemon, move: RBYMove) {
    // this program applies the attack on the opponent
    private var canAttack = true

    init {
        // check neither Pokemon is fainted
        if (!isPokemonFainted(attackingPokemon, defendingPokemon)) {
            attackingPokemon.tookTurn = true
            checkStatusEffectsBefore(attackingPokemon, defendingPokemon)
            if (canAttack) {
                if (attackingPokemon.multiTurn.first != RBYMove.EMPTY)
                    attackingPokemon.decrementMovePp(move)
                logAttack(attackingPokemon, move)
                doAttack(attackingPokemon, defendingPokemon, move)
            }
            checkStatusEffectsAfter(attackingPokemon, defendingPokemon)
        }
    }

    private fun checkStatusEffectsBefore(attackingPokemon: RBYPokemon,
                                         defendingPokemon: RBYPokemon) {
        // check confusion before hyper beam or flinch
        if (attackingPokemon.volatileStatus.contains(Status.CONFUSION)) {
            // https://bulbapedia.bulbagarden.net/wiki/Confusion_(status_condition)#Effect
            if (attackingPokemon.confuseCounter > 0) {
                attackingPokemon.confuseCounter--

                val statuses = listOf(Status.FLINCH, Status.HAZE, Status.HYPERBEAM)
                if (attackingPokemon.volatileStatus.any { it in statuses }
                        || attackingPokemon.nonVolatileStatus == Status.FREEZE
                        || attackingPokemon.nonVolatileStatus == Status.SLEEP)
                    attackingPokemon.confuseCounter++

                if (ThreadLocalRandom.current().nextBoolean()) {
                    // TODO substitute
                    //  https://www.smogon.com/rb/articles/rby_mechanics_guide
                    //  Confusion
                    val damage = calcDamage(attackingPokemon, defendingPokemon,
                            RBYMove.CONFUSE_DAMAGE, false)
                    applyDamage(attackingPokemon, damage)
                    cantAttack(attackingPokemon, Status.CONFUSION)
                    logEffect(attackingPokemon, Status.CONFUSION)
                    canAttack = false
                }
            } else {
                attackingPokemon.resetConfuseCounter()
                statusChanged(attackingPokemon, Status.CONFUSION)
            }
        }

        for (status in attackingPokemon.volatileStatus) {
            when (status) {
                Status.BIND -> {
                    if (defendingPokemon.multiTurn.first != RBYMove.EMPTY) {
                        cantAttack(attackingPokemon, status)
                        canAttack = false
                    } else {
                        attackingPokemon.removeVolatileStatus(status)
                        if (defendingPokemon.tookTurn) {
                            cantAttack(attackingPokemon, status)
                            canAttack = false
                        }
                    }
                }

                Status.FLINCH -> {
                    cantAttack(attackingPokemon, Status.FLINCH)
                    canAttack = false
                }

                Status.HAZE -> {
                    // was frozen or sleeping
                    canAttack = false
                }

                Status.HYPERBEAM -> {
                    if (attackingPokemon.nonVolatileStatus != Status.FREEZE) {
                        attackingPokemon.removeVolatileStatus(status)
                        cantAttack(attackingPokemon, status)
                    }
                    canAttack = false
                }

                else -> {}
            }
        }

        val roll = ThreadLocalRandom.current().nextInt(256)
        when (attackingPokemon.nonVolatileStatus) {
            Status.FREEZE -> {
                cantAttack(attackingPokemon, Status.FREEZE)
                canAttack = false
            }

            Status.PARALYSIS -> {
                if (roll < 64) {
                    cantAttack(attackingPokemon, Status.PARALYSIS)
                    canAttack = false
                }
            }

            Status.SLEEP -> {
                canAttack = false
                if (attackingPokemon.sleepCounter > 0) {
                    cantAttack(attackingPokemon, Status.SLEEP)
                    attackingPokemon.sleepCounter--
                } else {
                    // Can't attack on waking turn in gen 1
                    statusChanged(attackingPokemon, Status.SLEEP)
                }
            }

            else -> {}
        }
    }

    private fun doAttack(attackingPokemon: RBYPokemon,
                         defendingPokemon: RBYPokemon,
                         move: RBYMove) {
        // multi hit moves
        // https://bulbapedia.bulbagarden.net/wiki/Double_Kick_(move)#Generation_I
        // https://bulbapedia.bulbagarden.net/wiki/Multi-strike_move#Generation_I
        multihit(move.effect, attackingPokemon)

        val accuracy = calcAccuracy(attackingPokemon, defendingPokemon, move.accuracy)
        var attackHit = attackHit(accuracy)

        if (attackHit && defendingPokemon.nonVolatileStatus == Status.FREEZE
                && (move.effect == MoveEffect.BRN10 || move.effect == MoveEffect.BRN30)) {
            defendingPokemon.removeNonVolatileStatus()
            statusChanged(defendingPokemon, Status.BURN)
        }

        val damage = if (attackingPokemon.multiTurn.first != RBYMove.EMPTY) attackingPokemon.storedDamage
        else if (attackHit) calcDamage(attackingPokemon, defendingPokemon, move)
        else if (attackingPokemon.extraHit == 0) 0  // don't bother calculating
        else calcDamage(attackingPokemon, defendingPokemon, move, false)

        // TODO store fly, dig, razor wind dmg, set dmg to Int.MIN_VALUE

        do {
            if (attackHit) {
                if (move.isAttack) applyDamage(defendingPokemon, damage)
                secondaryEffect(attackingPokemon, defendingPokemon, move)
            } else {
                missEffect(attackingPokemon, defendingPokemon, move)
                logMissedAttack(attackingPokemon)
            }
            if (attackingPokemon.extraHit > 0) attackHit = attackHit(accuracy)
        } while (attackingPokemon.extraHit > 0)
    }

    private fun multihit(effect: MoveEffect, pokemon: RBYPokemon) {
        when (effect) {
            MoveEffect.DOUBLEATTACK,
            MoveEffect.TWINNEEDLE,
            -> pokemon.extraHit = 1

            MoveEffect.MULTIHIT -> {
                val roll = ThreadLocalRandom.current().nextInt(8)
                pokemon.extraHit = when (roll) {
                    in 0 until 3 -> 1
                    in 3 until 6 -> 2
                    in 6 until 7 -> 3
                    in 7 until 8 -> 4
                    else -> 0
                }
            }

            else -> {}
        }
    }

    private fun attackHit(accuracy: Int): Boolean {
        if (accuracy == Int.MAX_VALUE) return true

        val roll = ThreadLocalRandom.current().nextInt(256)
        return roll < accuracy  // miss if 255
    }

    private fun checkStatusEffectsAfter(attackingPokemon: RBYPokemon,
                                        defendingPokemon: RBYPokemon) {
        if (attackingPokemon.volatileStatus.contains(Status.LEECHSEED)) {
            val damage = toxicDamage(attackingPokemon)
            defendingPokemon.applyHeal(damage)
            logEffect(attackingPokemon, Status.LEECHSEED)
            logHeal(defendingPokemon)
            // does toxic go first or leech seed
            // if later, more heal
        }

        if (defendingPokemon.currentHP != 0) {
            val statuses = listOf(Status.BURN, Status.POISON, Status.TOXIC)
            if (statuses.contains(attackingPokemon.nonVolatileStatus))
                toxicDamage(attackingPokemon)
            when (attackingPokemon.nonVolatileStatus) {
                Status.BURN -> logEffect(attackingPokemon, Status.BURN)
                Status.POISON -> logEffect(attackingPokemon, Status.POISON)
                Status.TOXIC -> logEffect(attackingPokemon, Status.TOXIC)
                else -> {}
            }
        }
    }
}