package com.abrastat.general;

import com.fasterxml.jackson.databind.*;

import java.io.InputStream;

public abstract class Pokemon extends Species {
    private static Species species;
    private Pokemon pokemon;
    static Gender gender;
    static int ivHP, ivAtk, ivDef, ivSpA, ivSpD, ivSpe;
    static byte evHP, evAtk, evDef, evSpA, evSpD, evSpe;
    static int level;

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Pokemon()    {
        super();
        this.setSpecies();
    }

    enum Gender {

        MALE, FEMALE, NONE;
        // TODO: 19/08/2021 read & create getters + setters to read JSON

    }
}
