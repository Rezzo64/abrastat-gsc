package com.abrastat.general;


public class Player {

    private Pokemon[] pokemonTeam = new Pokemon[6];

    // [0] is always lead
    // [0] will always store the currently battling Pokemon
    public void addPokemon(Pokemon pokemon) {
        this.pokemonTeam[0] = pokemon;
    }

    public Pokemon getCurrentPokemon()  {
        return this.pokemonTeam[0];
    }

    //TODO eventually...
    public void swapCurrentPokemon()    {

    }

//    public void addPokemon(Pokemon pokemon, int partyPosition)  {
//        this.pokemonTeam[partyPosition] = pokemon;
//    }
//    public void addPokemon(Pokemon p1, Pokemon p2)  {
//        addPokemon(p1, 0);
//        addPokemon(p2, 1);
//    }

}