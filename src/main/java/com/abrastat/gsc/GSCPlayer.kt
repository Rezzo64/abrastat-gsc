package com.abrastat.gsc

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.logCurrentBehaviour
import com.abrastat.general.Messages.Companion.logNoGSCMoveBehaviourFound
import com.abrastat.general.Messages.Companion.logNoRecoveryMoveFound
import com.abrastat.general.Messages.Companion.notImplementedYet
import com.abrastat.general.PlayerBehaviour.BehaviourGroup
import java.util.*

class GSCPlayer(playerName: String?, pokemon: Pokemon?) : Player() {
    private val hasSleepTalk: Int
    private var currentBehaviour: PlayerBehaviour? = null

    init {
        name = playerName
        addPokemon(pokemon)
        hasSleepTalk = currentPokemon!!.hasMove(GSCMove.SLEEP_TALK)
        setBehaviours()
    }

    override val currentPokemon: GSCPokemon?
        get() = getPokemon(0) as GSCPokemon?

    fun chooseMove(opponent: GSCPlayer): GSCMove {
        var chosenMove = GSCMove.EMPTY
        if (justUseFirstAttack) { // ignores other moves, use with caution
            return if (currentPokemon!!.getMovePp(0) > 0) {
                currentPokemon!!.moves[0]
            } else {
                GSCMove.STRUGGLE
            }
        }
        chosenMove = chooseMoveHelper(opponent)
        return chosenMove
    }

    // each behaviour should exist as its own entity depending on the simulation state
    fun chooseMoveHelper(opponent: GSCPlayer): GSCMove {

        // just choose the first move as default in case it's not clear what should be done
        var moveChosen = currentPokemon!!.moves[0]
        if (hasSleepTalk > -1 && notAboutToWake()) {

            // check if any Sleep Talk PP remains
            if (currentPokemon!!.getMovePp(currentPokemon!!.hasMove(GSCMove.SLEEP_TALK)) > 0) {
                return GSCMove.SLEEP_TALK
            }
        }
        when (currentBehaviour) {
            PlayerBehaviour.JUST_ATTACK ->  // pre-processing damage calculations to choose the strongest attack
                moveChosen = getStrongestAttack(opponent.currentPokemon)

            PlayerBehaviour.RECOVER_SAFELY -> if (!opponentCritMayKO(opponent)) {
            } else {
                moveChosen = chooseRecoveryMove()
            }

            PlayerBehaviour.RECOVER_RISKILY -> if (!opponentMayKO(opponent)) {
            } else {
                moveChosen = chooseRecoveryMove()
            }

            PlayerBehaviour.FISH_FOR_PARALYSIS -> if (opponent.currentPokemon!!.nonVolatileStatus === Status.HEALTHY) {
            }

            else -> {
                notImplementedYet(currentBehaviour.toString())
            }
        }
        return moveChosen
    }

    fun setCurrentBehaviour(behaviour: PlayerBehaviour?) {
        logCurrentBehaviour(this, behaviour!!)
        currentBehaviour = behaviour
        currentPokemon!!.activeBehaviour = behaviour
    }

    override fun setBehaviours() {
        if (currentPokemon!!.countEmptyMoves() == 3) {
            justUseFirstAttack = true
            return
        }

        // Firstly, disable mindlessly attacking with the strongest move
        justUseFirstAttack = false
        val behaviourGroups = HashSet<BehaviourGroup>()

        // attribute each attack to a behaviour type & collect each behaviour group
        for (move in currentPokemon!!.moves) {
            when (move) {
                GSCMove.BEAT_UP, GSCMove.DOUBLE_EDGE, GSCMove.DRILL_PECK, GSCMove.EARTHQUAKE, GSCMove.FLAIL, GSCMove.GIGA_DRAIN, GSCMove.HIDDEN_POWER, GSCMove.HYDRO_PUMP, GSCMove.MEGAHORN, GSCMove.NIGHT_SHADE, GSCMove.RETURN, GSCMove.REVERSAL, GSCMove.ROLLOUT, GSCMove.SEISMIC_TOSS, GSCMove.SURF, GSCMove.STRUGGLE
                -> behaviourGroups.add(BehaviourGroup.ATTACK)

                GSCMove.MILK_DRINK, GSCMove.RECOVER, GSCMove.REST, GSCMove.SOFTBOILED, GSCMove.SELFDESTRUCT, GSCMove.EXPLOSION, GSCMove.DESTINY_BOND
                -> behaviourGroups.add(BehaviourGroup.KO_RESPONSE)

                GSCMove.CURSE, GSCMove.GROWTH, GSCMove.MEDITATE, GSCMove.SHARPEN
                -> behaviourGroups.add(BehaviourGroup.SET_UP)

                GSCMove.ACID_ARMOR, GSCMove.AMNESIA, GSCMove.AGILITY, GSCMove.BARRIER, GSCMove.SWORDS_DANCE
                -> behaviourGroups.add(BehaviourGroup.SET_UP_SHARP)

                GSCMove.BELLY_DRUM
                -> behaviourGroups.add(BehaviourGroup.BELLY)

                GSCMove.HYPNOSIS, GSCMove.LOVELY_KISS, GSCMove.SING, GSCMove.SLEEP_POWDER, GSCMove.STUN_SPORE, GSCMove.THUNDER_WAVE
                -> behaviourGroups.add(BehaviourGroup.STATUS_OPPONENT_NON_VOLATILE)

                GSCMove.ATTRACT, GSCMove.CONFUSE_RAY, GSCMove.SWAGGER
                -> behaviourGroups.add(BehaviourGroup.VOLATILES)

                GSCMove.BODY_SLAM, GSCMove.THUNDER, GSCMove.THUNDERBOLT, GSCMove.ZAP_CANNON, GSCMove.FIRE_BLAST, GSCMove.FIRE_PUNCH, GSCMove.FLAMETHROWER, GSCMove.SACRED_FIRE, GSCMove.BLIZZARD, GSCMove.ICE_BEAM, GSCMove.ICE_PUNCH, GSCMove.SLUDGE_BOMB
                -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.STATUS_FISH)
                }

                GSCMove.CRUNCH, GSCMove.PSYCHIC -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.STAT_RAISE_DROP_FISH)
                }

                GSCMove.CROSS_CHOP -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.FISH_CRIT)
                }

                GSCMove.THIEF -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.STEAL)
                }

                GSCMove.HYPER_BEAM -> {
                    behaviourGroups.add(BehaviourGroup.GO_FOR_HYPER_BEAM)
                    behaviourGroups.add(BehaviourGroup.KO_RESPONSE)
                }

                else -> logNoGSCMoveBehaviourFound(currentPokemon!!, move!!)
            }
        }
        // add all discovered behaviours to Player for utilisation
        for (group in behaviourGroups) {
            activeBehaviours.addAll(Arrays.asList(*group.behaviours))
        }
    }

    private fun notAboutToWake(): Boolean {
        return if (currentPokemon!!.nonVolatileStatus === Status.SLEEP) {
            currentPokemon!!.sleepCounter > 0
        } else false
    }

    fun getStrongestAttack(opponent: GSCPokemon?): GSCMove {
        var strongestAttack = GSCMove.EMPTY
        var currentDamage = -1
        var strongestDamage = 0
        var emptyMove = 0

        // cycle through moveslots
        for (i in 0..3) {
            if (currentPokemon!!.getMovePp(i) < 1) {
                emptyMove++
                continue
            }
            if (currentPokemon!!.moves[i].effect() === MoveEffect.SELFDESTRUCT) {
                if (strongestAttack == GSCMove.EMPTY) {
                    // keep strongestDamage as 0 so that a non-suicidal move can still be selected
                    strongestAttack = currentPokemon!!.moves[i]
                }
                continue
            }
            if (currentPokemon!!.moves[i].isAttack) {
                currentDamage = currentPokemon!!.getAttackDamageMaxRoll(
                        opponent,
                        currentPokemon!!.moves[i]
                )
                if (currentDamage > strongestDamage) {
                    strongestDamage = currentDamage
                }

                // check if there's a move that has better accuracy and can KO in this range
                // TODO this only checks max damage roll. Implement something to assess individual damage rolls
                if (currentDamage == opponent!!.currentHP
                        &&
                        currentPokemon!!.moves[i].accuracy() > strongestAttack.accuracy()) {
                    strongestAttack = currentPokemon!!.moves[i]
                }
            } else {
                strongestAttack = currentPokemon!!.moves[i]
            }
        }
        return if (emptyMove == 4) GSCMove.STRUGGLE else strongestAttack // only and always struggle when all moves are out of pp
    }

    private fun chooseRecoveryMove(): GSCMove {
        return if (currentPokemon!!.hasMove(GSCMove.REST) > -1) {
            GSCMove.REST
        } else if (currentPokemon!!.hasMove(GSCMove.RECOVER) > -1) {
            GSCMove.RECOVER
        } else if (currentPokemon!!.hasMove(GSCMove.SOFTBOILED) > -1) {
            GSCMove.SOFTBOILED
        } else if (currentPokemon!!.hasMove(GSCMove.MILK_DRINK) > -1) {
            GSCMove.MILK_DRINK
        } else {
            logNoRecoveryMoveFound(currentPokemon!!)
            currentPokemon!!.moves[0]
        }
    }

    private fun opponentMayKO(opponent: GSCPlayer): Boolean {
        val damage = opponent.currentPokemon!!.getAttackDamageMaxRoll(
                currentPokemon,
                opponent.getStrongestAttack(currentPokemon))

        // double the risk if slower, in order to recover early
        return if (currentPokemon!!.statSpe < opponent.currentPokemon!!.statSpe) damage >= 2 * currentPokemon!!.currentHP else damage >= currentPokemon!!.currentHP
    }

    private fun opponentCritMayKO(opponent: GSCPlayer): Boolean {
        val damage = opponent.currentPokemon!!.getAttackDamageCritMaxRoll(
                currentPokemon,
                opponent.getStrongestAttack(currentPokemon))

        // 1.5* the risk if slower in order to recover early. Back-to-back crits are exceedingly unlikely
        return if (currentPokemon!!.statSpe < opponent.currentPokemon!!.statSpe) damage >= Math.floor(1.5 * currentPokemon!!.currentHP) else damage >= currentPokemon!!.currentHP
    }
}