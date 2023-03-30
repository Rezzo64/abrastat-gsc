package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.logCurrentBehaviour
import com.abrastat.general.Messages.Companion.logNoMoveBehaviourFound
import com.abrastat.general.Messages.Companion.logNoRecoveryMoveFound
import com.abrastat.general.Messages.Companion.notImplementedYet
import com.abrastat.general.PlayerBehaviour.BehaviourGroup
import java.util.*

class RBYPlayer(playerName: String?, pokemon: Pokemon?) : Player() {
    private var currentBehaviour: PlayerBehaviour? = null

    init {
        name = playerName
        addPokemon(pokemon)
        setBehaviours()
    }

    override val currentPokemon: RBYPokemon?
        get() = getPokemon(0) as RBYPokemon?

    fun chooseMove(opponent: RBYPlayer): RBYMove {
        if (justUseFirstAttack) { // ignores other moves, use with caution
            return if (currentPokemon!!.getMovePp(0) > 0) {
                currentPokemon!!.moves[0]
            } else {
                RBYMove.STRUGGLE
            }
        }
        return chooseMoveHelper(opponent)
    }

    // each behaviour should exist as its own entity depending on the simulation state
    fun chooseMoveHelper(opponent: RBYPlayer): RBYMove {

        // just choose the first move as default in case it's not clear what should be done
        var moveChosen = currentPokemon!!.moves[0]
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
                RBYMove.DOUBLE_EDGE,
                RBYMove.DRILL_PECK,
                RBYMove.EARTHQUAKE,
                RBYMove.HYDRO_PUMP,
                RBYMove.NIGHT_SHADE,
                RBYMove.SEISMIC_TOSS,
                RBYMove.STRUGGLE,
                RBYMove.SURF,
                -> behaviourGroups.add(BehaviourGroup.ATTACK)

                RBYMove.EXPLOSION,
                RBYMove.RECOVER,
                RBYMove.REST,
                RBYMove.SELFDESTRUCT,
                RBYMove.SOFTBOILED,
                -> behaviourGroups.add(BehaviourGroup.KO_RESPONSE)

                RBYMove.GROWTH,
                RBYMove.MEDITATE,
                RBYMove.SHARPEN,
                -> behaviourGroups.add(BehaviourGroup.SET_UP)

                RBYMove.ACID_ARMOR,
                RBYMove.AGILITY,
                RBYMove.AMNESIA,
                RBYMove.BARRIER,
                RBYMove.SWORDS_DANCE,
                -> behaviourGroups.add(BehaviourGroup.SET_UP_SHARP)

                RBYMove.HYPNOSIS,
                RBYMove.LOVELY_KISS,
                RBYMove.SING,
                RBYMove.SLEEP_POWDER,
                RBYMove.STUN_SPORE,
                RBYMove.THUNDER_WAVE,
                -> behaviourGroups.add(BehaviourGroup.STATUS_OPPONENT_NON_VOLATILE)

                RBYMove.CONFUSE_RAY,
                -> behaviourGroups.add(BehaviourGroup.VOLATILES)

                RBYMove.BLIZZARD,
                RBYMove.BODY_SLAM,
                RBYMove.FIRE_BLAST,
                RBYMove.FIRE_PUNCH,
                RBYMove.FLAMETHROWER,
                RBYMove.ICE_BEAM,
                RBYMove.ICE_PUNCH,
                RBYMove.THUNDERBOLT,
                -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.STATUS_FISH)
                }

                RBYMove.PSYCHIC,
                -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.STAT_RAISE_DROP_FISH)
                }

                RBYMove.CRABHAMMER,
                RBYMove.RAZOR_LEAF,
                RBYMove.SLASH,
                -> {
                    behaviourGroups.add(BehaviourGroup.ATTACK)
                    behaviourGroups.add(BehaviourGroup.FISH_CRIT)
                }

                RBYMove.HYPER_BEAM,
                -> {
                    behaviourGroups.add(BehaviourGroup.GO_FOR_HYPER_BEAM)
                    behaviourGroups.add(BehaviourGroup.KO_RESPONSE)
                }

                else -> logNoMoveBehaviourFound(currentPokemon!!, move)
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
        var strongestDamage = -1

        // cycle through moveslots
        for (i in 0..3) {
            if (currentPokemon!!.getMovePp(i) < 1) continue

            val currentMove = currentPokemon!!.moves[i]

            if (currentMove.effect === MoveEffect.SELFDESTRUCT) {
                // non-suicidal move can still be selected
                if (strongestAttack == RBYMove.EMPTY)
                    strongestAttack = currentMove
                continue
            }

            if (currentMove.isAttack) {
                val currentDamage = currentPokemon!!
                        .getAttackDamageMaxRoll(opponent, currentMove)

                if (currentDamage > strongestDamage) {
                    strongestDamage = currentDamage
                    strongestAttack = currentMove
                } else if (currentDamage >= opponent!!.currentHP
                        && currentMove.accuracy > strongestAttack.accuracy) {
                    // check if there's a move that has better accuracy and can KO in this range
                    // TODO this only checks max damage roll. Implement something to assess individual damage rolls
                    strongestAttack = currentMove
                }
            } else strongestAttack = currentMove
        }

        // if no strongest move, use any move with pp
        // in case of bug, might be dead code
        if (strongestAttack == RBYMove.EMPTY) {
            for (i in 0..3) {
                if (currentPokemon!!.getMovePp(i) > 0) {
                    strongestAttack = currentPokemon!!.moves[i]
                    break
                }
            }
        }
        // only and always struggle when all moves are out of pp
        return if (strongestAttack == RBYMove.EMPTY) RBYMove.STRUGGLE else strongestAttack
    }

    private fun chooseRecoveryMove(): RBYMove {
        return if (currentPokemon!!.hasMove(RBYMove.REST) > -1) {
            RBYMove.REST
        } else if (currentPokemon!!.hasMove(RBYMove.RECOVER) > -1) {
            RBYMove.RECOVER
        } else if (currentPokemon!!.hasMove(RBYMove.SOFTBOILED) > -1) {
            RBYMove.SOFTBOILED
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