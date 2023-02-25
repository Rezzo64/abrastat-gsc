package com.abrastat.general

import com.abrastat.gsc.GSCMove
import com.abrastat.gsc.GSCPokemon
import com.abrastat.rby.RBYMove
import com.abrastat.rby.RBYPokemon

interface Game {
    fun getLastMoveUsed(): Move?
    fun setLastMoveUsed(move: GSCMove?)
    fun setLastMoveUsedRBY(move: RBYMove?)

    companion object {
        @JvmStatic
        fun isPokemonFainted(attackingPokemon: GSCPokemon, defendingPokemon: GSCPokemon): Boolean {
            return attackingPokemon.currentHP == 0 || defendingPokemon.currentHP == 0
        }
        fun isPokemonFaintedRBY(attackingPokemon: RBYPokemon, defendingPokemon: RBYPokemon): Boolean {
            return attackingPokemon.currentHP == 0 || defendingPokemon.currentHP == 0
        }
    }
}