package com.abrastat.gsc;

import com.abrastat.general.Game;
import static com.abrastat.general.Type.*;
import static com.abrastat.gsc.GSCTypeEffectiveness.CalcEffectiveness;

import com.abrastat.general.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

// Honestly just testing that values generally come out right.
// Not a complete test of all type coverage calculations!

class GSCTypeEffectivenessTest  {

    Game game = new GSCGame();

    @Test
    @DisplayName("damage successful test (carrying empty secondary typing)")
    void damageTest() {
        assertNotNull(CalcEffectiveness(NORMAL, ELECTRIC, NONE));
    }

    @ParameterizedTest
    @MethodSource("provideTypesForNormalDamageTest")
    @DisplayName("1* Damage Test")
    void normalDamageTest(Type attackingType, Type defendingType1, Type defendingType2) {
        assertEquals(1.0, CalcEffectiveness(attackingType, defendingType1, defendingType2));
    }

    private static Stream<Arguments> provideTypesForNormalDamageTest()  {
        return Stream.of(
                Arguments.of(NORMAL, NORMAL, NONE),
                Arguments.of(NORMAL, NONE, NORMAL),
                Arguments.of(NORMAL, NORMAL, FLYING),
                Arguments.of(FIGHTING, FIGHTING, FIRE),
                Arguments.of(WATER, BUG, NONE),
                Arguments.of(ROCK, STEEL, FLYING)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTypesForSETest")
    @DisplayName("2* Damage Test")
    void superEffectiveTest(Type attackingType, Type defendingType1, Type defendingType2) {
        assertEquals(2.0, CalcEffectiveness(attackingType, defendingType1, defendingType2));
    }

    private static Stream<Arguments> provideTypesForSETest()  {
        return Stream.of(
                Arguments.of(FIRE, GRASS, NONE),
                Arguments.of(ELECTRIC, NONE, WATER),
                Arguments.of(ICE, ROCK, FLYING),
                Arguments.of(FIGHTING, ROCK, DRAGON)
        );
    }

    @Test
    @DisplayName("4* Damage Test")
    void quadEffectiveTest()   {
        assertEquals(4.0, CalcEffectiveness(FIGHTING, STEEL, DARK));
    }

    @ParameterizedTest
    @MethodSource("provideTypesForNVETest")
    @DisplayName("0.5* Damage Test")
    void notVeryEffectiveTest(Type attackingType, Type defendingType1, Type defendingType2) {
        assertEquals(0.5, CalcEffectiveness(attackingType, defendingType1, defendingType2));
    }

    private static Stream<Arguments> provideTypesForNVETest()  {
        return Stream.of(
                Arguments.of(POISON, POISON, NONE),
                Arguments.of(DRAGON, NONE, STEEL),
                Arguments.of(BUG, ICE, FIGHTING),
                Arguments.of(DARK, STEEL, NORMAL)
        );
    }

    @Test
    @DisplayName("0.25* Damage Test")
    void quarterEffectiveTest() {
        assertEquals(0.25, CalcEffectiveness(PSYCHIC, PSYCHIC, STEEL));
    }

    @ParameterizedTest
    @MethodSource("provideTypesForZeroDmgTest")
    @DisplayName("Attack Immunity Test")
    void doesNotAffectTest(Type attackingType, Type defendingType1, Type defendingType2) {
        assertEquals(0.0, CalcEffectiveness(attackingType, defendingType1, defendingType2));
    }

    private static Stream<Arguments> provideTypesForZeroDmgTest()  {
        return Stream.of(
                Arguments.of(ELECTRIC, GROUND, NONE),
                Arguments.of(GHOST, NONE, NORMAL),
                Arguments.of(POISON, FIRE, STEEL),
                Arguments.of(FIGHTING, GHOST, POISON),
                Arguments.of(GHOST, PSYCHIC, NORMAL)
        );
    }

    @Test
    public void dummyTest() {
        int one = 1;
        assertEquals(1, one);
    }
}