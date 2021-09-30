package com.abrastat.gsc;

import com.abrastat.general.Move;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.abrastat.general.Type.*;

import static org.junit.jupiter.api.Assertions.*;

class GSCMoveTest {

    private static final Move bodySlam   = new GSCMove("bodySlam");
    private static final Move charm      = new GSCMove("charm");
    private static final Move rest       = new GSCMove("rest");
    private static final Move drillPeck  = new GSCMove("drillPeck");

    @Test
    @DisplayName("Body Slam move test")
    void bodySlamMoveTest() {
        assertEquals("Body Slam", bodySlam.getMove());
    }

    @Test
    @DisplayName("Body Slam 'is attack' test")
    void bodySlamIsAttackTest() {
        assertTrue(bodySlam.isAttack);
    }

    @Test
    @DisplayName("Body Slam type test")
    void bodySlamTypeTest() {
        assertEquals(NORMAL, bodySlam.getMoveType());
    }

    @Test
    @DisplayName("Body Slam base power test")
    void bodySlamBasePowerTest()    {
        assertEquals(85, bodySlam.getBasePower());
    }

    @Test
    @DisplayName("Body Slam PP test")
    void bodySlamPPTest()   {
        assertEquals(24, bodySlam.getPP());
    }

    @Test
    @DisplayName("Body Slam accuracy test")
    void bodySlamAccuracyTest() {
        assertEquals(255, bodySlam.getAccuracy());
    }

    @Test
    @DisplayName("Body Slam secondary effect test")
    void bodySlamSecondaryEffectTest()  {
        assertNotEquals("none", bodySlam.getSecondaryEffect());
        assertEquals("prz", bodySlam.getSecondaryEffect());
    }

    @Test
    @DisplayName("Body Slam secondary effect chance test")
    void bodySlamSecondaryEffectChanceTest()    {
        assertEquals(76, bodySlam.getSecondaryChance());
    }

    @Test
    @DisplayName("Body Slam unused target property test")
    void bodySlamUnusedTargetPropertyTest()    {
        assertTrue(bodySlam.opponentIsTarget);
    }

    @Test
    @DisplayName("Charm move test")
    void charmMoveTest()    {
        assertEquals("Charm", charm.getMove());
    }

    @Test
    @DisplayName("Charm 'is attack' test")
    void charmIsAttackTest() {
        assertFalse(charm.isAttack);
    }

    @Test
    @DisplayName("Charm target test")
    void charmTargetTest()  {
        assertTrue(charm.opponentIsTarget);
    }

    @Test
    @DisplayName("Charm type test")
    void charmTypeTest()    {
        assertEquals(NONE, charm.getMoveType());
    }

    @Test
    @DisplayName("Charm PP test")
    void charmPPTest()  {
        assertEquals(32, charm.getPP());
    }

    @Test
    @DisplayName("Charm accuracy test")
    void charmAccuracyTest()    {
        assertEquals(255, charm.getAccuracy());
    }

    @ParameterizedTest
    @MethodSource("charmNullProperties")
    @DisplayName("Charm unused properties test")
    void charmUnusedPropertiesTest(Object t)    {
        assertTrue(t == Integer.valueOf(0) || t == null);
    }

    private static Stream<Arguments> charmNullProperties()  {
        return Stream.of(
                Arguments.of(charm.getBasePower()),
                Arguments.of(charm.getSecondaryChance()),
                Arguments.of(charm.getSecondaryEffect())
        );
    }


}