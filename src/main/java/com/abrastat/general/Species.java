package com.abrastat.general;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

abstract class Species {

    private Species[] allSpecies;
    private String pokedexLoc = "./src/main/resources/pokedex.json";
    int[] baseStats = new int[5];
    int hp, attack, defense, special_attack, special_defense, speed;
    Type[] types = new Type[1];
    private String species;
    private double height;
    private double weight;
    int genderRatio;

    public int[] getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(int[] baseStats) {
        this.baseStats[0] = this.getHp();
        this.baseStats[1] = this.getAttack();
        this.baseStats[2] = this.getDefense();
        this.baseStats[3] = this.getSpecial_attack();
        this.baseStats[4] = this.getSpecial_defense();
        this.baseStats[5] = this.getSpecial_defense();
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

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(Type type0, Type type1) {
        this.types[0] = type0;
        this.types[1] = type1;
    }

    public Species getSpecies(int speciesNumber) {
        return allSpecies[speciesNumber];
    }

    //TODO test and modify this
    public void setSpecies() {
        final ObjectMapper mapper = new ObjectMapper();
        File pokedexJson = new File(pokedexLoc);

        try {
            allSpecies = mapper.readValue(
                    pokedexJson, Species[].class
                    );
            
        } catch (DatabindException e)   {
            e.printStackTrace();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    protected Species() {}

}
