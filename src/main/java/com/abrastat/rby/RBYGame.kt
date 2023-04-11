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
    // this program takes in two players and their strategies
    // to play a single instance of a game
    // both pokemon choose moves and take turns
    // until one has fallen until one team remains
    private var turnNumber = 0
    private var pokemonP1: RBYPokemon
    private var pokemonP2: RBYPokemon
    var winner = 0 // 0 = draw, 1 = p1, 2 = p2
        private set
    private var lastMoveUsed: RBYMove? = null
    private var lastAttacker: RBYPokemon? = null

    init {
        pokemonP1 = setup(player1, p1Behaviour)
        pokemonP2 = setup(player2, p2Behaviour)
    }

    private fun setup(player: RBYPlayer, behaviour: PlayerBehaviour): RBYPokemon {
        val pokemon = player.currentPokemon
        player.setCurrentBehaviour(behaviour)
        announceTeam(player)
        return pokemon
    }

    fun main(player1: RBYPlayer, player2: RBYPlayer) {
        turnNumber = 0
        // any player has no non-fainted pokemon remaining
        // while (!hasPokemon) {  // both players have pokemon left
        // if current pokemon is fainted switch
        // Messages.announceSwitch(player, pokemon);

        while (!someoneFainted()) {
            // test for infinite loops
            /*
            if (turnNumber > 1000) {
                winner = 0
                break
            }
            */

            // some moves are forced
            val moveP1 = player1.chooseMove(player2)
            val moveP2 = player2.chooseMove(player1)
            initTurn(moveP1, moveP2)
        }
        // }

        setWinner()
    }

    private fun someoneFainted(): Boolean {
        // Ditto vs Gengar
        if (pokemonP1.types.contains(Type.GHOST)
                && pokemonP2.types.contains(Type.GHOST)
                && pokemonP1.struggle
                && pokemonP2.struggle)
            return true
        fainted(pokemonP1)
        fainted(pokemonP2)
        return isPokemonFainted(pokemonP1, pokemonP2)
    }

    private fun fainted(pokemon: RBYPokemon) {
        if (pokemon.currentHP == 0
                || pokemon.nonVolatileStatus == Status.FREEZE) {
            if (pokemon.transformed)
                pokemon.unTransform()
            pokemon.resetStat(Stat.ATTACK)
            pokemon.resetStat(Stat.SPEED)
            pokemon.isFainted
            logFainted(pokemon)
        }
    }

    private fun initTurn(moveP1: RBYMove, moveP2: RBYMove) {
        turnNumber++
        announceTurn(turnNumber)
        displayCurrentHP(pokemonP1)
        displayCurrentHP(pokemonP2)

        // if (pokemonP1.multiTurn != RBYMove.EMPTY
        //         && move.effect != MoveEffect.BIND
        //         && move.effect != MoveEffect.BIDE)
        //     unable to switch
        // else
        //     if switch selected
        //         move is RBYMove.EMPTY

        if (pokemonP1.counter)
            counterable(pokemonP1, pokemonP2, moveP1, moveP2)
        if (pokemonP2.counter)
            counterable(pokemonP2, pokemonP1, moveP2, moveP1)

        if (pokemonP1IsFaster(moveP1.effect, moveP2.effect)) {  // true = player1, false = player2
            turn(pokemonP1, pokemonP2, moveP1)
            turn(pokemonP2, pokemonP1, moveP2)
        } else {
            turn(pokemonP2, pokemonP1, moveP2)
            turn(pokemonP1, pokemonP2, moveP1)
        }
        
        reset(pokemonP1)
        reset(pokemonP2)
    }

    // can't refactor to move effects
    // since we need to know pokemon move
    // TODO make current move a variable of RBYPokemon
    private fun counterable(pokemon1: RBYPokemon,
                            pokemon2: RBYPokemon,
                            move1: RBYMove,
                            move2: RBYMove) {
        if (move1.effect == MoveEffect.COUNTER) {
            if (move2.effect == MoveEffect.COUNTER) {
                pokemon1.counterable = false
                pokemon1.counterDamage = 0
                return
            }
            // still need to store counter damage in
            // case opponent did not make a move
            // if counter only looked at the current
            // turn, counter could use storedDamage
            if (move2 == RBYMove.EMPTY
                    || pokemon2.volatileStatus.contains(Status.HYPERBEAM)
                    || ((move2.effect == MoveEffect.INVULNERABLE
                    || move2.effect == MoveEffect.RAZORWIND)
                    && pokemon2.multiTurn.first == RBYMove.EMPTY))
                return
        }
        pokemon1.counterable = (move2.type == Type.NORMAL
                || move2.type == Type.FIGHTING)
        pokemon1.counterDamage = 0
    }

    private fun pokemonP1IsFaster(me1: MoveEffect, me2: MoveEffect): Boolean {
        val meq = MoveEffect.QUICKATTACK        // priority 1
        val mec = MoveEffect.COUNTER            // priority -1
                                                // else priority 0
        return if ((me1 == meq && me2 == meq)   // both QUICK_ATTACK
                || (me1 == mec && me2 == mec))  // both COUNTER
            pokemonP1IsFasterUtil()
        else if (me1 == meq) true
        else if (me2 == meq) false
        else if (me1 == mec) false
        else if (me2 == mec) true
        else pokemonP1IsFasterUtil()            // neither QUICK_ATTACK nor COUNTER
    }

    private fun pokemonP1IsFasterUtil(): Boolean {
        val speed1 = pokemonP1.modifiedStat(Stat.SPEED).coerceIn(1, 999)
        val speed2 = pokemonP2.modifiedStat(Stat.SPEED).coerceIn(1, 999)

        return if (speed1 == speed2)
            ThreadLocalRandom.current().nextBoolean()   // random player goes first
        else speed1 > speed2
    }

    private fun turn(poke1: RBYPokemon,
                     poke2: RBYPokemon,
                     move: RBYMove) {
        RBYTurn(poke1, poke2, move)
        setLastMoveUsed(move)
        lastAttacker = poke1
    }

    // prevents Flinch, Haze, Multi strike from carrying over
    private fun reset(pokemon: RBYPokemon) {
        pokemon.extraHit = 0
        pokemon.tookTurn = false
        if (pokemon.substituteBreak)
            pokemon.substituteBreak = false
    }

    private fun setWinner() {
        // this is technically correct,
        // but slower if each team is only one pokemon
        // arguments player1: RBYPlayer, player2: RBYPlayer
        /*
        val remainingPokemonP1 = player1.nonFaintedPokemon()
        val remainingPokemonP2 = player2.nonFaintedPokemon()

        winner = if (remainingPokemonP1.size == 0
                && remainingPokemonP2.size == 0) 0
        else if (remainingPokemonP1.size == 0) 2
        else if (remainingPokemonP2.size == 0) 1
        else 0
        */
        winner = if (pokemonP1.nonVolatileStatus == Status.FAINT
                && pokemonP2.nonVolatileStatus == Status.FAINT) 0
        else if (pokemonP1.nonVolatileStatus == Status.FAINT) 2
        else if (pokemonP2.nonVolatileStatus == Status.FAINT) 1
        else 0
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