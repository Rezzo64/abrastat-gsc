package com.abrastat.rby

import com.abrastat.general.Game.Companion.isPokemonFainted
import com.abrastat.general.Messages.Companion.cantAttack
import com.abrastat.general.Messages.Companion.logAttack
import com.abrastat.general.Messages.Companion.logMissedAttack
import com.abrastat.general.Messages.Companion.notImplementedYet
import com.abrastat.general.Messages.Companion.statusChanged
import com.abrastat.general.MoveEffect
import com.abrastat.general.Status
import com.abrastat.rby.RBYDamage.Companion.applyDamage
import com.abrastat.rby.RBYDamage.Companion.calcAccuracy
import com.abrastat.rby.RBYDamage.Companion.calcDamage
import com.abrastat.rby.RBYMoveEffects.Companion.missEffect
import com.abrastat.rby.RBYMoveEffects.Companion.secondaryEffect
import java.util.concurrent.ThreadLocalRandom

class RBYTurn(attackingPokemon: RBYPokemon, defendingPokemon: RBYPokemon, move: RBYMove) {
    // this program applies the attack on the opponent
    init {
        // check neither Pokemon is fainted
        if (!isPokemonFainted(attackingPokemon, defendingPokemon)) {
            attackingPokemon.tookTurn = true
            checkStatusEffects(attackingPokemon)
            if (canAttack(attackingPokemon, move)) {
                logAttack(attackingPokemon, move)
                doAttack(attackingPokemon, defendingPokemon, move)
                attackingPokemon.decrementMovePp(move)
            }
        }
    }

    private fun checkStatusEffects(attackingPokemon: RBYPokemon) {
        var roll = ThreadLocalRandom.current().nextInt(256)
        for (status in attackingPokemon.volatileStatus) {
            when (status) {
                Status.CONFUSION, Status.FATIGUE -> {
                    attackingPokemon.incrementConfuseCounter()
                }
                // TODO increment counter, roll hurt self chance, check for end chance

                Status.DISABLE -> return

                else -> notImplementedYet(status)
            }
        }
    }

    private fun canAttack(attackingPokemon: RBYPokemon, move: RBYMove): Boolean {
        if (attackingPokemon.volatileStatus.contains(Status.HAZE)) {
            return false
        }

        val roll = ThreadLocalRandom.current().nextInt(256)
        return when (attackingPokemon.nonVolatileStatus) {
            Status.HEALTHY -> true

            Status.SLEEP -> if (attackingPokemon.sleepCounter > 0) {
                cantAttack(attackingPokemon, Status.SLEEP)
                attackingPokemon.decrementSleepCounter()
                false
            } else {
                statusChanged(attackingPokemon, Status.SLEEP)
                false // Can't attack on waking turn in gen 1
            }

            Status.FREEZE -> {
                cantAttack(attackingPokemon, Status.FREEZE)
                false
            }

            Status.FLINCH -> {
                cantAttack(attackingPokemon, Status.FLINCH)
                false
            }

            Status.PARALYSIS -> if (roll < 64) {
                cantAttack(attackingPokemon, Status.PARALYSIS)
                false
            } else true


            else -> true
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

        val damage = if (attackHit) calcDamage(attackingPokemon, defendingPokemon, move)
        else if (attackingPokemon.extraHit == 0) 0
        else calcDamage(attackingPokemon, defendingPokemon, move, false)

        do {
            if (attackHit) {
                if (move.isAttack) applyDamage(defendingPokemon, damage)
                secondaryEffect(attackingPokemon, defendingPokemon, move)
            } else {
                missEffect(attackingPokemon, move)
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
                val roll = ThreadLocalRandom.current().nextInt(200)
                pokemon.extraHit = when (roll) {
                    in 0 until 75 -> 1
                    in 75 until 150 -> 2
                    in 150 until 175 -> 3
                    in 175 until 200 -> 4
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
}