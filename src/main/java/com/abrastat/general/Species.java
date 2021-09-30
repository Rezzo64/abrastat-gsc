package com.abrastat.general;

import java.io.*;
import javax.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


abstract class Species {

    private static final Logger LOGGER = LoggerFactory.getLogger(Species.class);

    //Json mapper variables
    private String species;
    private String[] allowedAbilities;
    private int baseHp, baseAttack, baseDefense, baseSpecialAttack, baseSpecialDefense, baseSpeed;
    private double height, weight;
    private Type[] types = new Type[2];
    private double genderRatio;

    protected Species(String speciesName)  {

        String pokedex = "./src/main/resources/pokedex.json";
        JsonObject jsonObject = null;

        try {
            JsonReader reader = Json.createReader(new FileInputStream(pokedex));
            jsonObject = reader.readObject();

        } catch (FileNotFoundException e) {
            LOGGER.error("Error reading Pokedex file at: {}", pokedex);
        }

        JsonObject jsonPokemon = jsonObject.getJsonObject(speciesName);
        this.setSpecies(speciesName);

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
        this.species = speciesName.substring(0, 1).toUpperCase() + speciesName.substring(1).toLowerCase();
    }

    public String[] getAllowedAbilities() {
        return allowedAbilities;
    }

    public void setAllowedAbilities(JsonObject allowedAbilities) {
        this.allowedAbilities[0] = allowedAbilities.getValue("0").toString();
        this.allowedAbilities[1] = allowedAbilities.getValue("1").toString();
        this.allowedAbilities[2] = allowedAbilities.getValue("H").toString();
    }

    public void setBaseStats(JsonObject baseStats) {
        this.baseHp =               baseStats.getInt("hp");
        this.baseAttack =           baseStats.getInt("attack");
        this.baseDefense =          baseStats.getInt("defense");
        this.baseSpecialAttack =    baseStats.getInt("special_attack");
        this.baseSpecialDefense =   baseStats.getInt("special_defense");
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
        height = jsonHeight.getJsonNumber("height").doubleValue();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(JsonObject weight) {
        this.weight = weight.getJsonNumber("weight").doubleValue();
    }

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(JsonArray types) {
        final String type0 = types.getJsonString(0)
                .toString()
                .replace("\"", "")
                .toUpperCase();
        this.types[0] = Type.valueOf(type0);

        try {
            final String type1 = types.getJsonString(1)
                    .toString()
                    .replace("\"", "")
                    .toUpperCase();
            this.types[1] = Type.valueOf(type1);

        } catch (ArrayIndexOutOfBoundsException e)  {
            System.out.println("no secondary typing for " + getSpecies() + ".");
            this.types[1] = Type.NONE;
        }
    }

}