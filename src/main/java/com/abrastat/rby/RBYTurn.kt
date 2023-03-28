package com.abrastat.rby

import com.abrastat.general.Game.Companion.isPokemonFainted
import com.abrastat.general.Messages.Companion.cantAttack
import com.abrastat.general.Messages.Companion.logAttack
import com.abrastat.general.Messages.Companion.logDamageTaken
import com.abrastat.general.Messages.Companion.logMissedAttack
import com.abrastat.general.Messages.Companion.notImplementedYet
import com.abrastat.general.Messages.Companion.statusChanged
import com.abrastat.general.MoveEffect
import com.abrastat.general.Status
import java.util.concurrent.ThreadLocalRandom

class RBYTurn(attackingPokemon: RBYPokemon, defendingPokemon: RBYPokemon, move: RBYMove) {
    init {
        // check neither Pokemon is fainted
        if (!isPokemonFainted(attackingPokemon, defendingPokemon)) {
            checkStatusEffects(attackingPokemon)
            if (canAttack(attackingPokemon, move)) {
                logAttack(attackingPokemon, move)
                if (didAttackMiss(move.accuracy)) {
                    logMissedAttack(attackingPokemon)
                    if (move.effect == MoveEffect.HIGHJUMPKICK)
                        RBYMoveEffects.guaranteedDamage(attackingPokemon, 1)
                } else {
                    doAttack(attackingPokemon, defendingPokemon, move)
                }
                attackingPokemon.decrementMovePp(move)
            }
        }

    }

    companion object {
        private fun checkStatusEffects(attackingPokemon: RBYPokemon) {
            var roll: Int // handles RNG factor

            // some of these effects override others and need to be checked first as such
            // TODO
//        switch (attackingPokemon.getNonVolatileStatus())    {
//
//        }

            for (status in attackingPokemon.volatileStatus) {
                when (status) {
                    Status.CONFUSION, Status.FATIGUE -> {
                        roll = ThreadLocalRandom.current().nextInt(256)
                        attackingPokemon.incrementConfuseCounter()
                    }
                    // TODO increment counter, roll hurt self chance, check for end chance

                    Status.DISABLE -> roll = ThreadLocalRandom.current().nextInt(256)
                    // TODO increment counter, block some attack from being selected, check for end chance

                    else -> notImplementedYet(status)
                }
            }
        }

        private fun canAttack(attackingPokemon: RBYPokemon, move: RBYMove): Boolean {
            if (attackingPokemon.nonVolatileStatus === Status.HEALTHY) {
                return true
            }
            val roll = ThreadLocalRandom.current().nextInt(256) // handles RNG factor
            return when (attackingPokemon.nonVolatileStatus) {
                Status.SLEEP -> if (attackingPokemon.sleepCounter > 1) {
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
                } else {
                    true
                }

                else -> true
            }
        }

        private fun didAttackMiss(accuracy: Int): Boolean {
            val roll = ThreadLocalRandom.current().nextInt(256)
            return accuracy < roll
        }

        @JvmStatic
        fun doAttack(attackingPokemon: RBYPokemon, defendingPokemon: RBYPokemon, move: RBYMove) {
            if (move.isAttack) calcAndApplyDamage(attackingPokemon, defendingPokemon, move)
            RBYMoveEffects.secondaryEffect(attackingPokemon, defendingPokemon, move)
        }

        private fun calcAndApplyDamage(attackingPokemon: RBYPokemon, defendingPokemon: RBYPokemon, move: RBYMove) {
            val damage = RBYDamageCalc.calcDamage(attackingPokemon, defendingPokemon, move)
            defendingPokemon.applyDamage(damage)
            defendingPokemon.lastDamageTaken = damage
            logDamageTaken(defendingPokemon, damage)
        }
    }
}