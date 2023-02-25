package com.abrastat.rby

import com.abrastat.general.Messages.Companion.cantRestFullHp
import com.abrastat.general.Messages.Companion.logAttack
import com.abrastat.general.Messages.Companion.logDamageTaken
import com.abrastat.general.Messages.Companion.logRest
import com.abrastat.general.Messages.Companion.notImplementedYet
import com.abrastat.general.MoveEffect
import com.abrastat.general.Stat
import com.abrastat.general.Status
import com.abrastat.general.Type
import com.abrastat.rby.RBYDamageCalc.Companion.calcHiddenPowerDamage
import com.abrastat.rby.RBYTurn.Companion.doAttack
import java.util.concurrent.ThreadLocalRandom

enum class RBYStatusMovesEffects {
    INSTANCE;

    companion object {
        fun applyStatusEffects(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                effect: MoveEffect) {
            when (effect) {
                MoveEffect.HIDDENPOWER -> {
                    val damage = calcHiddenPowerDamage(attackingPokemon, defendingPokemon)
                    defendingPokemon.applyDamage(damage)
                    defendingPokemon.lastDamageTaken = damage
                    logDamageTaken(defendingPokemon, damage)
                }

                MoveEffect.REST -> if (attackingPokemon.currentHP < attackingPokemon.statHP) {
                    logRest(attackingPokemon)
                    attackingPokemon.applyHeal(attackingPokemon.statHP)
                    attackingPokemon.applyNonVolatileStatus(Status.SLEEP)
                    attackingPokemon.setSleepCounter(2, 2)
                } else {
                    cantRestFullHp(attackingPokemon)
                }

                MoveEffect.SLEEPTALK -> {
                    require(attackingPokemon.moves[3] == RBYMove.SLEEP_TALK) {
                        "Sleep Talk called, but not found in " +
                                attackingPokemon + "'s moveset."
                    }
                    val randomMove = ThreadLocalRandom.current().nextInt(0, 3)
                    logAttack(attackingPokemon, attackingPokemon.moves[randomMove])
                    doAttack(attackingPokemon,
                            defendingPokemon,
                            attackingPokemon.moves[randomMove]
                    )
                }

                MoveEffect.CURSE -> if (attackingPokemon.types[0] == Type.GHOST || attackingPokemon.types[1] == Type.GHOST) {
                    // Do Ghost Curse stuff
                } else {
                    attackingPokemon.dropStat(Stat.SPEED)
                    attackingPokemon.raiseStat(Stat.ATTACK)
                    attackingPokemon.raiseStat(Stat.DEFENSE)
                }

                else -> {
                    notImplementedYet(effect.toString())
                }
            }
        }
    }
}