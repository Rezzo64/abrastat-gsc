package com.abrastat.general;


public class Pokemon {
    private static Species species;
    static Gender gender;
    static int ivHP, ivAtk, ivDef, ivSpA, ivSpD, ivSpe;
    static byte evHP, evAtk, evDef, evSpA, evSpD, evSpe;
    static int level;

    public Pokemon()    {

    }

    private static final class Species extends Pokemon {

        int baseHP, baseAtk, baseDef, baseSpA, baseSpD, baseSpe;
        Type pokemonType1, pokemonType2;
        int genderRatio;


        // TODO: 19/08/2021 read & create getters + setters to read JSON

        private Species() {}

    }

    enum Gender {

        MALE, FEMALE, NONE;
        // TODO: 19/08/2021 read & create getters + setters to read JSON

    }
}
