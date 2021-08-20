package com.abrastat.general;

public abstract class Pokemon {
    private static Species species;
    private static Gender gender;
    private static Type pokemonType1, pokemonType2;
    private static int baseHP, baseAtk, baseDef, baseSpA, baseSpD, baseSpe;
    private static int ivHP, ivAtk, ivDef, ivSpA, ivSpD, ivSpe;
    private static int level;

    private static class Species {

        // TODO: 19/08/2021 read & create getters + setters to read JSON

        public Species() {
        }

    }

    private static class Gender {

        // TODO: 19/08/2021 read & create getters + setters to read JSON
        private final Gender male = new Gender();
        private final Gender female = new Gender();
        private final Gender none = new Gender();
    }
}
