package com.abrastat.general;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.json.*;

abstract class Species {

    private final String pokedexLoc = "./src/main/resources/pokedex.json";

    //Json mapper variables
    private String species;
    private String[] abilities;
    private final int[] baseStats = new int[5];
    private int hp, attack, defense, special_attack, special_defense, speed;
    private double height, weight;
    private final Type[] types = new Type[2];
    private double genderRatio;
    private JsonReader reader;
    private JsonObject jsonObject;

    protected Species(String speciesName)  {

        this.setSpecies(speciesName);

        try {
            reader = Json.createReader(new FileInputStream(pokedexLoc));
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

    public int[] getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(JsonObject baseStats) {
        this.hp =               baseStats.getInt("hp");
        this.attack =           baseStats.getInt("attack");
        this.defense =          baseStats.getInt("defense");
        this.special_attack =   baseStats.getInt("special_attack");
        this.special_defense =  baseStats.getInt("special_defense");
        this.speed =            baseStats.getInt("speed");
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpecial_attack() {
        return special_attack;
    }

    public void setSpecial_attack(int special_attack) {
        this.special_attack = special_attack;
    }

    public int getSpecial_defense() {
        return special_defense;
    }

    public void setSpecial_defense(int special_defense) {
        this.special_defense = special_defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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