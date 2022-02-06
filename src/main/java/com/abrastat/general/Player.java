package com.abrastat.general;

import com.abrastat.gsc.GSCMove;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;

public abstract class Player {

    private String playerName;
    private Pokemon[] pokemonTeam = new Pokemon[6];
    protected boolean justUseFirstAttack = true;
    protected HashSet<PlayerBehaviour> behaviours;

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

    public HashSet<PlayerBehaviour> getBehaviours()    {
        return this.behaviours;
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

