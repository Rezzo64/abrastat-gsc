package com.abrastat.general;

public class Player {

    private Pokemon[] pokemonTeam = new Pokemon[5];

    // [0] is always lead
    public void AddPokemon(Pokemon pokemon) {
        this.pokemonTeam[0] = pokemon;
    }
}
