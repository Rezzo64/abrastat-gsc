package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.cantRestFullHp
import com.abrastat.general.Messages.Companion.logHeal
import com.abrastat.general.Messages.Companion.logNewStatus
import com.abrastat.general.Messages.Companion.logRest
import com.abrastat.rby.RBYDamage.Companion.applyDamage
import com.abrastat.rby.RBYDamage.Companion.calcDamage
import com.abrastat.rby.RBYTypeEffectiveness.calcEffectiveness
import java.util.concurrent.ThreadLocalRandom

class RBYMoveEffects {
    // this program implements
    // move effects of all moves

    // move effects not implemented:
    // conversion, disable, metronome, mimic, mirror move, transform
    companion object {
        fun secondaryEffect(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                move: RBYMove) {
            val effect = move.effect


            // none
            // only damaging moves
            if (effect == MoveEffect.NONE) return
            if (effect.target() === MoveEffect.Target.NONE) return
            
            // guaranteed
            if (effect.chance() == 0)
                guaranteedEffect(attackingPokemon, defendingPokemon, move)

            // unfreeze, move doesn't have to actually burn
            if (defendingPokemon.nonVolatileStatus == Status.FREEZE
                    && (effect == MoveEffect.BRN10 || effect == MoveEffect.BRN30)) {
                defendingPokemon.removeNonVolatileStatus()
                Messages.statusChanged(defendingPokemon, Status.BURN)
            }

            // chance
            val roll = ThreadLocalRandom.current().nextInt(256)
            if (roll < effect.chance())
                chanceEffect(defendingPokemon, move)
        }

        fun missEffect(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                move: RBYMove) {
            when (move.effect) {
                MoveEffect.BIND -> {
                    if (defendingPokemon.volatileStatus.contains(Status.HYPERBEAM))
                        defendingPokemon.removeVolatileStatus(Status.HYPERBEAM)
                    if (!defendingPokemon.volatileStatus.contains(Status.BIND))
                        attackingPokemon.decrementMovePp(move)
                }

                MoveEffect.DOUBLEATTACK,
                MoveEffect.MULTIHIT,
                MoveEffect.TWINNEEDLE,
                -> lastHit(attackingPokemon, defendingPokemon.substituteBreak)

                // HIGH_JUMP_KICK, JUMP_KICK
                MoveEffect.HIGHJUMPKICK -> applyDamage(attackingPokemon, 1)

                MoveEffect.RAGE -> {
                    if (!attackingPokemon.volatileStatus.contains(Status.RAGE)
                            && (attackingPokemon.accMod - defendingPokemon.evaMod) != 0)
                        attackingPokemon.rageHit = false
                }

                else -> return
            }
        }

        fun opponentEffect(pokemon: RBYPokemon) {
            if (pokemon.lastDamageTaken != 0) {
                if (pokemon.multiTurn.first == RBYMove.BIDE)
                    pokemon.storedDamage += pokemon.lastDamageTaken

                if (pokemon.counter && pokemon.counterable)
                    pokemon.counterDamage = pokemon.lastDamageTaken

                // TODO disable
                if (pokemon.volatileStatus.contains(Status.RAGE))
                    pokemon.raiseStat(Stat.ATTACK)
            }
        }

        private fun guaranteedEffect(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                move: RBYMove) {
            when (move.effect) {
                // alphabetical
                // ABSORB, LEECH_LIFE, MEGA_DRAIN
                MoveEffect.ABSORB -> {
                    if (defendingPokemon.substituteBreak) return
                    val absorb = defendingPokemon.lastDamageTaken / 2
                    attackingPokemon.applyHeal(absorb)
                    logHeal(attackingPokemon)
                }

                MoveEffect.BIDE -> {
                    // https://www.smogon.com/rb/articles/rby_mechanics_guide#bide
                    if (attackingPokemon.multiTurn.first != RBYMove.BIDE) {
                        val roll = ThreadLocalRandom.current().nextBoolean()
                        attackingPokemon.multiTurn = if (roll) Pair(move, 2)
                        else Pair(move, 3)
                    } else {
                        var turn = attackingPokemon.multiTurn.second
                        if (turn > 0) {
                            turn -= 1
                            attackingPokemon.multiTurn = Pair(move, turn)
                        } else {
                            applyDamage(defendingPokemon, attackingPokemon.storedDamage, true)
                            attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
                            attackingPokemon.storedDamage = 0
                        }
                    }
                }

                // BIND, CLAMP, FIRE_SPIN, WRAP
                MoveEffect.BIND -> {
                    // https://www.smogon.com/rb/articles/differences
                    // Partial-Trapping Moves
                    // TODO when attackingPokemon switches out, reset multiTurn and storedDamage
                    // TODO when defendingPokemon switches out before attack,
                    //  reset multiTurn but auto use move and storedDamage
                    if (!defendingPokemon.volatileStatus.contains(Status.BIND)) {
                        val turns = when (ThreadLocalRandom.current().nextInt(8)) {
                            in 0 until 3 -> 1
                            in 3 until 6 -> 2
                            6 -> 3
                            7 -> 4
                            else -> 0
                        }
                        attackingPokemon.multiTurn = Pair(move, turns)
                        attackingPokemon.storedDamage = defendingPokemon.lastDamageTaken
                        applyStatus(defendingPokemon, Status.BIND)
                        logNewStatus(defendingPokemon, Status.BIND)
                        attackingPokemon.decrementMovePp(move)

                        if (defendingPokemon.volatileStatus.contains(Status.HYPERBEAM))
                            defendingPokemon.removeVolatileStatus(Status.HYPERBEAM)
                    } else {
                        var turns = attackingPokemon.multiTurn.second
                        if (turns > 1) {
                            turns -= 1
                            attackingPokemon.multiTurn = Pair(move, turns)
                        } else {
                            attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
                            attackingPokemon.storedDamage = 0
                        }
                    }
                }

                // CONFUSE_RAY, SUPERSONIC
                MoveEffect.CONFUSION -> {
                    if (defendingPokemon.substituteHP != 0) return
                    applyStatus(defendingPokemon, Status.CONFUSION)
                    defendingPokemon.confuseCounter =
                            ThreadLocalRandom.current().nextInt(1, 5)
                }

                MoveEffect.CONVERSION -> {
                    TODO()
                }

                MoveEffect.COUNTER -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Counter_(move)#Generation_I
                    // TODO metronome
                    // is this supposed to work if move missed
                    if (attackingPokemon.counterable
                            && attackingPokemon.counterDamage > 0) {
                        applyDamage(defendingPokemon, attackingPokemon.counterDamage * 2)
                        attackingPokemon.counterDamage = 0
                    }
                }

                // implemented in RBYDamage
                MoveEffect.CRITRATE -> return

                MoveEffect.DISABLE -> {
                    TODO()
                }

                // BARRAGE, BONEMERANG, COMET_PUNCH, DOUBLE_KICK, DOUBLESLAP,
                // FURY_ATTACK, FURY_SWIPES, PIN_MISSILE, SPIKE_CANNON
                MoveEffect.DOUBLEATTACK,
                MoveEffect.MULTIHIT,
                -> lastHit(attackingPokemon, defendingPokemon.substituteBreak)

                MoveEffect.DRAGONRAGE -> applyDamage(defendingPokemon, 40)

                MoveEffect.DREAMEATER -> {
                    if (defendingPokemon.substituteBreak) return
                    if (defendingPokemon.nonVolatileStatus != Status.SLEEP) return
                    var heal = defendingPokemon.lastDamageTaken
                    if (heal != 0) heal = (heal / 2).coerceAtLeast(1)
                    attackingPokemon.applyHeal(heal)
                }

                MoveEffect.FOCUSENERGY -> applyStatus(attackingPokemon, Status.FOCUSENERGY)

                MoveEffect.HAZE -> {
                    val cannotAttack = (defendingPokemon.nonVolatileStatus == Status.FREEZE
                            || defendingPokemon.nonVolatileStatus == Status.SLEEP)

                    resetPokemon(attackingPokemon)
                    resetPokemon(defendingPokemon)
                    if (attackingPokemon.nonVolatileStatus == Status.TOXIC)
                        attackingPokemon.applyNonVolatileStatus(Status.POISON)
                    defendingPokemon.removeNonVolatileStatus()

                    if (cannotAttack && !defendingPokemon.tookTurn)
                        // defending pokemon removed sleep or freeze in same turn
                        defendingPokemon.applyVolatileStatus(Status.HAZE)
                }

                // implemented in miss effects
                MoveEffect.HIGHJUMPKICK -> return

                MoveEffect.HYPERBEAM -> {
                    if (defendingPokemon.substituteBreak) return
                    if (defendingPokemon.currentHP != 0)
                        applyStatus(attackingPokemon, Status.HYPERBEAM)
                }

                // DIG, FLY
                MoveEffect.INVULNERABLE -> {
                    // https://www.smogon.com/rb/articles/differences
                    // invulnerable bug
                    if (!attackingPokemon.volatileStatus.contains(Status.INVULNERABLE)) {
                        attackingPokemon.applyVolatileStatus(Status.INVULNERABLE)
                        attackingPokemon.multiTurn = Pair(move, 1)
                    } else {
                        attackingPokemon.removeVolatileStatus(Status.INVULNERABLE)
                        attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
                    }
                }

                MoveEffect.LEECHSEED -> {
                    if (!defendingPokemon.types.contains(Type.GRASS))
                        applyStatus(defendingPokemon, Status.LEECHSEED)
                }

                // implemented in RBYDamage
                MoveEffect.LIGHTSCREEN -> applyStatus(attackingPokemon, Status.LIGHTSCREEN)

                MoveEffect.METRONOME -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Metronome_(move)
                    TODO()
                }

                MoveEffect.MIMIC -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Mimic_(move)#Generation_I
                    TODO()
                }

                MoveEffect.MIRRORMOVE -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Mirror_Move_(move)#Generation_I_to_VII
                    TODO()
                }

                MoveEffect.MIST -> applyStatus(attackingPokemon, Status.MIST)

                // FISSURE, GUILLOTINE, HORN_DRILL
                MoveEffect.ONEHITKO -> {
                    val typeEffectiveness = calcEffectiveness(move.type,
                            defendingPokemon.types[0],
                            defendingPokemon.types[1])
                    if (typeEffectiveness != 0.0
                            && attackingPokemon.statSpe > defendingPokemon.statSpe)
                        applyDamage(defendingPokemon, 65535)
                }

                // FLASH, KINESIS, SAND_ATTACK, SMOKESCREEN
                MoveEffect.OPP_ACCURACYDROP1 -> {
                    if (defendingPokemon.volatileStatus.contains(Status.MIST)
                            || defendingPokemon.substituteHP != 0) return
                    statGlitch(defendingPokemon)
                    defendingPokemon.dropStat(Stat.ACCURACY)
                }

                // GROWL
                MoveEffect.OPP_ATTACKDROP1 -> {
                    if (defendingPokemon.volatileStatus.contains(Status.MIST)
                            || defendingPokemon.substituteHP != 0) return
                    statGlitch(defendingPokemon)
                    defendingPokemon.dropStat(Stat.ATTACK)
                }

                // LEER, TAIL_WHIP
                MoveEffect.OPP_DEFENSEDROP1 -> {
                    if (defendingPokemon.volatileStatus.contains(Status.MIST)
                            || defendingPokemon.substituteHP != 0) return
                    statGlitch(defendingPokemon)
                    defendingPokemon.dropStat(Stat.DEFENSE)
                }

                // SCREECH
                MoveEffect.OPP_DEFENSEDROP2 -> {
                    if (defendingPokemon.volatileStatus.contains(Status.MIST)
                            || defendingPokemon.substituteHP != 0) return
                    statGlitch(defendingPokemon)
                    defendingPokemon.dropStatSharp(Stat.DEFENSE)
                }

                // STRING_SHOT
                MoveEffect.OPP_SPEEDDROP1 -> {
                    if (defendingPokemon.volatileStatus.contains(Status.MIST)
                            || defendingPokemon.substituteHP != 0) return
                    statGlitch(defendingPokemon)
                    defendingPokemon.dropStat(Stat.SPEED)
                }

                // PETAL_DANCE, THRASH
                MoveEffect.PETALDANCE -> {
                    if (attackingPokemon.multiTurn.first != RBYMove.EMPTY) {
                        val roll = ThreadLocalRandom.current().nextBoolean()
                        attackingPokemon.multiTurn = if (roll) Pair(move, 2)
                        else Pair(move, 3)
                    } else {
                        var turns = attackingPokemon.multiTurn.second
                        if (turns > 1) {
                            turns -= 1
                            attackingPokemon.multiTurn = Pair(move, turns)
                        } else {
                            attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
                            // does sub also protect against self confusion?
                            if (attackingPokemon.substituteHP != 0) return
                            applyStatus(attackingPokemon, Status.CONFUSION)
                            attackingPokemon.confuseCounter =
                                    ThreadLocalRandom.current().nextInt(1, 5)
                        }
                    }
                }

                // GLARE, STUN_SPORE, THUNDER_WAVE
                MoveEffect.PRZ -> applyStatus(defendingPokemon, Status.PARALYSIS)

                // POISON_GAS, POISON_POWDER
                MoveEffect.PSN -> {
                    if (defendingPokemon.substituteHP != 0) return
                    if (defendingPokemon.types.contains(Type.POISON)) return
                    applyStatus(defendingPokemon, Status.POISON)
                }

                MoveEffect.PSYWAVE -> {
                    val damage = ThreadLocalRandom.current().nextInt(256)
                    applyDamage(defendingPokemon, damage)
                }

                // implemented in RBYGame
                MoveEffect.QUICKATTACK -> return

                MoveEffect.RAGE -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Rage_(move)#Generation_I
                    // TODO disable
                    if (attackingPokemon.multiTurn.first != RBYMove.RAGE) {
                        attackingPokemon.multiTurn = Pair(RBYMove.RAGE, Int.MAX_VALUE)
                        attackingPokemon.applyVolatileStatus(Status.RAGE)
                    }
                }

                // RAZOR_WIND, SKULLBASH, SKY_ATTACK, SOLARBEAM
                MoveEffect.RAZORWIND -> {
                    if (attackingPokemon.multiTurn.first == RBYMove.EMPTY)
                        attackingPokemon.multiTurn = Pair(move, 1)
                    else {
                        attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
                    }
                }

                // DOUBLE_EDGE, STRUGGLE, SUBMISSION, TAKE_DOWN
                MoveEffect.RECOIL25 -> {
                    // struggle does not hit ghost types
                    // two ghost pokemon struggle soft lock
                    // but only one fully evolved ghost pokemon
                    // ditto vs gengar could softlock, but unlikely
                    if (defendingPokemon.substituteBreak) return
                    val recoil = (defendingPokemon.lastDamageTaken / 4)
                            .coerceAtMost(attackingPokemon.currentHP)
                    applyDamage(attackingPokemon, recoil, true, ignoreSubstitute = true)
                    Messages.logRecoil(attackingPokemon, recoil)
                    if (defendingPokemon.tookTurn) {
                        attackingPokemon.counterable = true
                        attackingPokemon.counterDamage = recoil
                    }
                }

                // RECOVER, SOFTBOILED
                MoveEffect.RECOVER -> {
                    if ((attackingPokemon.currentHP < attackingPokemon.statHP)
                            && ((attackingPokemon.statHP - attackingPokemon.currentHP) % 256 != 255)) {
                        attackingPokemon.applyHeal(attackingPokemon.statHP / 2)
                        logHeal(attackingPokemon)
                    } else cantRestFullHp(attackingPokemon)
                }

                // implemented in RBYDamage
                MoveEffect.REFLECT -> applyStatus(attackingPokemon, Status.REFLECT)

                MoveEffect.REST -> {
                    if ((attackingPokemon.currentHP < attackingPokemon.statHP)
                            && ((attackingPokemon.statHP - attackingPokemon.currentHP) % 256 != 255)) {
                        logRest(attackingPokemon)
                        attackingPokemon.applyHeal(attackingPokemon.statHP)
                        attackingPokemon.applyNonVolatileStatus(Status.SLEEP)
                        attackingPokemon.setSleepCounter(1, 1)
                    } else cantRestFullHp(attackingPokemon)
                }

                // NIGHT_SHADE, SEISMIC_TOSS
                MoveEffect.SEISMICTOSS -> applyDamage(defendingPokemon, attackingPokemon.level)

                // EXPLOSION, SELF_DESTRUCT
                MoveEffect.SELFDESTRUCT -> {
                    if (defendingPokemon.substituteBreak) return
                    applyDamage(attackingPokemon, attackingPokemon.currentHP)
                }

                // MEDITATE, SHARPEN
                MoveEffect.SELF_ATTACKRAISE1 -> {
                    statGlitch(defendingPokemon)
                    attackingPokemon.raiseStat(Stat.ATTACK)
                }

                // SWORDS_DANCE
                MoveEffect.SELF_ATTACKRAISE2 -> {
                    statGlitch(defendingPokemon)
                    attackingPokemon.resetStat(Stat.ATTACK)
                    attackingPokemon.raiseStatSharp(Stat.ATTACK)
                }

                // DEFENSE_CURL, HARDEN, WITHDRAW
                MoveEffect.SELF_DEFENSERAISE1 -> {
                    statGlitch(defendingPokemon)
                    attackingPokemon.raiseStat(Stat.DEFENSE)
                }

                // ACID_ARMOR, BARRIER
                MoveEffect.SELF_DEFENSERAISE2 -> {
                    statGlitch(defendingPokemon)
                    attackingPokemon.raiseStatSharp(Stat.DEFENSE)
                }

                // DOUBLE_TEAM, MINIMIZE
                MoveEffect.SELF_EVASIONRAISE1 -> {
                    statGlitch(defendingPokemon)
                    attackingPokemon.raiseStat(Stat.EVASION)
                }

                // GROWTH
                MoveEffect.SELF_SPECIALRAISE1 -> {
                    statGlitch(defendingPokemon)
                    attackingPokemon.raiseStat(Stat.SPECIAL)
                }

                // AMNESIA
                MoveEffect.SELF_SPECIALRAISE2 -> {
                    statGlitch(defendingPokemon)
                    attackingPokemon.raiseStatSharp(Stat.SPECIAL)
                }

                // AGILITY
                MoveEffect.SELF_SPEEDRAISE2 -> {
                    statGlitch(defendingPokemon)
                    attackingPokemon.resetStat(Stat.SPEED)
                    attackingPokemon.raiseStatSharp(Stat.SPEED)
                }

                // HYPNOSIS, LOVELY_KISS, SING, SLEEP_POWDER, SPORE
                MoveEffect.SLEEP -> {
                    applyStatus(defendingPokemon, Status.SLEEP)
                    defendingPokemon.setSleepCounter(0, 7)
                    if (defendingPokemon.volatileStatus.contains(Status.HYPERBEAM))
                        defendingPokemon.removeVolatileStatus(Status.HYPERBEAM)
                }

                MoveEffect.SONICBOOM -> applyDamage(defendingPokemon, 20)

                MoveEffect.SUBSTITUTE -> {
                    // exact damage kill
                    if (attackingPokemon.currentHP >= (attackingPokemon.statHP / 4)
                            && attackingPokemon.substituteHP == 0) {
                        applyDamage(attackingPokemon, attackingPokemon.statHP / 4)
                        attackingPokemon.substituteHP = (attackingPokemon.statHP / 4) + 1
                    }
                }

                MoveEffect.SUPERFANG -> {
                    val damage = (defendingPokemon.currentHP / 2).coerceAtLeast(1)
                    applyDamage(defendingPokemon, damage)
                }

                MoveEffect.SWIFT -> {
                    val damage = calcDamage(attackingPokemon, defendingPokemon, move)
                    applyDamage(defendingPokemon, damage, true)
                }

                MoveEffect.TOXIC -> {
                    applyStatus(defendingPokemon, Status.TOXIC)
                    defendingPokemon.resetToxicCounter()
                }

                MoveEffect.TRANSFORM -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Transform_(move)#Generation_I
                    // in gen 1, pokemon can use transform if they've already transformed
                    // but don't want to overwrite initial state
                    if (attackingPokemon.transformed) return
                    attackingPokemon.transform(defendingPokemon)
                }

                MoveEffect.TWINNEEDLE -> {
                    if (lastHit(attackingPokemon, defendingPokemon.substituteBreak)) {
                        val roll = ThreadLocalRandom.current().nextInt(256)
                        if (roll < 51) applyStatus(defendingPokemon, Status.POISON)
                    }
                }

                else -> unknown(move.effect)
            }
        }

        private fun chanceEffect(
                defendingPokemon: RBYPokemon,
                move: RBYMove) {
            when (move.effect) {
                // alphabetical
                // EMBER, FIRE_PUNCH, FLAMETHROWER, FIRE_BLAST
                MoveEffect.BRN10,
                MoveEffect.BRN30,
                -> {
                    if (defendingPokemon.substituteHP != 0) return
                    if (defendingPokemon.types.contains(Type.FIRE)) return
                    applyStatus(defendingPokemon, Status.BURN)
                }

                // CONFUSION, PSYBEAM
                MoveEffect.CONFUSION10 -> {
                    if (defendingPokemon.substituteBreak) return
                    applyStatus(defendingPokemon, Status.CONFUSION)
                    defendingPokemon.confuseCounter =
                            ThreadLocalRandom.current().nextInt(1, 5)
                }

                // BITE, BONE_CLUB, HYPER_FANG, HEADBUTT, LOW_KICK, ROLLING_KICK, STOMP
                MoveEffect.FLINCH10,
                MoveEffect.FLINCH30,
                -> {
                    if (defendingPokemon.substituteHP != 0) return
                    if (!defendingPokemon.tookTurn)
                        applyStatus(defendingPokemon, Status.FLINCH)
                }

                // BLIZZARD, ICE_BEAM, ICE_PUNCH
                MoveEffect.FRZ10 -> {
                    if (defendingPokemon.substituteHP != 0) return
                    if (defendingPokemon.types.contains(Type.ICE)) return
                    applyStatus(defendingPokemon, Status.FREEZE)
                }

                // AURORA_BEAM
                MoveEffect.OPP_ATTACKDROP1_10 -> {
                    if (defendingPokemon.substituteHP != 0) return
                    statGlitch(defendingPokemon)
                    defendingPokemon.dropStat(Stat.ATTACK)
                }

                // ACID
                MoveEffect.OPP_DEFENSEDROP1_10 -> {
                    if (defendingPokemon.substituteHP != 0) return
                    statGlitch(defendingPokemon)
                    defendingPokemon.dropStat(Stat.DEFENSE)
                }

                // PSYCHIC
                MoveEffect.OPP_SPECIALDROP1_30 -> {
                    if (defendingPokemon.substituteHP != 0) return
                    statGlitch(defendingPokemon)
                    defendingPokemon.dropStat(Stat.SPECIAL)
                }

                // BUBBLE, BUBBLE_BEAM, CONSTRICT
                MoveEffect.OPP_SPEEDDROP1_10 -> {
                    if (defendingPokemon.substituteHP != 0) return
                    statGlitch(defendingPokemon)
                    defendingPokemon.dropStat(Stat.SPEED)
                }

                // THUNDER, THUNDERBOLT, THUNDERPUNCH, THUNDERSHOCK, BODY_SLAM, LICK
                MoveEffect.PRZ10,
                MoveEffect.PRZ30,
                -> {
                    if (defendingPokemon.substituteHP != 0) return
                    if ((move == RBYMove.BODY_SLAM && defendingPokemon.types.contains(Type.NORMAL))
                            || (move.type == Type.ELECTRIC && defendingPokemon.types.contains(Type.ELECTRIC)))
                        return  // only non-damaging electric move that paralyzes is THUNDER_WAVE
                    applyStatus(defendingPokemon, Status.PARALYSIS)
                }

                // POISON_STING, SLUDGE, SMOG
                MoveEffect.PSN20,
                MoveEffect.PSN30,
                -> {
                    if (defendingPokemon.substituteHP != 0) return
                    if (defendingPokemon.types.contains(Type.POISON)) return
                    applyStatus(defendingPokemon, Status.POISON)
                }

                else -> unknown(move.effect)
            }
        }

        private fun applyStatus(pokemon: RBYPokemon, status: Status) {
            if (status.volatility == Status.Volatility.NONVOLATILE) {
                if (pokemon.nonVolatileStatus != Status.HEALTHY)
                    Messages.statusFailed(pokemon, status)
                pokemon.applyNonVolatileStatus(status)
                logNewStatus(pokemon, status)
            } else pokemon.applyVolatileStatus(status)
        }

        // damage implemented in RBYTurn
        private fun lastHit(attackingPokemon: RBYPokemon,
                            substituteBreak: Boolean): Boolean {
            return if (attackingPokemon.extraHit > 0) {
                attackingPokemon.extraHit -= 1
                if (substituteBreak) {
                    attackingPokemon.extraHit = 0
                    attackingPokemon.storedDamage = 0
                }
                false
            } else {
                attackingPokemon.storedDamage = 0
                true
            }
        }

        private fun resetPokemon(pokemon: RBYPokemon) {
            pokemon.resetMods()
            pokemon.clearVolatileStatus()
            pokemon.resetAllCounters()
            pokemon.resetStat(Stat.ATTACK)
            pokemon.resetStat(Stat.SPEED)
        }

        private fun statGlitch(pokemon: RBYPokemon) {
            if (pokemon.nonVolatileStatus == Status.BURN)
                pokemon.applyNonVolatileStatusDebuff(Status.BURN)
            if (pokemon.nonVolatileStatus == Status.PARALYSIS)
                pokemon.applyNonVolatileStatusDebuff(Status.PARALYSIS)
        }

        private fun unknown(effect: MoveEffect) {
//            notImplementedYet(effect)
            throw IllegalArgumentException(
                    "Move effect $effect is not implemented"
            )
        }
    }
}