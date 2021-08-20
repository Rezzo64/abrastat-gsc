package com.abrastat.general;

public class Pokemon {
    private static Species species;
    static int baseHP, baseAtk, baseDef, baseSpA, baseSpD, baseSpe;
    static int ivHP, ivAtk, ivDef, ivSpA, ivSpD, ivSpe;
    static int level;

    public Pokemon()    {

    }

    private static class Species extends Pokemon {

        static Gender gender;
        static Type pokemonType1, pokemonType2;

        // TODO: 19/08/2021 read & create getters + setters to read JSON

        public Species() {
        }

    }

    enum Gender {

        MALE, FEMALE, NONE;
        // TODO: 19/08/2021 read & create getters + setters to read JSON

    }
}
