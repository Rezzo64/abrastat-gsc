package com.abrastat.general

abstract class Player {
    var name: String? = null
    val currentTeam = arrayOfNulls<Pokemon>(6)
    @JvmField
    protected var justUseFirstAttack = true
    @JvmField
    protected var activeBehaviours = HashSet<PlayerBehaviour>()

    // [0] is always lead
    // [0] will always store the currently battling Pokemon
    fun addPokemon(pokemon: Pokemon?) {
        currentTeam[0] = pokemon
    }

    fun getPokemon(partyPosition: Int): Pokemon? {
        return currentTeam[partyPosition]
    }

    //@JvmField
    abstract val currentPokemon: Pokemon?
    fun getActiveBehaviours(): HashSet<PlayerBehaviour> {
        if (justUseFirstAttack) {
            activeBehaviours.add(PlayerBehaviour.JUST_ATTACK)
        }
        return activeBehaviours
    }

    abstract fun setBehaviours()

    //TODO eventually...
    fun swapCurrentPokemon() {}

    //    public void addPokemon(Pokemon pokemon, int partyPosition)  {
    //        this.pokemonTeam[partyPosition] = pokemon;
    //    }
    //    public void addPokemon(Pokemon p1, Pokemon p2)  {
    //        addPokemon(p1, 0);
    //        addPokemon(p2, 1);
    //    }
    override fun toString(): String {
        return name!!
    }
}