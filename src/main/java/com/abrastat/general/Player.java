package com.abrastat.general;


public class Player {

    private String playerName;
    private Pokemon[] pokemonTeam = new Pokemon[6];
    private PlayerBehaviour behaviour;

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

    public Pokemon getCurrentPokemon()  {
        return this.pokemonTeam[0];
    }

    //TODO eventually...
    public void swapCurrentPokemon()    {

    }

    public Pokemon[] getCurrentTeam() {
        return this.pokemonTeam;
    }

    // eventually going to handle the logic sequence of selecting a preferred move.
    public Move chooseAttack() {
        return this.pokemonTeam[0].getMoves()[0];
    }
//    public void addPokemon(Pokemon pokemon, int partyPosition)  {
//        this.pokemonTeam[partyPosition] = pokemon;
//    }
//    public void addPokemon(Pokemon p1, Pokemon p2)  {
//        addPokemon(p1, 0);
//        addPokemon(p2, 1);
//    }

}