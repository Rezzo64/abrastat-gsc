package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.logCurrentBehaviour
import com.abrastat.general.Messages.Companion.logNoRBYMoveBehaviourFound
import com.abrastat.general.Messages.Companion.logNoRecoveryMoveFound
import com.abrastat.general.Messages.Companion.notImplementedYet
import com.abrastat.general.PlayerBehaviour.BehaviourGroup
import java.util.*

class RBYPlayer(playerName: String?, pokemon: Pokemon?) : Player() {
    private val hasSleepTalk: Int
    private var currentBehaviour: PlayerBehaviour? = null

    init {
        name = playerName
        addPokemon(pokemon)
        hasSleepTalk = currentPokemon!!.hasMove(RBYMove.SLEEP_TALK)
        setBehaviours()
    }

    override val currentPokemon: RBYPokemon?
        get() = getPokemon(0) as RBYPokemon?

    fun chooseMove(opponent: RBYPlayer): RBYMove {
        var chosenMove = RBYMove.EMPTY
        if (justUseFirstAttack) { // ignores other moves, use with caution
            return if (currentPokemon!!.getMovePp(0) > 0) {
                currentPokemon!!.moves[0]
            } else {
                RBYMove.STRUGGLE
            }
        }
        chosenMove = chooseMoveHelper(opponent)
        return chosenMove
    }

    // each behaviour should exist as its own entity depending on the simulation state
    fun chooseMoveHelper(opponent: RBYPlayer): RBYMove {

        // just choose the first move as default in case it's not clear what should be done
        var moveChosen = currentPokemon!!.moves[0]
        if (hasSleepTalk > -1 && notAboutToWake()) {

            // check if any Sleep Talk PP remains
            if (currentPokemon!!.getMovePp(currentPokemon!!.hasMove(RBYMove.SLEEP_TALK)) > 0) {
                return RBYMove.SLEEP_TALK
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
                RBYMove.BEAT_UP, RBYMove.DOUBLE_EDGE, RBYMove.DRILL_PECK, RBYMove.EARTHQUAKE, RBYMove.FLAIL, RBYMove.GIGA_DRAIN, RBYMove.HIDDEN_POWER, RBYMove.HYDRO_PUMP, RBYMove.MEGAHORN, RBYMove.NIGHT_SHADE, RBYMove.RETURN, RBYMove.REVERSAL, RBYMove.ROLLOUT, RBYMove.SEISMIC_TOSS, RBYMove.SURF, RBYMove.STRUGGLE
                -> behaviourGroups.add(BehaviourGroup.ATTACK)

                RBYMove.MILK_DRINK, RBYMove.RECOVER, RBYMove.REST, RBYMove.SOFTBOILED, RBYMove.SELFDESTRUCT, RBYMove.EXPLOSION, RBYMove.DESTINY_BOND
                -> behaviourGroups.add(BehaviourGroup.KO_RESPONSE)

                RBYMove.CURSE, RBYMove.GROWTH, RBYMove.MEDITATE, RBYMove.SHARPEN
                -> behaviourGroups.add(BehaviourGroup.SET_UP)

                RBYMove.ACID_ARMOR, RBYMove.AMNESIA, RBYMove.AGILITY, RBYMove.BARRIER, RBYMove.SWORDS_DANCE
                -> behaviourGroups.add(BehaviourGroup.SET_UP_SHARP)

                RBYMove.BELLY_DRUM
                -> behaviourGroups.add(BehaviourGroup.BELLY)

                RBYMove.HYPNOSIS, RBYMove.LOVELY_KISS, RBYMove.SING, RBYMove.SLEEP_POWDER, RBYMove.STUN_SPORE, RBYMove.THUNDER_WAVE
                -> behaviourGroups.add(BehaviourGroup.STATUS_OPPONENT_NON_VOLATILE)

                RBYMove.ATTRACT, RBYMove.CONFUSE_RAY, RBYMove.SWAGGER
                -> behaviourGroups.add(BehaviourGroup.VOLATILES)

                RBYMove.BODY_SLAM, RBYMove.THUNDER, RBYMove.THUNDERBOLT, RBYMove.ZAP_CANNON, RBYMove.FIRE_BLAST, RBYMove.FIRE_PUNCH, RBYMove.FLAMETHROWER, RBYMove.SACRED_FIRE, RBYMove.BLIZZARD, RBYMove.ICE_BEAM, RBYMove.ICE_PUNCH, RBYMove.SLUDGE_BOMB
                -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.STATUS_FISH)
                }

                RBYMove.CRUNCH, RBYMove.PSYCHIC -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.STAT_RAISE_DROP_FISH)
                }

                RBYMove.CROSS_CHOP -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.FISH_CRIT)
                }

                RBYMove.THIEF -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.STEAL)
                }

                RBYMove.HYPER_BEAM -> {
                    behaviourGroups.add(BehaviourGroup.GO_FOR_HYPER_BEAM)
                    behaviourGroups.add(BehaviourGroup.KO_RESPONSE)
                }

                else -> logNoRBYMoveBehaviourFound(currentPokemon!!, move!!)
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

    fun getStrongestAttack(opponent: RBYPokemon?): RBYMove {
        var strongestAttack = RBYMove.EMPTY
        var currentDamage = -1
        var strongestDamage = 0
        var emptyMove = 0

        // cycle through moveslots
        for (i in 0..3) {
            if (currentPokemon!!.getMovePp(i) < 1) {
                emptyMove++
                continue
            }
            if (currentPokemon!!.moves[i].effect === MoveEffect.SELFDESTRUCT) {
                if (strongestAttack == RBYMove.EMPTY) {
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
                        currentPokemon!!.moves[i].accuracy > strongestAttack.accuracy) {
                    strongestAttack = currentPokemon!!.moves[i]
                }
            } else {
                strongestAttack = currentPokemon!!.moves[i]
            }
        }
        return if (emptyMove == 4) RBYMove.STRUGGLE else strongestAttack // only and always struggle when all moves are out of pp
    }

    private fun chooseRecoveryMove(): RBYMove {
        return if (currentPokemon!!.hasMove(RBYMove.REST) > -1) {
            RBYMove.REST
        } else if (currentPokemon!!.hasMove(RBYMove.RECOVER) > -1) {
            RBYMove.RECOVER
        } else if (currentPokemon!!.hasMove(RBYMove.SOFTBOILED) > -1) {
            RBYMove.SOFTBOILED
        } else if (currentPokemon!!.hasMove(RBYMove.MILK_DRINK) > -1) {
            RBYMove.MILK_DRINK
        } else {
            logNoRecoveryMoveFound(currentPokemon!!)
            currentPokemon!!.moves[0]
        }
    }

    private fun opponentMayKO(opponent: RBYPlayer): Boolean {
        val damage = opponent.currentPokemon!!.getAttackDamageMaxRoll(
                currentPokemon,
                opponent.getStrongestAttack(currentPokemon))

        // double the risk if slower, in order to recover early
        return if (currentPokemon!!.statSpe < opponent.currentPokemon!!.statSpe) damage >= 2 * currentPokemon!!.currentHP else damage >= currentPokemon!!.currentHP
    }

    private fun opponentCritMayKO(opponent: RBYPlayer): Boolean {
        val damage = opponent.currentPokemon!!.getAttackDamageCritMaxRoll(
                currentPokemon,
                opponent.getStrongestAttack(currentPokemon))

        // 1.5* the risk if slower in order to recover early. Back-to-back crits are exceedingly unlikely
        return if (currentPokemon!!.statSpe < opponent.currentPokemon!!.statSpe) damage >= Math.floor(1.5 * currentPokemon!!.currentHP) else damage >= currentPokemon!!.currentHP
    }
}