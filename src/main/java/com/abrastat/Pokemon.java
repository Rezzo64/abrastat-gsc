package com.abrastat;

import javax.sound.sampled.Control;

public abstract class Pokemon {
    public static Species species;
    public static pokemonType type1, type2;
    public static int baseAtk, baseDef, baseSpA, baseSpD, baseSpe;

    private static class Species {

        // TODO: 19/08/2021 read & create getters + setters to read JSON

        public Species() {
        }

    }

    private static class pokemonType    {

        // TODO: 19/08/2021 read & create getters + setters to read JSON

        public pokemonType() {

        }
    }
}
