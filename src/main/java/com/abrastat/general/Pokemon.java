package com.abrastat.general;

public class Pokemon {
    private static Species species;
    static Gender gender;
    static int ivHP, ivAtk, ivDef, ivSpA, ivSpD, ivSpe;
    static byte evHP, evAtk, evDef, evSpA, evSpD, evSpe;
    static int level;

    public Pokemon()    {

    }

    private static class Species extends Pokemon {

        static int baseHP, baseAtk, baseDef, baseSpA, baseSpD, baseSpe;
        static Type pokemonType1, pokemonType2;
        static int genderRatio;


        // TODO: 19/08/2021 read & create getters + setters to read JSON

        public Species() {
        }

    }

    enum Gender {

        MALE, FEMALE, NONE;
        // TODO: 19/08/2021 read & create getters + setters to read JSON

    }
}
