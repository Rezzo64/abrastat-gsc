package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.cantRestFullHp
import com.abrastat.general.Messages.Companion.logRest
import com.abrastat.rby.RBYDamage.Companion.applyDamage
import java.util.concurrent.ThreadLocalRandom

class RBYMoveEffects {

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
                move: RBYMove) {
            when (move.effect) {
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
                    val absorb = defendingPokemon.lastDamageTaken / 2
                    // TODO substitute
                    attackingPokemon.applyHeal(absorb)
                }

                MoveEffect.BIDE -> {

                }

                // CONFUSE_RAY, SUPERSONIC
                MoveEffect.CONFUSION -> applyStatus(defendingPokemon, Status.CONFUSION)

                MoveEffect.CONVERSION -> {

                }

                MoveEffect.COUNTER -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Counter_(move)#Generation_I
                }

                // implemented in RBYDamage
                MoveEffect.CRITRATE -> return

                MoveEffect.DISABLE -> {

                }

                // BARRAGE, BONEMERANG, COMET_PUNCH, DOUBLE_KICK, DOUBLESLAP,
                // FURY_ATTACK, FURY_SWIPES, PIN_MISSILE, SPIKE_CANNON
                MoveEffect.DOUBLEATTACK,
                MoveEffect.MULTIHIT,
                -> lastHit(attackingPokemon)

                MoveEffect.DRAGONRAGE -> applyDamage(defendingPokemon, 40)

                MoveEffect.DREAMEATER -> {

                }

                MoveEffect.HAZE -> {
                    val cannotAttack = (defendingPokemon.nonVolatileStatus == Status.FREEZE
                            || defendingPokemon.nonVolatileStatus == Status.SLEEP)
                    reset(attackingPokemon)
                    reset(defendingPokemon)
                    if (attackingPokemon.nonVolatileStatus == Status.TOXIC)
                        attackingPokemon.applyNonVolatileStatus(Status.POISON)
                    defendingPokemon.removeNonVolatileStatus()
                    if (cannotAttack && (attackingPokemon.turn > defendingPokemon.turn))
                        // defending pokemon removed sleep or freeze in same turn
                        defendingPokemon.applyVolatileStatus(Status.HAZE)
                }

                MoveEffect.HIDE -> {

                }

                MoveEffect.HIGHJUMPKICK -> return

                MoveEffect.HYPERBEAM -> {

                }

                MoveEffect.LEECHSEED -> {

                }

                // implemented in RBYDamage
                MoveEffect.LIGHTSCREEN -> applyStatus(attackingPokemon, Status.LIGHTSCREEN)

                MoveEffect.METRONOME -> {

                }

                MoveEffect.MIMIC -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Mimic_(move)#Generation_I
                }

                MoveEffect.MIRRORMOVE -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Mirror_Move_(move)#Generation_I_to_VII
                }

                MoveEffect.MIST -> {

                }

                // FISSURE, GUILLOTINE, HORN_DRILL
                MoveEffect.ONEHITKO -> applyDamage(defendingPokemon, defendingPokemon.currentHP)

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

                MoveEffect.QUICKATTACK -> {

                }

                MoveEffect.RAGE -> {

                }

                // rename?
                // RAZOR_WIND, SKULLBASH, SKY_ATTACK, SOLARBEAM
                MoveEffect.RAZORWIND -> {

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
                MoveEffect.SELF_ATTACKRAISE1 -> attackingPokemon.raiseStat(Stat.ATTACK)

                // SWORDS_DANCE
                MoveEffect.SELF_ATTACKRAISE2 -> attackingPokemon.raiseStatSharp(Stat.ATTACK)

                // DEFENSE_CURL, HARDEN, WITHDRAW
                MoveEffect.SELF_DEFENSERAISE1 -> attackingPokemon.raiseStat(Stat.DEFENSE)

                // ACID_ARMOR, BARRIER
                MoveEffect.SELF_DEFENSERAISE2 -> attackingPokemon.raiseStatSharp(Stat.DEFENSE)

                // DOUBLE_TEAM, MINIMIZE
                MoveEffect.SELF_EVASIONRAISE1 -> attackingPokemon.raiseStat(Stat.EVASION)

                // GROWTH
                MoveEffect.SELF_SPECIALRAISE1 -> attackingPokemon.raiseStat(Stat.SPECIAL)

                // AMNESIA
                MoveEffect.SELF_SPECIALRAISE2 -> attackingPokemon.raiseStatSharp(Stat.SPECIAL)

                // AGILITY
                MoveEffect.SELF_SPEEDRAISE2 -> attackingPokemon.raiseStatSharp(Stat.SPEED)

                // HYPNOSIS, LOVELY_KISS, SING, SLEEP_POWDER, SPORE
                MoveEffect.SLEEP -> {
                    applyStatus(defendingPokemon, Status.SLEEP)
                    defendingPokemon.setSleepCounter(0, 7)
                }

                MoveEffect.SONICBOOM -> applyDamage(defendingPokemon, 20)

                MoveEffect.SUBSTITUTE -> {
                    // SUBSTITUTE
                }

                MoveEffect.SUPERFANG -> {
                    val damage = (defendingPokemon.currentHP / 2).coerceAtLeast(1)
                    applyDamage(defendingPokemon, damage)
                }

                MoveEffect.SWIFT -> {
                    // Ignores Evasion and Accuracy modifiers and will hit Pokemon even if they are in the middle
                    // of Fly or Dig. Has a 1-in-256 chance of missing.
                }

                // rename?
                // PETAL_DANCE, THRASH
                MoveEffect.THRASH -> {

                }

                MoveEffect.TOXIC -> applyStatus(defendingPokemon, Status.TOXIC)

                MoveEffect.TRANSFORM -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Transform_(move)#Generation_I
                }

                MoveEffect.TWINNEEDLE -> {
                    // https://pokemondb.net/move/twineedle
                    if (lastHit(attackingPokemon)) {
                        val roll = ThreadLocalRandom.current().nextInt(256)
                        if (roll < 51) applyStatus(defendingPokemon, Status.POISON)
                    }
                }

                // BIND, CLAMP, FIRE_SPIN, WRAP
                MoveEffect.WRAP -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Wrap_(move)#Generation_I
                    // https://bulbapedia.bulbagarden.net/wiki/Clamp_(move)#Generation_I
                    // https://bulbapedia.bulbagarden.net/wiki/Fire_Spin_(move)#Generation_I
                    // https://bulbapedia.bulbagarden.net/wiki/Bind_(move)#Generation_I
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
                MoveEffect.CONFUSION10 -> applyStatus(defendingPokemon, Status.CONFUSION)

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

        private fun applyStatus(pokemon: RBYPokemon, status: Status): Boolean {
            if (status.volatility == Status.Volatility.NONVOLATILE) {
                if (pokemon.nonVolatileStatus != Status.HEALTHY) {
                    Messages.statusFailed(pokemon, status)
                    return false
                }

                pokemon.applyNonVolatileStatus(status)
                Messages.logNewStatus(pokemon, status)
            } else pokemon.applyVolatileStatus(status)
            return true
        }

        // damage implemented in RBYTurn
        private fun lastHit(pokemon: RBYPokemon): Boolean {
            // TODO if substitute.broken pokemon.extraHit = 0, true
            return if (pokemon.extraHit > 0) {
                pokemon.extraHit -= 1
                true
            } else false
        }

        private fun reset(pokemon: RBYPokemon) {
            pokemon.resetMods()
            pokemon.clearVolatileStatus()
            pokemon.resetSleepCounter()
            pokemon.initStatAtk(pokemon.originalAttack)
            pokemon.initStatSpe(pokemon.originalSpeed)
        }

        private fun unknown(effect: MoveEffect) {
//            notImplementedYet(effect)
            throw IllegalArgumentException(
                    "Move effect $effect is not implemented"
            )
        }
    }
}