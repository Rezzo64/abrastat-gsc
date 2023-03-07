package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Game.Companion.isPokemonFainted
import com.abrastat.general.Messages.Companion.announceTeam
import com.abrastat.general.Messages.Companion.announceTurn
import com.abrastat.general.Messages.Companion.displayCurrentHP
import com.abrastat.general.Messages.Companion.leftoversHeal
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

        // prevents Counter or Mirror Coat from carrying over
        pokemonPlayerOne.lastDamageTaken = 0
        pokemonPlayerTwo.lastDamageTaken = 0

        // IF switch selected
        // IF pursuit also selected

        // IF speed priority !=0 move selected (Quick Attack, Protect, Roar)
        if (playerOneIsFaster()) { // true = player1, false = player2
            RBYTurn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne)
            setLastMoveUsed(movePlayerOne)
            lastAttacker = pokemonPlayerOne
            RBYTurn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo)
            setLastMoveUsed(movePlayerTwo)
            lastAttacker = pokemonPlayerTwo
        } else {
            RBYTurn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo)
            setLastMoveUsed(movePlayerTwo)
            lastAttacker = pokemonPlayerTwo
            RBYTurn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne)
            setLastMoveUsed(movePlayerOne)
            lastAttacker = pokemonPlayerOne
        }

        // IF battle effects like Perish Song, Light Screen, Safeguard
        // thaw chance
        if (pokemonPlayerOne.nonVolatileStatus === Status.FREEZE) {
            rollFreezeThaw(pokemonPlayerOne)
        }
        if (pokemonPlayerTwo.nonVolatileStatus === Status.FREEZE) {
            rollFreezeThaw(pokemonPlayerTwo)
        }
    }

    private fun rollFreezeThaw(pokemon: RBYPokemon?) {
        val roll = ThreadLocalRandom.current().nextInt(256)
        if (roll < 25) {
            pokemon!!.removeNonVolatileStatus()
            statusChanged(pokemon, Status.FREEZE)
        }
    }

    private fun playerOneIsFaster(): Boolean {

        // TODO Quick Attack, Mach Punch, Roar etc. priority moves logic checking
        return if (pokemonPlayerOne!!.statSpe == pokemonPlayerTwo!!.statSpe) ThreadLocalRandom.current().nextBoolean() // random player to go first this turn
        else pokemonPlayerOne.statSpe ==
                pokemonPlayerOne.statSpe.coerceAtLeast(pokemonPlayerTwo.statSpe)
    }

    fun someoneFainted(): Boolean {
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
        get() = pokemonPlayerOne!!.moveOnePp == 0 && pokemonPlayerOne.moveTwoPp == 0 && pokemonPlayerOne.moveThreePp == 0 && pokemonPlayerOne.moveFourPp == 0
    val isStrugglePlayerTwo: Boolean
        get() = pokemonPlayerTwo!!.moveOnePp == 0 && pokemonPlayerTwo.moveTwoPp == 0 && pokemonPlayerTwo.moveThreePp == 0 && pokemonPlayerTwo.moveFourPp == 0
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