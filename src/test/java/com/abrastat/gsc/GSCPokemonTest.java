package com.abrastat.gsc;

import static org.junit.jupiter.api.Assertions.*;

import com.abrastat.general.Pokemon;
import static com.abrastat.general.Type.*;
import com.abrastat.gsc.GSCPokemon;
import com.abrastat.gsc.GSCPokemon.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.json.*;

class GSCPokemonTest {

    Pokemon bulbasaur = new GSCPokemon("bulbasaur");

    @Test
    @DisplayName("Bulbasaur Species Name Test")
    void bulbasaurSpeciesTest()    {
        assertEquals("bulbasaur", bulbasaur.getSpecies());
    }

    @Test
    @DisplayName("Bulbasaur Base Stats Test")
    void bulbasaurBaseStatsTest()   {
        assertAll("each individual stat should pass",
            () -> assertEquals(45, bulbasaur.getHp()),
            () -> assertEquals(49, bulbasaur.getAttack()),
            () -> assertEquals(49, bulbasaur.getDefense()),
            () -> assertEquals(65, bulbasaur.getSpecial_attack()),
            () -> assertEquals(65, bulbasaur.getSpecial_defense()),
            () -> assertEquals(45, bulbasaur.getSpeed()));
    }

    @Test
    @DisplayName("Bulbasaur Height and Weight Test")
    void bulbasaurHeightAndWeightTest()  {
        assertAll("height and weight shouldn't change here",
                () -> assertEquals(0.7, bulbasaur.getHeight()),
                () -> assertEquals(6.9, bulbasaur.getWeight()));
    }

    @Test
    @DisplayName("Bulbasaur Typing Test")
    void bulbasaurTypingTest()  {
        assertAll("both types should exist, never null",
                () -> assertEquals(GRASS, bulbasaur.getTypes()[0]),
                () -> assertEquals(POISON, bulbasaur.getTypes()[1]));
    }
}