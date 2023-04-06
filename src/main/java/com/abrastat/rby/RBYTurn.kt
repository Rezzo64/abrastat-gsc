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
import com.abrastat.rby.RBYMoveEffects.Companion.opponentEffect
import com.abrastat.rby.RBYMoveEffects.Companion.secondaryEffect
import java.util.concurrent.ThreadLocalRandom

class RBYTurn(attackingPokemon: RBYPokemon, defendingPokemon: RBYPokemon, move: RBYMove) {
    // this program applies the attacks
    // move effects on self and opponent
    private var canAttack = true

    init {
        // check neither Pokemon is fainted
        if (!isPokemonFainted(attackingPokemon, defendingPokemon)) {
            attackingPokemon.tookTurn = true
            checkStatusEffectsBefore(attackingPokemon, defendingPokemon, move)
            if (canAttack) {
                logAttack(attackingPokemon, move)
                doAttack(attackingPokemon, defendingPokemon, move)
            }
            checkStatusEffectsAfter(attackingPokemon, defendingPokemon)
        }
    }

    // sets canAttack and other effects
    private fun checkStatusEffectsBefore(attackingPokemon: RBYPokemon,
                                         defendingPokemon: RBYPokemon,
                                         move: RBYMove) {
        if (move == RBYMove.EMPTY) canAttack = false
        val ignoreInvulnerable = listOf(RBYMove.BIDE, RBYMove.SWIFT, RBYMove.TRANSFORM)
        if (defendingPokemon.volatileStatus.contains(Status.INVULNERABLE)
                && !(ignoreInvulnerable.contains(move)))
            canAttack = false

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
                    val damage = calcDamage(attackingPokemon, attackingPokemon,
                            RBYMove.CONFUSE_DAMAGE, false)
                    if (attackingPokemon.substituteHP != 0
                            || defendingPokemon.substituteHP != 0) {
                        if (defendingPokemon.substituteHP != 0)
                            applyDamage(defendingPokemon, damage, true)
                    } else applyDamage(attackingPokemon, damage, true)
                    cantAttack(attackingPokemon, Status.CONFUSION)
                    logEffect(attackingPokemon, Status.CONFUSION)
                    canAttack = false

                    // confusion interrupt
                    val m = attackingPokemon.multiTurn.first
                    if (m == RBYMove.BIDE)
                        attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
                    // fly/dig bug doesn't reset invulnerable status
                    if (m.effect == MoveEffect.INVULNERABLE
                            || m.effect == MoveEffect.PETALDANCE
                            || m.effect == MoveEffect.RAZORWIND)
                        attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
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
                    attackingPokemon.removeVolatileStatus(Status.FLINCH)
                    cantAttack(attackingPokemon, Status.FLINCH)
                    canAttack = false
                }

                Status.HAZE -> {
                    // was frozen or sleeping
                    attackingPokemon.removeVolatileStatus(Status.HAZE)
                    cantAttack(attackingPokemon, Status.HAZE)
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

                    // paralysis interrupt
                    val m = attackingPokemon.multiTurn.first
                    if (m == RBYMove.BIDE)
                        attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
                    // fly/dig bug doesn't reset invulnerable status
                    if (m.effect == MoveEffect.INVULNERABLE
                            || m.effect == MoveEffect.PETALDANCE
                            || m.effect == MoveEffect.RAZORWIND)
                        attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
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
        // multi hit moves flag
        multihit(move.effect, attackingPokemon)

        // accuracy and damage
        val accuracy = calcAccuracy(attackingPokemon, defendingPokemon, move.accuracy)
        var attackHit = if ((move.effect == MoveEffect.INVULNERABLE
                || move.effect == MoveEffect.RAZORWIND)
                && attackingPokemon.multiTurn.first == RBYMove.EMPTY) true  // set up turn
        else attackHit(accuracy)
        val damage = getDamage(attackingPokemon, defendingPokemon, move, attackHit)

        // decrease pp on first turn of move except some moves
        // pp also only decreases if pokemon could attack
        // doesn't actually work this way, but likely insignificant
        // for 1v1 battles
        if (attackingPokemon.multiTurn.first == RBYMove.EMPTY
                && move.effect != MoveEffect.BIND)
            attackingPokemon.decrementMovePp(move)

        // opponent effects flag
        defendingPokemon.lastDamageTaken = 0

        // apply damage
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

        opponentEffect(defendingPokemon)
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
                    6 -> 3
                    7 -> 4
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

    // doesn't call calcDamage if not necessary
    private fun getDamage(attackingPokemon: RBYPokemon,
                          defendingPokemon: RBYPokemon,
                          move: RBYMove,
                          attackHit: Boolean): Int {
        // damaging effects handled separately
        return if (!move.isAttack) -1
        // pre calculated
        else if (attackingPokemon.storedDamage != 0) attackingPokemon.storedDamage
        else if (attackingPokemon.extraHit != 0         // multiple hits in one turn
                || move.effect == MoveEffect.BIND) {    // one hit over multiple turns
            val d = if (attackHit) calcDamage(attackingPokemon, defendingPokemon, move)
            else calcDamage(attackingPokemon, defendingPokemon, move, false)
            attackingPokemon.storedDamage = d
            d
        }
        else if (move.effect == MoveEffect.DREAMEATER) {
            if (defendingPokemon.nonVolatileStatus == Status.SLEEP)
                calcDamage(attackingPokemon, defendingPokemon, move)
            else -1
        }
        else if (move.effect == MoveEffect.INVULNERABLE) {
            // goes into the ground or sky
            if (!attackingPokemon.volatileStatus.contains(Status.INVULNERABLE)) -1
            // dig/fly glitch
            else if (attackHit) calcDamage(attackingPokemon, defendingPokemon, move)
            else 0
        }
        else if (move.effect == MoveEffect.RAZORWIND) {
            // prepare
            if (attackingPokemon.multiTurn.first == RBYMove.EMPTY) -1
            else if (attackHit) calcDamage(attackingPokemon, defendingPokemon, move)
            else 0
        }
        else if (move.effect == MoveEffect.SWIFT) -1    // bypass invulnerability
        else if (attackHit) calcDamage(attackingPokemon, defendingPokemon, move)
        else 0  // don't bother calculating
    }

    private fun checkStatusEffectsAfter(attackingPokemon: RBYPokemon,
                                        defendingPokemon: RBYPokemon) {
        val statuses = listOf(Status.BURN, Status.POISON, Status.TOXIC)
        if (statuses.contains(attackingPokemon.nonVolatileStatus))
            toxicDamage(attackingPokemon)
        when (attackingPokemon.nonVolatileStatus) {
            Status.BURN -> logEffect(attackingPokemon, Status.BURN)
            Status.POISON -> logEffect(attackingPokemon, Status.POISON)
            Status.TOXIC -> logEffect(attackingPokemon, Status.TOXIC)
            else -> {}
        }

        // toxicCounter = toxic 1, leech 2, toxic 3, leech 4, ...
        if (attackingPokemon.volatileStatus.contains(Status.LEECHSEED)) {
            val damage = toxicDamage(attackingPokemon)
            defendingPokemon.applyHeal(damage)
            logEffect(attackingPokemon, Status.LEECHSEED)
            logHeal(defendingPokemon)
        }
    }
}