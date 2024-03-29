package com.abrastat.gsc

import com.abrastat.general.Game.Companion.isPokemonFainted
import com.abrastat.general.Messages.Companion.cantAttack
import com.abrastat.general.Messages.Companion.logAttack
import com.abrastat.general.Messages.Companion.logDamageTaken
import com.abrastat.general.Messages.Companion.logMissedAttack
import com.abrastat.general.Messages.Companion.logNewStatus
import com.abrastat.general.Messages.Companion.logRecoil
import com.abrastat.general.Messages.Companion.notImplementedYet
import com.abrastat.general.Messages.Companion.statusChanged
import com.abrastat.general.Move
import com.abrastat.general.MoveEffect
import com.abrastat.general.Status
import java.util.concurrent.ThreadLocalRandom

class GSCTurn(attackingPokemon: GSCPokemon, defendingPokemon: GSCPokemon, move: GSCMove) {
    init {

        // check neither Pokemon is fainted
        if (!isPokemonFainted(attackingPokemon, defendingPokemon)) {
            checkStatusEffects(attackingPokemon)
            if (canAttack(attackingPokemon, move)) {
                logAttack(attackingPokemon, move)
                if (didAttackMiss(move.accuracy)) {
                    logMissedAttack(attackingPokemon)
                } else {
                    doAttack(attackingPokemon, defendingPokemon, move)
                }
                attackingPokemon.decrementMovePp(move)
            }
        }

    }

    companion object {
        private fun checkStatusEffects(attackingPokemon: GSCPokemon) {
            var roll: Int // handles RNG factor

            // some of these effects override others and need to be checked first as such
            // TODO
//        switch (attackingPokemon.getNonVolatileStatus())    {
//
//        }

            for (status in attackingPokemon.volatileStatus) {
                when (status) {
                    Status.ATTRACT -> roll = ThreadLocalRandom.current().nextInt(256)
                    // TODO roll move fail chance

                    Status.CONFUSION -> {
                        roll = ThreadLocalRandom.current().nextInt(256)
                        attackingPokemon.confuseCounter--
                    }
                    // TODO increment counter, roll hurt self chance, check for end chance

                    Status.ENCORE -> {
                        roll = ThreadLocalRandom.current().nextInt(256)
                        attackingPokemon.incrementEncoreCounter()
                    }
                    // TODO increment counter, override attack to instead be previously used attack, check for end chance

                    Status.LOCKON -> roll = ThreadLocalRandom.current().nextInt(256)
                    // TODO remove accuracy checks (for this turn only), remove LOCKON status

                    Status.DISABLE -> roll = ThreadLocalRandom.current().nextInt(256)
                    // TODO increment counter, block some attack from being selected, check for end chance

                    else -> {
                        //TODO other effects
                        notImplementedYet(status)
                    }
                }
            }
        }

        private fun canAttack(attackingPokemon: GSCPokemon, move: GSCMove): Boolean {
            if (attackingPokemon.nonVolatileStatus === Status.HEALTHY) {
                return true
            }
            val roll = ThreadLocalRandom.current().nextInt(256) // handles RNG factor
            return when (attackingPokemon.nonVolatileStatus) {
                Status.SLEEP -> if (attackingPokemon.sleepCounter > 0) {
                    cantAttack(attackingPokemon, Status.SLEEP)
                    attackingPokemon.sleepCounter--
                    move == GSCMove.SLEEP_TALK
                } else {
                    statusChanged(attackingPokemon, Status.SLEEP)
                    true
                }

                Status.FREEZE -> {
                    // if (move != GSCMove.FLAME_WHEEL)
                    cantAttack(attackingPokemon, Status.FREEZE)
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
        fun doAttack(attackingPokemon: GSCPokemon, defendingPokemon: GSCPokemon, move: GSCMove) {
            if (move.isAttack) {
                calcAndApplyDamage(attackingPokemon, defendingPokemon, move)
                rollSecondaryEffects(attackingPokemon, defendingPokemon, move) // for stuff like Counter as well as debugging
            } else {
                GSCStatusMovesEffects.applyStatusEffects(attackingPokemon, defendingPokemon, move.effect)
            }
        }

        private fun calcAndApplyDamage(attackingPokemon: GSCPokemon, defendingPokemon: GSCPokemon, move: GSCMove) {
            val damage = GSCDamageCalc.calcDamage(attackingPokemon, defendingPokemon, move)
            defendingPokemon.applyDamage(damage)
            defendingPokemon.lastDamageTaken = damage
            logDamageTaken(defendingPokemon, damage)
        }

        private fun rollSecondaryEffects(self: GSCPokemon, opponent: GSCPokemon, move: Move) {
            if (move.effect.target() === MoveEffect.Target.NONE) {
                return
            }

            // Guaranteed effects go here!
            if (move.effect.chance() == 0) {
                when (move.effect) {
                    MoveEffect.RECOIL25, MoveEffect.STRUGGLE -> {
                        val recoil = opponent.lastDamageTaken / 4
                        self.applyDamage(recoil)
                        logRecoil(self, recoil)
                        return
                    }

                    else -> { return }
                }
            }
            val roll = ThreadLocalRandom.current().nextInt(256)

            // Chance probability attacks go here!
            if (roll < move.effect.chance()) {
                when (move.effect) {
                    MoveEffect.THUNDER, MoveEffect.PRZ100, MoveEffect.PRZ30, MoveEffect.PRZ10, MoveEffect.PRZ -> {
                        if (opponent.nonVolatileStatus !== Status.HEALTHY) {
                            return
                        }
                        opponent.applyNonVolatileStatus(Status.PARALYSIS)
                        logNewStatus(opponent, Status.PARALYSIS)
                    }

                    MoveEffect.FRZ10 -> {
                        if (opponent.nonVolatileStatus !== Status.HEALTHY) {
                            return
                        }
                        opponent.applyNonVolatileStatus(Status.FREEZE)
                        logNewStatus(opponent, Status.FREEZE)
                    }

                    MoveEffect.BRN10, MoveEffect.SACREDFIRE, MoveEffect.FLAMEWHEEL -> {
                        if (opponent.nonVolatileStatus !== Status.HEALTHY) {
                            return
                        }
                        opponent.applyNonVolatileStatus(Status.BURN)
                        logNewStatus(opponent, Status.BURN)
                        notImplementedYet(move.effect)
                    }

                    else -> notImplementedYet(move.effect)
                }
            }
        }
    }
}