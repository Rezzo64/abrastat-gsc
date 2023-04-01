package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.logCurrentBehaviour
import com.abrastat.general.Messages.Companion.logNoMoveBehaviourFound
import com.abrastat.general.Messages.Companion.notImplementedYet
import com.abrastat.general.PlayerBehaviour.BehaviourGroup
import kotlin.math.floor

class RBYPlayer(playerName: String, pokemon: RBYPokemon) : Player() {
    // this program takes in a pokemon and
    // chooses moves based on current strategy
    private var currentBehaviour: PlayerBehaviour? = null

    init {
        name = playerName
        addPokemon(pokemon as Pokemon)
        setBehaviours()
    }

    override val currentPokemon: RBYPokemon
        get() = getPokemon(0) as RBYPokemon

    fun chooseMove(opponent: RBYPlayer): RBYMove {
        // default no move
        if (currentPokemon.struggle) return RBYMove.STRUGGLE

        // default one move
        if (justUseFirstAttack) {
            return if (currentPokemon.getMovePp(0) > 0)
                currentPokemon.moves[0]
            else {
                currentPokemon.struggle = true
                RBYMove.STRUGGLE
            }
        }

        var move = chooseMoveHelper(opponent)

        // if invalid move, use any move with pp
        if (move == RBYMove.EMPTY) {
            for (i in 0..3) {
                if (currentPokemon.getMovePp(i) > 0) {
                    move = currentPokemon.moves[i]
                    break
                }
            }
        }

        if (move == RBYMove.EMPTY) currentPokemon.struggle = true
        return if (!currentPokemon.struggle) move else RBYMove.STRUGGLE
    }

    // each behaviour should exist as its own entity depending on the simulation state
    private fun chooseMoveHelper(opponent: RBYPlayer): RBYMove {
        // choose move based on strategy
        // pre-computation
        val strongestAttack = getStrongestAttack(opponent.currentPokemon)
//        val canDebuff = opponent.currentPokemon.nonVolatileStatus === Status.HEALTHY

        var moveChosen = RBYMove.EMPTY

        when (currentBehaviour) {
            PlayerBehaviour.JUST_ATTACK ->
                moveChosen = strongestAttack

            PlayerBehaviour.RECOVER_SAFELY -> {
                moveChosen = if (opponentCritMayKO(opponent))
                    chooseRecoveryMove()
                else strongestAttack
            }

            PlayerBehaviour.RECOVER_RISKILY -> {
                moveChosen = if (opponentMayKO(opponent))
                    chooseRecoveryMove()
                else strongestAttack
            }

            else -> {
                notImplementedYet(currentBehaviour.toString())
            }
        }

        return moveChosen
    }

    fun setCurrentBehaviour(behaviour: PlayerBehaviour) {
        logCurrentBehaviour(this, behaviour)
        currentBehaviour = behaviour
        currentPokemon.activeBehaviour = behaviour  // unsure if needed
    }

    override fun setBehaviours() {
        if (currentPokemon.countEmptyMoves() == 3) {
            justUseFirstAttack = true
            return
        }

        justUseFirstAttack = false
        val behaviourGroups = HashSet<BehaviourGroup>()

        // TODO add other moves, unsure how strategies work
        // attribute each attack to a behaviour type & collect each behaviour group
        for (move in currentPokemon.moves) {
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

                else -> logNoMoveBehaviourFound(currentPokemon, move)
            }
        }

        for (group in behaviourGroups)
            activeBehaviours.addAll(listOf(*group.behaviours))
    }


    // TODO maybe refactor move helper functions in separate companion object
    private fun getStrongestAttack(opponent: RBYPokemon): RBYMove {
        var strongestAttack = RBYMove.EMPTY
        var strongestDamage = -1

        // cycle through moveslots
        for (i in 0..3) {
            // skip EMPTY
            if (currentPokemon.getMovePp(i) < 1) continue

            val currentMove = currentPokemon.moves[i]
            if (currentMove.effect === MoveEffect.SELFDESTRUCT) {
                // non-suicidal move can still be selected
                if (strongestAttack == RBYMove.EMPTY)
                    strongestAttack = currentMove
                continue
            }

            if (currentMove.isAttack) {
                val currentDamage = currentPokemon
                        .getAttackDamageMaxRoll(opponent, currentMove)
                if (currentDamage > strongestDamage) {
                    strongestDamage = currentDamage
                    strongestAttack = currentMove
                } else if (currentDamage >= opponent.currentHP
                        && currentMove.accuracy > strongestAttack.accuracy) {
                    // TODO this only checks max damage roll
                    //  Implement something to assess individual damage rolls
                    strongestAttack = currentMove
                }
            }
        }

        return strongestAttack
    }

    private fun opponentMayKO(opponent: RBYPlayer): Boolean {
        val damage = opponent.currentPokemon.getAttackDamageMaxRoll(
                this.currentPokemon, opponent.getStrongestAttack(this.currentPokemon))

        // double the risk if slower, in order to recover early
        return if (currentPokemon.statSpe < opponent.currentPokemon.statSpe)
            damage >= 2 * currentPokemon.currentHP
        else damage >= currentPokemon.currentHP
    }

    private fun opponentCritMayKO(opponent: RBYPlayer): Boolean {
        val damage = opponent.currentPokemon.getAttackDamageCritMaxRoll(
                this.currentPokemon, opponent.getStrongestAttack(this.currentPokemon))

        // 1.5* the risk if slower in order to recover early. Back-to-back crits are exceedingly unlikely
        return if (currentPokemon.statSpe < opponent.currentPokemon.statSpe)
            damage >= floor(1.5 * currentPokemon.currentHP)
        else damage >= currentPokemon.currentHP
    }

    private fun chooseRecoveryMove(): RBYMove {
        return if (currentPokemon.validMove(RBYMove.REST))
            RBYMove.REST
        else if (currentPokemon.validMove(RBYMove.RECOVER))
            RBYMove.RECOVER
        else if (currentPokemon.validMove(RBYMove.SOFTBOILED))
            RBYMove.SOFTBOILED
        else RBYMove.EMPTY
    }
}