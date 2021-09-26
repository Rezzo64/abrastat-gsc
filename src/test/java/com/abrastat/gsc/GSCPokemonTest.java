package com.abrastat.gsc;

import static org.junit.jupiter.api.Assertions.*;

import com.abrastat.general.Pokemon;
import static com.abrastat.general.Type.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
            () -> assertEquals(45, bulbasaur.getBaseHp()),
            () -> assertEquals(49, bulbasaur.getBaseAttack()),
            () -> assertEquals(49, bulbasaur.getBaseDefense()),
            () -> assertEquals(65, bulbasaur.getBaseSpecialAttack()),
            () -> assertEquals(65, bulbasaur.getBaseSpecialDefense()),
            () -> assertEquals(45, bulbasaur.getBaseSpeed()));
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