package com.abrastat.general

import com.abrastat.gsc.GSCPokemon
import com.abrastat.rby.RBYPokemon

interface Game {
    fun getLastMoveUsed(): Move?
    fun setLastMoveUsed(move: Move?)

    companion object {
        @JvmStatic
        fun isPokemonFainted(attackingPokemon: Pokemon, defendingPokemon: Pokemon): Boolean {
            return attackingPokemon.currentHP == 0 || defendingPokemon.currentHP == 0
        }
    }
}