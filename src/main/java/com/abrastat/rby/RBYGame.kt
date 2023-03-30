package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Game.Companion.isPokemonFainted
import com.abrastat.general.Messages.Companion.announceTeam
import com.abrastat.general.Messages.Companion.announceTurn
import com.abrastat.general.Messages.Companion.displayCurrentHP
import com.abrastat.general.Messages.Companion.logFainted
import java.util.concurrent.ThreadLocalRandom

class RBYGame(player1: RBYPlayer,
              p1Behaviour: PlayerBehaviour,
              player2: RBYPlayer,
              p2Behaviour: PlayerBehaviour) : Game {
    var turnNumber = 0
        private set
    private val pokemonP1: RBYPokemon
    private val pokemonP2: RBYPokemon
    var winner = 0 // 0 = draw, 1 = p1, 2 = p2
        private set
    private var lastMoveUsed: RBYMove? = null
    private var lastAttacker: RBYPokemon? = null

    init {
        pokemonP1 = setup(player1, p1Behaviour)
        pokemonP2 = setup(player2, p2Behaviour)

        // Messages.announceSwitch(player1, pokemonP1);
        // Messages.announceSwitch(player2, pokemonP2);
        while (!someoneFainted()) { // TODO player has no non-fainted pokemon remaining
            // test for infinite loops
            if (turnNumber > 1000) break

            val moveP1 = player1.chooseMove(player2)
            val moveP2 = player2.chooseMove(player1)
            initTurn(moveP1, moveP2)
        }
    }

    private fun setup(player: RBYPlayer, behaviour: PlayerBehaviour): RBYPokemon {
        val pokemon = player.currentPokemon ?:
                throw IllegalArgumentException(
                        "${player.name} has no Pokemon"
                )

        player.setCurrentBehaviour(behaviour)
        pokemon.turn = 0
        announceTeam(player)
        return pokemon
    }

    private fun initTurn(moveP1: RBYMove, moveP2: RBYMove) {
        turnNumber++
        announceTurn(turnNumber)
        displayCurrentHP(pokemonP1)
        displayCurrentHP(pokemonP2)

        reset(pokemonP1)
        reset(pokemonP2)

        // IF switch selected
        // IF pursuit also selected

        // IF speed priority !=0 move selected (Quick Attack, Protect, Roar)
        if (pokemonP1IsFaster(moveP1.effect, moveP2.effect)) { // true = player1, false = player2
            turn(pokemonP1, pokemonP2, moveP1)
            turn(pokemonP2, pokemonP1, moveP2)
        } else {
            turn(pokemonP2, pokemonP1, moveP2)
            turn(pokemonP1, pokemonP2, moveP1)
        }
    }

    // prevents Counter, Mirror Coat, Flinch from carrying over
    private fun reset(pokemon: RBYPokemon) {
        pokemon.lastDamageTaken = 0
        pokemon.removeVolatileStatus(Status.FLINCH)
    }

    private fun turn(poke1: RBYPokemon, poke2: RBYPokemon, move: RBYMove) {
        RBYTurn(poke1, poke2, move)
        setLastMoveUsed(move)
        lastAttacker = poke1
    }

    private fun pokemonP1IsFaster(me1: MoveEffect, me2: MoveEffect): Boolean {
        val meq = MoveEffect.QUICKATTACK        // priority 1
        val mec = MoveEffect.COUNTER            // priority -1
                                                // else priority 0
        return if ((me1 == meq && me2 == meq)   // both QUICK_ATTACK
                || (me1 == mec && me2 == mec))  // both COUNTER
            pokemonP1IsFasterUtil()
        else if (me1 == meq)
            true
        else if (me2 == meq)
            false
        else if (me1 == mec)
            false
        else if (me2 == mec)
            true
        else
            pokemonP1IsFasterUtil()
    }

    private fun pokemonP1IsFasterUtil(): Boolean {
        val speed1 = pokemonP1.modifiedStat(Stat.SPEED)
        val speed2 = pokemonP2.modifiedStat(Stat.SPEED)

        return if (speed1 == speed2) ThreadLocalRandom.current().nextBoolean() // random player goes first
        else speed1 > speed2
    }

    private fun someoneFainted(): Boolean {
        if (pokemonP1.currentHP == 0) {
            pokemonP1.removeNonVolatileStatusDebuff()
            pokemonP1.isFainted
            logFainted(pokemonP1)
        }
        if (pokemonP2.currentHP == 0) {
            pokemonP2.removeNonVolatileStatusDebuff()
            pokemonP2.isFainted
            logFainted(pokemonP2)
        }
        setWinner()
        return isPokemonFainted(pokemonP1, pokemonP2)
    }

    val pokemonP1HP: Int
        get() = pokemonP1.currentHP
    val pokemonP2HP: Int
        get() = pokemonP2.currentHP
    private val pokemonP1PP: IntArray
        get() = intArrayOf(
                pokemonP1.moveOnePp,
                pokemonP1.moveTwoPp,
                pokemonP1.moveThreePp,
                pokemonP1.moveFourPp)
    private val pokemonP2PP: IntArray
        get() = intArrayOf(
                pokemonP2.moveOnePp,
                pokemonP2.moveTwoPp,
                pokemonP2.moveThreePp,
                pokemonP2.moveFourPp)
    val isStruggleP1: Boolean
        get() = pokemonP1PP[0] <= 0 && pokemonP1PP[1] <= 0 && pokemonP1PP[2] <= 0 && pokemonP1PP[3] <= 0
    val isStruggleP2: Boolean
        get() = pokemonP2PP[0] <= 0 && pokemonP2PP[1] <= 0 && pokemonP2PP[2] <= 0 && pokemonP2PP[3] <= 0
    val isBoomedP1: Boolean
        get() = if (lastAttacker == pokemonP1) {
            lastMoveUsed == RBYMove.EXPLOSION || lastMoveUsed == RBYMove.SELFDESTRUCT
        } else {
            false
        }
    val isBoomedP2: Boolean
        get() = if (lastAttacker == pokemonP2) {
            lastMoveUsed == RBYMove.EXPLOSION || lastMoveUsed == RBYMove.SELFDESTRUCT
        } else {
            false
        }

    private fun setWinner() {
        winner = if (pokemonP1.nonVolatileStatus === Status.FAINT && pokemonP2.nonVolatileStatus === Status.FAINT) {
            0
        } else if (pokemonP1.nonVolatileStatus !== Status.FAINT && pokemonP2.nonVolatileStatus !== Status.FAINT) {
            // test for infinite loop
            0
        } else if (pokemonP1.nonVolatileStatus === Status.FAINT) {
            2
        } else {
            1
        }
    }

    override fun getLastMoveUsed(): Move? {
        return lastMoveUsed
    }
    override fun setLastMoveUsed(move: Move?) {
        if (move is RBYMove) {
            lastMoveUsed = move
        }
    }
}