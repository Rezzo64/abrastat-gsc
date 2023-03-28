package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.cantRestFullHp
import com.abrastat.general.Messages.Companion.logDamageTaken
import com.abrastat.general.Messages.Companion.logRest
import java.util.concurrent.ThreadLocalRandom

enum class RBYMoveEffects {
    INSTANCE;

    companion object {
        fun secondaryEffect(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                move: Move) {
            val effect = move.effect

            // none
            if (effect == MoveEffect.NONE) return
            if (effect.target() === MoveEffect.Target.NONE) return
            
            // guaranteed
            if (effect.chance() == 0)
                guaranteedEffect(attackingPokemon, defendingPokemon, move)

            // chance
            val roll = ThreadLocalRandom.current().nextInt(256)
            if (roll < effect.chance())
                chanceEffect(attackingPokemon, defendingPokemon, move)
        }

        private fun guaranteedEffect(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                move: Move) {
            when (val effect = move.effect) {
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
                MoveEffect.CONFUSION -> {
                    applyStatus(defendingPokemon, Status.CONFUSION)
                }

                MoveEffect.CONVERSION -> {

                }

                MoveEffect.COUNTER -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Counter_(move)#Generation_I
                }

                MoveEffect.CRITRATE -> return   // implemented in RBYDamageCalc

                // rename?
                MoveEffect.DIG,
                MoveEffect.FLY,
                -> {

                }

                MoveEffect.DISABLE -> {

                }

                MoveEffect.DOUBLEATTACK -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Double_Kick_(move)#Generation_I
                    // TODO substitute?
                    val setDamage = defendingPokemon.lastDamageTaken
                    guaranteedDamage(defendingPokemon, setDamage)
                }

                MoveEffect.DRAGONRAGE -> {
                    guaranteedDamage(defendingPokemon, 40)
                }

                MoveEffect.DREAMEATER -> {

                }

                MoveEffect.HAZE -> {

                }

                // HIGH_JUMP_KICK, JUMP_KICK
                MoveEffect.HIGHJUMPKICK -> return   // implemented in RBYTurn

                MoveEffect.HYPERBEAM -> {

                }

                MoveEffect.LEECHSEED -> {

                }

                MoveEffect.LIGHTSCREEN -> {

                }

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

                // BARRAGE, COMET_PUNCH, DOUBLESLAP, FURY_ATTACK, FURY_SWIPES, PIN_MISSILE, SPIKE_CANNON
                MoveEffect.MULTIHIT -> {

                }

                // FISSURE, GUILLOTINE, HORN_DRILL
                MoveEffect.ONEHITKO -> {
                    guaranteedDamage(defendingPokemon, defendingPokemon.currentHP)
                }

                // FLASH, KINESIS, SAND_ATTACK, SMOKESCREEN
                MoveEffect.OPP_ACCURACYDROP1 -> {
                    defendingPokemon.dropStat(Stat.ACCURACY)
                }

                // GROWL
                MoveEffect.OPP_ATTACKDROP1 -> {
                    defendingPokemon.dropStat(Stat.ATTACK)
                }

                // LEER, TAIL_WHIP
                MoveEffect.OPP_DEFENSEDROP1 -> {
                    defendingPokemon.dropStat(Stat.DEFENSE)
                }

                // SCREECH
                MoveEffect.OPP_DEFENSEDROP2 -> {
                    defendingPokemon.dropStatSharp(Stat.DEFENSE)
                }

                // STRING_SHOT
                MoveEffect.OPP_SPEEDDROP1 -> {
                    defendingPokemon.dropStat(Stat.SPEED)
                }

                // GLARE, STUN_SPORE, THUNDER_WAVE
                MoveEffect.PRZ -> {
                    applyStatus(defendingPokemon, Status.PARALYSIS)
                }

                // POISON_GAS, POISON_POWDER
                MoveEffect.PSN -> {
                    applyStatus(defendingPokemon, Status.POISON)
                }

                MoveEffect.PSYWAVE -> {
                    val damage = ThreadLocalRandom.current().nextInt(256)
                    guaranteedDamage(defendingPokemon, damage)
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
                    } else {
                        cantRestFullHp(attackingPokemon)
                    }
                }

                MoveEffect.REFLECT -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Reflect_(move)#Generation_I
                }

                MoveEffect.REST -> {
                    if ((attackingPokemon.currentHP < attackingPokemon.statHP)
                            && ((attackingPokemon.statHP - attackingPokemon.currentHP) % 256 != 255)) {
                        logRest(attackingPokemon)
                        attackingPokemon.applyHeal(attackingPokemon.statHP)
                        attackingPokemon.applyNonVolatileStatus(Status.SLEEP)
                        attackingPokemon.setSleepCounter(2, 2)
                    } else {
                        cantRestFullHp(attackingPokemon)
                    }
                }

                MoveEffect.SEISMICTOSS -> {
                    guaranteedDamage(defendingPokemon, attackingPokemon.level)
                }

                // EXPLOSION, SELF_DESTRUCT
                MoveEffect.SELFDESTRUCT -> {
                    // TODO substitute
                    guaranteedDamage(attackingPokemon, attackingPokemon.currentHP)
                }

                // MEDITATE, SHARPEN
                MoveEffect.SELF_ATTACKRAISE1 -> {
                    attackingPokemon.raiseStat(Stat.ATTACK)
                }

                // SWORDS_DANCE
                MoveEffect.SELF_ATTACKRAISE2 -> {
                    attackingPokemon.raiseStatSharp(Stat.ATTACK)
                }

                // DEFENSE_CURL, HARDEN, WITHDRAW
                MoveEffect.SELF_DEFENSERAISE1 -> {
                    attackingPokemon.raiseStat(Stat.DEFENSE)
                }

                // ACID_ARMOR, BARRIER
                MoveEffect.SELF_DEFENSERAISE2 -> {
                    attackingPokemon.raiseStatSharp(Stat.DEFENSE)
                }

                // DOUBLE_TEAM, MINIMIZE
                MoveEffect.SELF_EVASIONRAISE1 -> {
                    attackingPokemon.raiseStat(Stat.EVASION)
                }

                // GROWTH
                MoveEffect.SELF_SPECIALRAISE1 -> {
                    attackingPokemon.raiseStat(Stat.SPECIAL)
                }

                // AMNESIA
                MoveEffect.SELF_SPECIALRAISE2 -> {
                    attackingPokemon.raiseStatSharp(Stat.SPECIAL)
                }

                // AGILITY
                MoveEffect.SELF_SPEEDRAISE2 -> {
                    attackingPokemon.raiseStatSharp(Stat.SPEED)
                }

                // HYPNOSIS, LOVELY_KISS, SING, SLEEP_POWDER, SPORE
                MoveEffect.SLEEP -> {
                    applyStatus(defendingPokemon, Status.SLEEP)
                    defendingPokemon.setSleepCounter(1, 7)
                }

                MoveEffect.SONICBOOM -> {
                    guaranteedDamage(defendingPokemon, 20)
                }

                MoveEffect.SUBSTITUTE -> {
                    // SUBSTITUTE
                }

                MoveEffect.SUPERFANG -> {
                    val damage = (defendingPokemon.currentHP / 2).coerceAtLeast(1)
                    guaranteedDamage(defendingPokemon, damage)
                }

                MoveEffect.SWIFT -> {
                    // Ignores Evasion and Accuracy modifiers and will hit Pokemon even if they are in the middle
                    // of Fly or Dig. Has a 1-in-256 chance of missing.
                }

                // rename?
                // PETAL_DANCE, THRASH
                MoveEffect.THRASH -> {

                }

                MoveEffect.TOXIC -> {
                    // TOXIC
                }

                MoveEffect.TRANSFORM -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Transform_(move)#Generation_I
                }

                MoveEffect.TWINNEEDLE -> {
                    val setDamage = defendingPokemon.lastDamageTaken
                    guaranteedDamage(defendingPokemon, setDamage)
                    val roll = ThreadLocalRandom.current().nextInt(256)
                    if (roll < 51) applyStatus(defendingPokemon, Status.POISON)
                }

                // BIND, CLAMP, FIRE_SPIN, WRAP
                MoveEffect.WRAP -> {
                    // https://bulbapedia.bulbagarden.net/wiki/Wrap_(move)#Generation_I
                    // https://bulbapedia.bulbagarden.net/wiki/Clamp_(move)#Generation_I
                    // https://bulbapedia.bulbagarden.net/wiki/Fire_Spin_(move)#Generation_I
                    // https://bulbapedia.bulbagarden.net/wiki/Bind_(move)#Generation_I
                }

                else -> unknown(effect)
            }
        }

        private fun chanceEffect(
                attackingPokemon: RBYPokemon,
                defendingPokemon: RBYPokemon,
                move: Move) {
            when (val effect = move.effect) {
                // alphabetical
                // EMBER, FIRE_PUNCH, FLAMETHROWER, FIRE_BLAST
                MoveEffect.BRN10,
                MoveEffect.BRN30,
                -> applyStatus(defendingPokemon, Status.BURN)

                // CONFUSION, PSYBEAM
                MoveEffect.CONFUSION10 -> applyStatus(defendingPokemon, Status.CONFUSION)

                // BITE, BONE_CLUB, HYPER_FANG, HEADBUTT, LOW_KICK, ROLLING_KICK, STOMP
                MoveEffect.FLINCH10,
                MoveEffect.FLINCH30,
                -> applyStatus(defendingPokemon, Status.FLINCH) // TODO substitute

                // BLIZZARD, ICE_BEAM, ICE_PUNCH
                MoveEffect.FRZ10 -> applyStatus(defendingPokemon, Status.FREEZE)

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
                -> applyStatus(defendingPokemon, Status.PARALYSIS)

                // POISON_STING, SLUDGE, SMOG
                MoveEffect.PSN20,
                MoveEffect.PSN30,
                -> applyStatus(defendingPokemon, Status.POISON)

                else -> unknown(effect)
            }
        }

        fun guaranteedDamage(pokemon: RBYPokemon, setDamage: Int) {
            var damage = setDamage.coerceAtMost(pokemon.currentHP)
            pokemon.applyDamage(damage)
            logDamageTaken(pokemon, damage)
        }

        private fun applyStatus(pokemon: RBYPokemon, status: Status) {
            if (status.volatility == Status.Volatility.NONVOLATILE) {
                if (pokemon.nonVolatileStatus == status) {
                    Messages.statusFailed(pokemon, status)
                    return
                }

                pokemon.applyNonVolatileStatus(status)
                Messages.logNewStatus(pokemon, status)
            } else {
                pokemon.applyVolatileStatus(status)
            }
        }

        private fun unknown(effect: MoveEffect) {
//            notImplementedYet(effect)
            throw IllegalArgumentException(
                    "Move effect $effect is not implemented"
            )
        }
    }
}