package com.abrastat.general;

import java.io.*;
import javax.json.*;

abstract class Species {

    //Json mapper variables
    private String species;
    private String[] abilities;
    private final int[] baseStats = new int[5];
    private int baseHp, baseAttack, baseDefense, baseSpecialAttack, baseSpecialDefense, baseSpeed;
    private double height, weight;
    private final Type[] types = new Type[2];
    private double genderRatio;

    protected Species(String speciesName)  {

        this.setSpecies(speciesName);
        String pokedex = "./src/main/resources/pokedex.json";
        JsonObject jsonObject = null;

        try {
            JsonReader reader = Json.createReader(new FileInputStream(pokedex));
            jsonObject = reader.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JsonObject jsonPokemon = jsonObject.getJsonObject(species);

        JsonObject jsonAbilities = jsonPokemon.getJsonObject("abilities");
        JsonObject jsonBaseStats = jsonPokemon.getJsonObject("baseStats");
        JsonArray jsonTypesArray = jsonPokemon.getJsonArray("types");

    //  this.setAbilities(jsonAbilities);
        this.setBaseStats(jsonBaseStats);
        this.setHeight(jsonPokemon);
        this.setWeight(jsonPokemon);
        this.setTypes(jsonTypesArray);


    }

    public String getSpecies()  {
        return this.species;
    }

    public void setSpecies(String speciesName)    {
        this.species = speciesName.toLowerCase();
    }

    public String[] getAbilities() {
        return abilities;
    }

    public void setAbilities(JsonObject abilities) {
        this.abilities[0] = abilities.getValue("0").toString();
        this.abilities[1] = abilities.getValue("1").toString();
        this.abilities[2] = abilities.getValue("H").toString();
    }

    public void setBaseStats(JsonObject baseStats) {
        this.baseHp =               baseStats.getInt("hp");
        this.baseAttack =           baseStats.getInt("attack");
        this.baseDefense =          baseStats.getInt("defense");
        this.baseSpecialAttack =   baseStats.getInt("special_attack");
        this.baseSpecialDefense =  baseStats.getInt("special_defense");
        this.baseSpeed =            baseStats.getInt("speed");
    }

    public int getBaseHp() {
        return baseHp;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getBaseSpecialAttack() {
        return baseSpecialAttack;
    }

    public int getBaseSpecialDefense() {
        return baseSpecialDefense;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(JsonObject jsonHeight) {
        height = jsonHeight.getInt("height");
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(JsonObject weight) {
        this.weight = weight.getInt("weight");
    }

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(JsonArray types) {
        final String type0 = types.getJsonString(0)
                .toString()
                .toUpperCase()
                .replace("\"", "");
        this.types[0] = Type.valueOf(type0);

        try {
            final String type1 = types.getJsonString(1)
                    .toString()
                    .toUpperCase()
                    .replace("\"", "");
            this.types[1] = Type.valueOf(type1);

        } catch (ArrayIndexOutOfBoundsException e)  {
            System.out.println("no secondary typing for " + getSpecies() + ".");
            this.types[1] = Type.NONE;
        }
    }

}