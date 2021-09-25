package com.abrastat.gsc;

import static org.junit.jupiter.api.Assertions.*;

import com.abrastat.general.Pokemon;
import com.abrastat.general.Pokemon.*;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

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

    @Test
    @DisplayName("Bulbasaur Test")
    public void bulbasaurTest() {

        String[] abilities;
        int[] baseStats;
        double height;
        String species;
        String types;
        double weight;
        Pokemon testBulbasaur = new GSCPokemon();

        try {
            ObjectMapper mapper = new ObjectMapper();
            testBulbasaur = mapper.readValue(bulbasaurJson, GSCPokemon.class);
        } catch (DatabindException e)   {
            e.printStackTrace();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pokemon bulbasaur = new GSCPokemon().getGSCPokemon();
        assertEquals(testBulbasaur, bulbasaur);
    }

}