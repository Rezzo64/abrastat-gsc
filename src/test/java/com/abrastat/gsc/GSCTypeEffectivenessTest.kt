package com.abrastat.gsc

import com.abrastat.general.Type
import com.abrastat.gsc.GSCTypeEffectiveness.calcEffectiveness
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

// Honestly just testing that values generally come out right.
// Not a complete test of all type coverage calculations!
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GSCTypeEffectivenessTest {
    @Test
    @DisplayName("damage successful test (carrying empty secondary typing)")
    fun damageTest() {
        Assertions.assertNotNull(calcEffectiveness(Type.NORMAL, Type.ELECTRIC, Type.NONE))
    }

    @ParameterizedTest
    @MethodSource("provideTypesForNormalDamageTest")
    @DisplayName("1* Damage Test")
    fun normalDamageTest(attackingType: Type, defendingType1: Type, defendingType2: Type) {
        Assertions.assertEquals(1.0, calcEffectiveness(attackingType, defendingType1, defendingType2))
    }

    @ParameterizedTest
    @MethodSource("provideTypesForSETest")
    @DisplayName("2* Damage Test")
    fun superEffectiveTest(attackingType: Type, defendingType1: Type, defendingType2: Type) {
        Assertions.assertEquals(2.0, calcEffectiveness(attackingType, defendingType1, defendingType2))
    }

    @Test
    @DisplayName("4* Damage Test")
    fun quadEffectiveTest() {
        Assertions.assertEquals(4.0, calcEffectiveness(Type.FIGHTING, Type.STEEL, Type.DARK))
    }

    @ParameterizedTest
    @MethodSource("provideTypesForNVETest")
    @DisplayName("0.5* Damage Test")
    fun notVeryEffectiveTest(attackingType: Type, defendingType1: Type, defendingType2: Type) {
        Assertions.assertEquals(0.5, calcEffectiveness(attackingType, defendingType1, defendingType2))
    }

    @Test
    @DisplayName("0.25* Damage Test")
    fun quarterEffectiveTest() {
        Assertions.assertEquals(0.25, calcEffectiveness(Type.PSYCHIC, Type.PSYCHIC, Type.STEEL))
    }

    @ParameterizedTest
    @MethodSource("provideTypesForZeroDmgTest")
    @DisplayName("Attack Immunity Test")
    fun doesNotAffectTest(attackingType: Type?, defendingType1: Type?, defendingType2: Type?) {
        Assertions.assertEquals(0.0, calcEffectiveness(attackingType, defendingType1, defendingType2))
    }

    @Test
    fun dummyTest() {
        val one = 1
        Assertions.assertEquals(1, one)
    }
    private fun provideTypesForNormalDamageTest(): Stream<Arguments> {
        return Stream.of(
                Arguments.of(Type.NORMAL, Type.NORMAL, Type.NONE),
                Arguments.of(Type.BUG, Type.NONE, Type.ICE),
                Arguments.of(Type.FIRE, Type.NORMAL, Type.FLYING),
                Arguments.of(Type.FIGHTING, Type.FIGHTING, Type.FIRE),
                Arguments.of(Type.WATER, Type.BUG, Type.NONE),
                Arguments.of(Type.ROCK, Type.STEEL, Type.FLYING)
        )
    }

    private fun provideTypesForSETest(): Stream<Arguments> {
        return Stream.of(
                Arguments.of(Type.FIRE, Type.GRASS, Type.NONE),
                Arguments.of(Type.ELECTRIC, Type.NONE, Type.WATER),
                Arguments.of(Type.ICE, Type.ROCK, Type.FLYING),
                Arguments.of(Type.FIGHTING, Type.ROCK, Type.DRAGON)
        )
    }

    private fun provideTypesForNVETest(): Stream<Arguments> {
        return Stream.of(
                Arguments.of(Type.POISON, Type.POISON, Type.NONE),
                Arguments.of(Type.DRAGON, Type.NONE, Type.STEEL),
                Arguments.of(Type.BUG, Type.ICE, Type.FIGHTING),
                Arguments.of(Type.DARK, Type.STEEL, Type.NORMAL)
        )
    }

    private fun provideTypesForZeroDmgTest(): Stream<Arguments> {
        return Stream.of(
                Arguments.of(Type.ELECTRIC, Type.GROUND, Type.NONE),
                Arguments.of(Type.GHOST, Type.NONE, Type.NORMAL),
                Arguments.of(Type.POISON, Type.FIRE, Type.STEEL),
                Arguments.of(Type.FIGHTING, Type.GHOST, Type.POISON),
                Arguments.of(Type.GHOST, Type.PSYCHIC, Type.NORMAL)
        )
    }
}