package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.cantRestFullHp
import com.abrastat.general.Messages.Companion.logAttack
import com.abrastat.general.Messages.Companion.logRest
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
                // alphabetical order
                MoveEffect.ABSORB -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Mega_Drain_(move)#Generations_I_and_II
                }

                MoveEffect.COUNTER -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Counter_(move)#Generation_I
                }

                MoveEffect.CURSE -> if (attackingPokemon.types[0] == Type.GHOST || attackingPokemon.types[1] == Type.GHOST) {
                    // Do Ghost Curse stuff
                } else {
                    attackingPokemon.dropStat(Stat.SPEED)
                    attackingPokemon.raiseStat(Stat.ATTACK)
                    attackingPokemon.raiseStat(Stat.DEFENSE)
                }

                MoveEffect.DOUBLEATTACK -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Double_Kick_(move)#Generation_I
                }

                MoveEffect.FLINCH30 -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Low_Kick_(move)#Generations_I_and_II
                }

                MoveEffect.HIGHJUMPKICK -> {
                    // https://bulbapedia.bulbagarden.net/wiki/High_Jump_Kick_(move)#Generation_I
                }

                MoveEffect.MIMIC -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Mimic_(move)#Generation_I
                }

                MoveEffect.MIRRORMOVE -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Mirror_Move_(move)#Generation_I_to_VII
                }

                MoveEffect.NONE -> {
                    // EMPTY
                    // unsure how pokemon would used this
                    // probably should show warning if used
                }

                MoveEffect.OPP_ACCURACYDROP1 -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Sand_Attack_(move)#Generation_I
                    defendingPokemon.dropStat(Stat.ACCURACY)
                }

                MoveEffect.OPP_CONFUSION -> {
                    // CONFUSE RAY
                }

                MoveEffect.OPP_DEFENSEDROP2 -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Screech_(move)
                    defendingPokemon.dropStat(Stat.DEFENSE)
                    defendingPokemon.dropStat(Stat.DEFENSE)
                }

                MoveEffect.OPP_SPECIALDROP1 -> {
                    defendingPokemon.dropStat(Stat.SPECIAL)
                }

                MoveEffect.OPP_SPEEDDROP1 -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Bubble_Beam_(move)#Generation_I
                    defendingPokemon.dropStat(Stat.SPEED)
                }

                MoveEffect.PRZ -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Glare_(move)#Generation_I
                }

                MoveEffect.PRZ10 -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Reflect_(move)#Generation_I
                }

                MoveEffect.PSN40 -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Sludge_(move)#Generation_I
                }

                MoveEffect.PSYWAVE -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Psywave_(move)#Generation_I
                }

                MoveEffect.RECOIL25 -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Submission_(move)#Generation_I
                }

                MoveEffect.RECOVER -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Soft-Boiled_(move)#Generation_I
                }

                MoveEffect.REFLECT -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Reflect_(move)#Generation_I
                }

                MoveEffect.REST -> if (attackingPokemon.currentHP < attackingPokemon.statHP) {
                    logRest(attackingPokemon)
                    attackingPokemon.applyHeal(attackingPokemon.statHP)
                    attackingPokemon.applyNonVolatileStatus(Status.SLEEP)
                    attackingPokemon.setSleepCounter(2, 2)
                } else {
                    cantRestFullHp(attackingPokemon)
                }

                MoveEffect.SELF_ATTACKRAISE1 -> {
                    attackingPokemon.raiseStat(Stat.ATTACK)
                }

                MoveEffect.SELF_ATTACKRAISE2 -> {
                    attackingPokemon.raiseStat(Stat.ATTACK)
                    attackingPokemon.raiseStat(Stat.ATTACK)
                }

                MoveEffect.SELF_DEFENSERAISE2 -> {
                    attackingPokemon.raiseStat(Stat.DEFENSE)
                    attackingPokemon.raiseStat(Stat.DEFENSE)
                }

                MoveEffect.SELF_SPECIALRAISE1 -> {
                    attackingPokemon.raiseStat(Stat.SPECIAL)
                }

                MoveEffect.SELF_SPECIALRAISE2 -> {
                    attackingPokemon.raiseStat(Stat.SPECIAL)
                    attackingPokemon.raiseStat(Stat.SPECIAL)
                }

                MoveEffect.SELF_SPEEDRAISE2 -> {
                    attackingPokemon.raiseStat(Stat.SPEED)
                    attackingPokemon.raiseStat(Stat.SPEED)
                }

                MoveEffect.SLEEP -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Spore_(move)#Generation_I_to_V
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

                MoveEffect.SUBSTITUTE -> {
                    // SUBSTITUTE
                }

                MoveEffect.SUPERFANG -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Super_Fang_(move)#Effect
                }

                MoveEffect.TOXIC -> {
                    // TOXIC
                }

                MoveEffect.TRANSFORM -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Transform_(move)#Generation_I
                }

                MoveEffect.TWINNEEDLE -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Twineedle_(move)#Generation_I
                }

                MoveEffect.WRAP -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Wrap_(move)#Generation_I
                    // https://bulbapedia.bulbagarden.net/wiki/Clamp_(move)#Generation_I
                    // https://bulbapedia.bulbagarden.net/wiki/Fire_Spin_(move)#Generation_I
                    // https://bulbapedia.bulbagarden.net/wiki/Bind_(move)#Generation_I
                }

                else -> {
//                    notImplementedYet(effect.toString())
                    throw IllegalArgumentException(
                            "Move effect $effect is not implemented"
                    )
                }
            }
        }
    }
}