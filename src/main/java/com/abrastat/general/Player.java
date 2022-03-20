package com.abrastat.general;

import java.util.HashSet;

import static com.abrastat.general.PlayerBehaviour.BehaviourGroup;

public abstract class Player {

    private String playerName;
    private Pokemon[] pokemonTeam = new Pokemon[6];
    protected boolean justUseFirstAttack = true;
    protected HashSet<PlayerBehaviour> activeBehaviours = new HashSet<>();


    public Player() {

    }

    public void setName(String name)    {
        this.playerName = name;
    }

    public String getName()   {
        return playerName;
    }

    // [0] is always lead
    // [0] will always store the currently battling Pokemon
    public void addPokemon(Pokemon pokemon) {
        this.pokemonTeam[0] = pokemon;
    }

    public Pokemon getPokemon(int partyPosition)    {
        return this.pokemonTeam[partyPosition];
    }

    public abstract Pokemon getCurrentPokemon();

    public HashSet<PlayerBehaviour> getActiveBehaviours()    {
        return this.activeBehaviours;
    }

    public abstract void setBehaviours();


    //TODO eventually...
    public void swapCurrentPokemon()    {

    }

    public Pokemon[] getCurrentTeam() {
        return this.pokemonTeam;
    }

//    public void addPokemon(Pokemon pokemon, int partyPosition)  {
//        this.pokemonTeam[partyPosition] = pokemon;
//    }
//    public void addPokemon(Pokemon p1, Pokemon p2)  {
//        addPokemon(p1, 0);
//        addPokemon(p2, 1);
//    }

    @Override
    public String toString()    {
        return this.playerName;
    }
}

