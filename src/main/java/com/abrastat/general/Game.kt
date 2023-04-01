package com.abrastat.general

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