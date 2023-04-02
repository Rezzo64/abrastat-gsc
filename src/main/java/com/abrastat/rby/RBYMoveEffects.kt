package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.cantRestFullHp
import com.abrastat.general.Messages.Companion.logHeal
import com.abrastat.general.Messages.Companion.logNewStatus
import com.abrastat.general.Messages.Companion.logRest
import com.abrastat.rby.RBYDamage.Companion.applyDamage
import java.util.concurrent.ThreadLocalRandom

class RBYMoveEffects {
    // this program implements
    // move effects of all moves
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
                }

                MoveEffect.DOUBLEATTACK,
                MoveEffect.MULTIHIT,
                MoveEffect.TWINNEEDLE,
                -> lastHit(attackingPokemon)

                // HIGH_JUMP_KICK, JUMP_KICK
                MoveEffect.HIGHJUMPKICK -> applyDamage(attackingPokemon, 1)

                else -> return
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
                    // https://bulbapedia.bulbagarden.net/wiki/Mega_Drain_(move)#Generations_I_and_II
                    // TODO substitute
                    val absorb = defendingPokemon.lastDamageTaken / 2
                    attackingPokemon.applyHeal(absorb)
                    logHeal(attackingPokemon)
                }

                MoveEffect.BIDE -> {
                    // https://www.smogon.com/rb/articles/rby_mechanics_guide#bide
                    TODO()
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
                            in 6 until 7 -> 3
                            in 7 until 8 -> 4
                            else -> 0
                        }
                        attackingPokemon.multiTurn = Pair(move, turns)
                        attackingPokemon.storedDamage = defendingPokemon.lastDamageTaken
                        applyStatus(defendingPokemon, Status.BIND)
                        logNewStatus(defendingPokemon, Status.BIND)
                        if (defendingPokemon.volatileStatus.contains(Status.HYPERBEAM))
                            defendingPokemon.removeVolatileStatus(Status.HYPERBEAM)
                    } else {
                        var turns = attackingPokemon.multiTurn.second
                        if (turns > 1) {
                            turns -= 1
                            attackingPokemon.multiTurn = Pair(move, turns)
                            attackingPokemon.storedDamage = 0
                        } else attackingPokemon.multiTurn = Pair(RBYMove.EMPTY, 0)
                    }
                }

                // CONFUSE_RAY, SUPERSONIC
                MoveEffect.CONFUSION -> {
                    applyStatus(defendingPokemon, Status.CONFUSION)
                    defendingPokemon.confuseCounter =
                            ThreadLocalRandom.current().nextInt(1, 5)
                }

                MoveEffect.CONVERSION -> {
                    TODO()
                }

                MoveEffect.COUNTER -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Counter_(move)#Generation_I
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
                -> lastHit(attackingPokemon)

                MoveEffect.DRAGONRAGE -> applyDamage(defendingPokemon, 40)

                MoveEffect.DREAMEATER -> {
                    TODO()
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

                // DIG, FLY
                MoveEffect.HIDE -> {
                    TODO()
                }

                // implemented in miss effects
                MoveEffect.HIGHJUMPKICK -> return

                MoveEffect.HYPERBEAM -> {
                    // TODO does not break substitute
                    // TODO recharge doesn't count to confusion count
                    if (defendingPokemon.currentHP != 0)
                        applyStatus(attackingPokemon, Status.HYPERBEAM)
                }

                MoveEffect.LEECHSEED -> {
                    if (!defendingPokemon.types.contains(Type.GRASS))
                        applyStatus(defendingPokemon, Status.LEECHSEED)
                }

                // implemented in RBYDamage
                MoveEffect.LIGHTSCREEN -> applyStatus(attackingPokemon, Status.LIGHTSCREEN)

                MoveEffect.METRONOME -> {
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

                MoveEffect.MIST -> {
                    TODO()
                }

                // FISSURE, GUILLOTINE, HORN_DRILL
                MoveEffect.ONEHITKO -> applyDamage(defendingPokemon, defendingPokemon.currentHP)

                // TODO does statGlitch still apply if defending pokemon drops a different stat?
                // FLASH, KINESIS, SAND_ATTACK, SMOKESCREEN
                MoveEffect.OPP_ACCURACYDROP1 -> defendingPokemon.dropStat(Stat.ACCURACY)

                // GROWL
                MoveEffect.OPP_ATTACKDROP1 -> defendingPokemon.dropStat(Stat.ATTACK)

                // LEER, TAIL_WHIP
                MoveEffect.OPP_DEFENSEDROP1 -> defendingPokemon.dropStat(Stat.DEFENSE)

                // SCREECH
                MoveEffect.OPP_DEFENSEDROP2 -> defendingPokemon.dropStatSharp(Stat.DEFENSE)

                // STRING_SHOT
                MoveEffect.OPP_SPEEDDROP1 -> defendingPokemon.dropStat(Stat.SPEED)

                // PETAL_DANCE, THRASH
                MoveEffect.PETALDANCE -> {
                    TODO()
                }

                // GLARE, STUN_SPORE, THUNDER_WAVE
                MoveEffect.PRZ -> applyStatus(defendingPokemon, Status.PARALYSIS)

                // POISON_GAS, POISON_POWDER
                MoveEffect.PSN -> {
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
                    TODO()
                }

                // RAZOR_WIND, SKULLBASH, SKY_ATTACK, SOLARBEAM
                MoveEffect.RAZORWIND -> {
                    TODO()
                }

                // DOUBLE_EDGE, STRUGGLE, SUBMISSION, TAKE_DOWN
                MoveEffect.RECOIL25 -> {
                    val recoil = defendingPokemon.lastDamageTaken / 4
                    attackingPokemon.applyDamage(recoil)
                    Messages.logRecoil(attackingPokemon, recoil)
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
                    // TODO substitute
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
                    // SUBSTITUTE
                    TODO()
                }

                MoveEffect.SUPERFANG -> {
                    val damage = (defendingPokemon.currentHP / 2).coerceAtLeast(1)
                    applyDamage(defendingPokemon, damage)
                }

                MoveEffect.SWIFT -> {
                    // Ignores Evasion and Accuracy modifiers and will hit Pokemon even if they are in the middle
                    // of Fly or Dig. Has a 1-in-256 chance of missing.
                    TODO()
                }

                MoveEffect.TOXIC -> {
                    applyStatus(defendingPokemon, Status.TOXIC)
                    defendingPokemon.resetToxicCounter()
                }

                MoveEffect.TRANSFORM -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Transform_(move)#Generation_I
//                    TODO()
                }

                MoveEffect.TWINNEEDLE -> {
                    // https://pokemondb.net/move/twineedle
                    if (lastHit(attackingPokemon)) {
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
                    // https://bulbapedia.bulbagarden.net/wiki/Fire_(type)#Generation_I
                    if (defendingPokemon.types.contains(Type.FIRE)) return
                    applyStatus(defendingPokemon, Status.BURN)
                }

                // CONFUSION, PSYBEAM
                MoveEffect.CONFUSION10 -> {
                    applyStatus(defendingPokemon, Status.CONFUSION)
                    defendingPokemon.confuseCounter =
                            ThreadLocalRandom.current().nextInt(1, 5)
                }

                // BITE, BONE_CLUB, HYPER_FANG, HEADBUTT, LOW_KICK, ROLLING_KICK, STOMP
                MoveEffect.FLINCH10,
                MoveEffect.FLINCH30,
                -> applyStatus(defendingPokemon, Status.FLINCH) // TODO substitute

                // BLIZZARD, ICE_BEAM, ICE_PUNCH
                MoveEffect.FRZ10 -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Ice_(type)#Generation_I
                    if (defendingPokemon.types.contains(Type.ICE)) return
                    applyStatus(defendingPokemon, Status.FREEZE)
                }

                // AURORA_BEAM
                MoveEffect.OPP_ATTACKDROP1_10 -> defendingPokemon.dropStat(Stat.ATTACK)

                // ACID
                MoveEffect.OPP_DEFENSEDROP1_10 -> defendingPokemon.dropStat(Stat.DEFENSE)

                // PSYCHIC
                MoveEffect.OPP_SPECIALDROP1_30 -> defendingPokemon.dropStat(Stat.SPECIAL)

                // BUBBLE, BUBBLE_BEAM, CONSTRICT
                MoveEffect.OPP_SPEEDDROP1_10 -> defendingPokemon.dropStat(Stat.SPEED)

                // THUNDER, THUNDERBOLT, THUNDERPUNCH, THUNDERSHOCK, BODY_SLAM, LICK
                MoveEffect.PRZ10,
                MoveEffect.PRZ30,
                -> {
                    // https://www.smogon.com/forums/threads/normal-types-cannot-be-paralyzed-by-body-slam.3525371/
                    // https://bulbapedia.bulbagarden.net/wiki/Electric_(type)#Generation_I
                    if ((move == RBYMove.BODY_SLAM && defendingPokemon.types.contains(Type.NORMAL))
                            || (move.type == Type.ELECTRIC && defendingPokemon.types.contains(Type.ELECTRIC)))
                        return  // only non-damaging electric move that paralyzes is THUNDER_WAVE
                    applyStatus(defendingPokemon, Status.PARALYSIS)
                }

                // POISON_STING, SLUDGE, SMOG
                MoveEffect.PSN20,
                MoveEffect.PSN30,
                -> {
                    if (defendingPokemon.types.contains(Type.POISON)) return
                    applyStatus(defendingPokemon, Status.POISON)
                }

                else -> unknown(move.effect)
            }
        }

        private fun applyStatus(pokemon: RBYPokemon, status: Status) {
            if (status.volatility == Status.Volatility.NONVOLATILE) {
                if (pokemon.nonVolatileStatus != Status.HEALTHY) {
                    Messages.statusFailed(pokemon, status)
                }

                pokemon.applyNonVolatileStatus(status)
                logNewStatus(pokemon, status)
            } else pokemon.applyVolatileStatus(status)
        }

        // damage implemented in RBYTurn
        private fun lastHit(pokemon: RBYPokemon): Boolean {
            // TODO if substitute.broken pokemon.extraHit = 0, false
            return if (pokemon.extraHit > 0) {
                pokemon.extraHit -= 1
                true
            } else false
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