package com.abrastat.general

class GameResult(
        private val pokemon1: Pokemon,
        private val pokemon2: Pokemon,
        private val winsP1: Int,
        private val winsP2: Int,
        private val draws: Int,
        private val avgTurns: Int,
        private val avgHpP1: Int,
        private val avgPpP1: IntArray,
        private val avgHpP2: Int,
        private val avgPpP2: IntArray,
        private val struggleP1: Int,
        private val struggleP2: Int,
        private val boomP1: Int,
        private val boomP2: Int
) {
    fun pokemon1(): Pokemon {
        return pokemon1
    }

    fun pokemon2(): Pokemon {
        return pokemon2
    }

    fun winsP1(): Int {
        return winsP1
    }

    fun winsP2(): Int {
        return winsP2
    }

    fun draws(): Int {
        return draws
    }

    fun avgTurns(): Int {
        return avgTurns
    }

    fun avgHpP1(): Int {
        return avgHpP1
    }

    fun avgHpP2(): Int {
        return avgHpP2
    }

    fun struggleP1(): Int {
        return struggleP1
    }

    fun struggleP2(): Int {
        return struggleP2
    }

    fun boomP1(): Int {
        return boomP1
    }

    fun boomP2(): Int {
        return boomP2
    }

    fun avgPpP1(): IntArray {
        return avgPpP1
    }

    fun avgPpP2(): IntArray {
        return avgPpP2
    }
}