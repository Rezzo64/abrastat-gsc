package com.abrastat.gsc;

import static org.junit.jupiter.api.Assertions.*;

import com.abrastat.general.Pokemon;
import com.abrastat.general.Pokemon.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.File;

class GSCPokemonTest {

    private String pokedexLoc = "./src/main/resources/pokedex.json";
    File pokedexJson = new File(pokedexLoc);
    Pokemon[] mons = new GSCPokemon[250];
    String bulbasaurJson = "\"bulbasaur\": {\n" +
            "  \"abilities\": {\n" +
            "    \"0\": \"Overgrow\",\n" +
            "    \"H\": \"Chlorophyll\"\n" +
            "  },\n" +
            "  \"baseStats\": {\n" +
            "    \"hp\": 45,\n" +
            "    \"attack\": 49,\n" +
            "    \"defense\": 49,\n" +
            "    \"special_attack\": 65,\n" +
            "    \"special_defense\": 65,\n" +
            "    \"speed\": 45\n" +
            "  },\n" +
            "  \"height\": 0.7,\n" +
            "  \"species\": \"Bulbasaur\",\n" +
            "  \"types\": [\n" +
            "  \"grass\",\n" +
            "  \"poison\"\n" +
            "],\n" +
            "  \"weight\": 6.9,\n" +
            "  \"most_likely_ability\": \"Chlorophyll\"\n" +
            "}";

    @Before


    @Test
    @DisplayName("Bulbasaur Test")
    public void bulbasaurTest() {

        String[] abilities;
        int[] baseStats;
        double height;
        String species;
        String types;
        double weight;

        ObjectMapper mapper = new ObjectMapper();
        GSCPokemon testBulbasaur = mapper.readValue(bulbasaurJson, GSCPokemonTest.class);
        Pokemon bulbasaur = new GSCPokemon().getGSCPokemon();
        assertEquals(testBulbasaur, bulbasaur.toString());
    }

}