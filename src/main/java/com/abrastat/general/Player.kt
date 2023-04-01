package com.abrastat.general

abstract class Player {
    var name: String? = null
    protected val team = arrayOfNulls<Pokemon>(6) // would ArrayList<Pokemon> work better?
    @JvmField
    protected var justUseFirstAttack = true
    @JvmField
    protected var activeBehaviours = HashSet<PlayerBehaviour>()

    abstract val currentPokemon: Pokemon?

    // [0] is always lead
    // [0] will always store the currently battling Pokemon
    fun addPokemon(pokemon: Pokemon) {
        var noRoom = true
        for (i in 0 until 6) {
            if (team[i] == null) {
                team[i] = pokemon
                noRoom = false
                break
            }
        }
        if (noRoom)
            throw IndexOutOfBoundsException(
                    "A team can only contain 6 pokemon"
            )
    }

    fun getPokemon(partyPosition: Int): Pokemon? {
        return team[partyPosition]
    }

    // TODO team having multiple pokemon
    /*
    fun swapCurrentPokemon() {}

    // keep for RBYGame.setWinner
    fun nonFaintedPokemon(): ArrayList<Pokemon> {
        val pokemons = ArrayList<Pokemon>()
        for (i in 0 until 6) {
            if (team[i] != null) {
                if (team[i]!!.nonVolatileStatus != Status.FAINT)
                    pokemons += team[i]!!
            }
        }
        return pokemons
    }
     */
    fun getActiveBehaviours(): HashSet<PlayerBehaviour> {
        if (justUseFirstAttack) {
            activeBehaviours.add(PlayerBehaviour.JUST_ATTACK)
        }
        return activeBehaviours
    }

    abstract fun setBehaviours()

    override fun toString(): String {
        return name!!
    }
}