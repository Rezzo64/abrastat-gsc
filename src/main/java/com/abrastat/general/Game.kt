package com.abrastat.general

import com.abrastat.gsc.GSCMove
import com.abrastat.gsc.GSCPokemon

interface Game {
    fun getLastMoveUsed(): Move?
    fun setLastMoveUsed(move: GSCMove?)

    companion object {
        @JvmStatic
        fun isPokemonFainted(attackingPokemon: GSCPokemon, defendingPokemon: GSCPokemon): Boolean {
            return attackingPokemon.currentHP == 0 || defendingPokemon.currentHP == 0
        }
    }
}