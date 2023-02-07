package com.abrastat.gsc

import com.abrastat.general.Pokemon
import com.abrastat.general.Type
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class GSCSpeciesTest {
    var bulbasaur: Pokemon = GSCPokemon.Builder("bulbasaur")
            .moves(GSCMove.BODY_SLAM)
            .build()

    @Test
    @DisplayName("Bulbasaur Species Name Test")
    fun bulbasaurSpeciesTest() {
        Assertions.assertEquals("Bulbasaur", bulbasaur.species)
    }

    @Test
    @DisplayName("Bulbasaur Base Stats Test")
    fun bulbasaurBaseStatsTest() {
        Assertions.assertAll("each individual stat should pass",
                Executable { Assertions.assertEquals(45, bulbasaur.baseHp) },
                Executable { Assertions.assertEquals(49, bulbasaur.baseAttack) },
                Executable { Assertions.assertEquals(49, bulbasaur.baseDefense) },
                Executable { Assertions.assertEquals(65, bulbasaur.baseSpecialAttack) },
                Executable { Assertions.assertEquals(65, bulbasaur.baseSpecialDefense) },
                Executable { Assertions.assertEquals(45, bulbasaur.baseSpeed) })
    }

    @Test
    @DisplayName("Bulbasaur Height and Weight Test")
    fun bulbasaurHeightAndWeightTest() {
        Assertions.assertAll("height and weight shouldn't change here",
                Executable { Assertions.assertEquals(0.7, bulbasaur.height) },
                Executable { Assertions.assertEquals(6.9, bulbasaur.weight) })
    }

    @Test
    @DisplayName("Bulbasaur Typing Test")
    fun bulbasaurTypingTest() {
        Assertions.assertAll("both types should exist, never null",
                Executable { Assertions.assertEquals(Type.GRASS, bulbasaur.types[0]) },
                Executable { Assertions.assertEquals(Type.POISON, bulbasaur.types[1]) })
    }
}