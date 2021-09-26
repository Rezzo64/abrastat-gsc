package com.abrastat.general;

public abstract class Pokemon extends Species {
    private static Species species;
    private Pokemon pokemon;
    private static Gender gender;
    private static int ivHP, ivAtk, ivDef, ivSpA, ivSpD, ivSpe;
    private static byte evHP, evAtk, evDef, evSpA, evSpD, evSpe;
    private static int level;

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Pokemon(String species)    {
        super(species);
    }

    enum Gender {

        MALE, FEMALE, NONE;
        // TODO: 19/08/2021 read & create getters + setters to read JSON

    }
}
