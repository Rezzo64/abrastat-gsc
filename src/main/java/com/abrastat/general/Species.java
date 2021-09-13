package com.abrastat.general;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class Species extends Pokemon {

    private Species species;
    int hp, attack, defense, special_attack, special_defense, speed;
    Type[] types = new Type[1];
    int genderRatio;

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

    public Species getSpecies() {
        return species;
    }

    //TODO test and modify this
    public void setSpecies(Species species) {
        final ObjectMapper mapper = new ObjectMapper();
        File pokedex = new File("./src/main/resources/pokedex.json");

        try {
            this.species = mapper.readValue(
                    pokedex, Species.class
            );

        } catch (DatabindException e)   {
            e.printStackTrace();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private Species() {}

}
