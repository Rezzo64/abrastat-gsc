package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Game.Companion.isPokemonFainted
import com.abrastat.general.Messages.Companion.announceTeam
import com.abrastat.general.Messages.Companion.announceTurn
import com.abrastat.general.Messages.Companion.displayCurrentHP
import com.abrastat.general.Messages.Companion.logFainted
import com.abrastat.general.Messages.Companion.statusChanged
import java.util.concurrent.ThreadLocalRandom

class RBYGame(player1: RBYPlayer,
              p1Behaviour: PlayerBehaviour,
              player2: RBYPlayer,
              p2Behaviour: PlayerBehaviour) : Game {
    var turnNumber = 0
        private set
    private val pokemonPlayerOne: RBYPokemon?
    private val pokemonPlayerTwo: RBYPokemon?
    var winner = 0 // 0 = draw, 1 = p1, 2 = p2
        private set
    private var lastMoveUsed: RBYMove? = null
    private var lastAttacker: RBYPokemon? = null

    init {
        pokemonPlayerOne = player1.currentPokemon
        player1.setCurrentBehaviour(p1Behaviour)
        pokemonPlayerTwo = player2.currentPokemon
        player2.setCurrentBehaviour(p2Behaviour)
        announceTeam(player1)
        announceTeam(player2)

        // Messages.announceSwitch(player1, pokemonPlayerOne);
        // Messages.announceSwitch(player2, pokemonPlayerTwo);
        while (!someoneFainted()) {
            // test for infinite loops
            if (turnNumber > 1000) break

            val movePlayerOne = player1.chooseMove(player2)
            val movePlayerTwo = player2.chooseMove(player1)
            initTurn(movePlayerOne, movePlayerTwo)
        }
    }

    fun initTurn(movePlayerOne: RBYMove, movePlayerTwo: RBYMove) {
        turnNumber++
        announceTurn(turnNumber)
        displayCurrentHP(pokemonPlayerOne!!)
        displayCurrentHP(pokemonPlayerTwo!!)

        reset(pokemonPlayerOne)
        reset(pokemonPlayerTwo)

        // IF switch selected
        // IF pursuit also selected

        // IF speed priority !=0 move selected (Quick Attack, Protect, Roar)
        if (playerOneIsFaster(movePlayerOne.effect, movePlayerTwo.effect)) { // true = player1, false = player2
            turn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne)
            turn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo)
        } else {
            turn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo)
            turn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne)
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

    private fun playerOneIsFaster(me1: MoveEffect, me2: MoveEffect): Boolean {
        val meq = MoveEffect.QUICKATTACK        // priority 1
        val mec = MoveEffect.COUNTER            // priority -1
                                                // else priority 0
        return if ((me1 == meq && me2 == meq)   // both QUICK_ATTACK
                || (me1 == mec && me2 == mec))  // both COUNTER
            playerOneIsFasterUtil()
        else if (me1 == meq)
            true
        else if (me2 == meq)
            false
        else if (me1 == mec)
            false
        else if (me2 == mec)
            true
        else
            playerOneIsFasterUtil()
    }

    private fun playerOneIsFasterUtil(): Boolean {
        var speed1 = pokemonPlayerOne!!.modifiedStat(Stat.SPEED)
        var speed2 = pokemonPlayerTwo!!.modifiedStat(Stat.SPEED)

        return if (speed1 == speed2) ThreadLocalRandom.current().nextBoolean() // random player goes first
        else speed1 > speed2
    }

    private fun someoneFainted(): Boolean {
        if (pokemonPlayerOne!!.currentHP == 0) {
            pokemonPlayerOne.removeNonVolatileStatusDebuff()
            pokemonPlayerOne.isFainted
            logFainted(pokemonPlayerOne)
        }
        if (pokemonPlayerTwo!!.currentHP == 0) {
            pokemonPlayerTwo.removeNonVolatileStatusDebuff()
            pokemonPlayerTwo.isFainted
            logFainted(pokemonPlayerTwo)
        }
        setWinner()
        return isPokemonFainted(pokemonPlayerOne, pokemonPlayerTwo)
    }

    val pokemonPlayerOneHP: Int
        get() = pokemonPlayerOne!!.currentHP
    val pokemonPlayerTwoHP: Int
        get() = pokemonPlayerTwo!!.currentHP
    val pokemonPlayerOnePP: IntArray
        get() = intArrayOf(
                pokemonPlayerOne!!.moveOnePp,
                pokemonPlayerOne.moveTwoPp,
                pokemonPlayerOne.moveThreePp,
                pokemonPlayerOne.moveFourPp)
    val pokemonPlayerTwoPP: IntArray
        get() = intArrayOf(
                pokemonPlayerTwo!!.moveOnePp,
                pokemonPlayerTwo.moveTwoPp,
                pokemonPlayerTwo.moveThreePp,
                pokemonPlayerTwo.moveFourPp)
    val isStrugglePlayerOne: Boolean
        get() = pokemonPlayerOne!!.moveOnePp <= 0 && pokemonPlayerOne.moveTwoPp <= 0 && pokemonPlayerOne.moveThreePp <= 0 && pokemonPlayerOne.moveFourPp <= 0
    val isStrugglePlayerTwo: Boolean
        get() = pokemonPlayerTwo!!.moveOnePp <= 0 && pokemonPlayerTwo.moveTwoPp <= 0 && pokemonPlayerTwo.moveThreePp <= 0 && pokemonPlayerTwo.moveFourPp <= 0
    val isBoomedPlayerOne: Boolean
        get() = if (lastAttacker == pokemonPlayerOne) {
            lastMoveUsed == RBYMove.EXPLOSION || lastMoveUsed == RBYMove.SELFDESTRUCT
        } else {
            false
        }
    val isBoomedPlayerTwo: Boolean
        get() = if (lastAttacker == pokemonPlayerTwo) {
            lastMoveUsed == RBYMove.EXPLOSION || lastMoveUsed == RBYMove.SELFDESTRUCT
        } else {
            false
        }

    private fun setWinner() {
        winner = if (pokemonPlayerOne!!.nonVolatileStatus === Status.FAINT && pokemonPlayerTwo!!.nonVolatileStatus === Status.FAINT) {
            0
        } else if (pokemonPlayerOne!!.nonVolatileStatus !== Status.FAINT && pokemonPlayerTwo!!.nonVolatileStatus !== Status.FAINT) {
            // test for infinite loop
            0
        } else if (pokemonPlayerOne!!.nonVolatileStatus === Status.FAINT) {
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