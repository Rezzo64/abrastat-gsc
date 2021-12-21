package com.abrastat.gsc;

import com.abrastat.general.MoveEffect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.abrastat.general.Type.*;
import static com.abrastat.gsc.GSCMove.*;

import static org.junit.jupiter.api.Assertions.*;

class GSCMoveTest {

    @Test
    @DisplayName("Body Slam move test")
    void bodySlamMoveTest() {
        assertEquals("BODY SLAM", BODY_SLAM.toString());
    }

    @Test
    @DisplayName("Body Slam 'is attack' test")
    void bodySlamIsAttackTest() {
        assertTrue(BODY_SLAM.isAttack());
    }

    @Test
    @DisplayName("Body Slam type test")
    void bodySlamTypeTest() {
        assertEquals(NORMAL, BODY_SLAM.type());
    }

    @Test
    @DisplayName("Body Slam base power test")
    void bodySlamBasePowerTest()    {
        assertEquals(85, BODY_SLAM.basePower());
    }

    @Test
    @DisplayName("Body Slam PP test")
    void bodySlamPPTest()   {
        assertEquals(24, BODY_SLAM.pp());
    }

    @Test
    @DisplayName("Body Slam accuracy test")
    void bodySlamAccuracyTest() {
        assertEquals(255, BODY_SLAM.accuracy());
    }

    @Test
    @DisplayName("Body Slam secondary effect test")
    void bodySlamSecondaryEffectTest()  {
        assertEquals(MoveEffect.PRZ30, BODY_SLAM.effect());
    }

    @Test
    @DisplayName("Body Slam secondary effect chance test")
    void bodySlamSecondaryEffectChanceTest()    {
        assertEquals(76, BODY_SLAM.effect().chance());
    }

    @Test
    @DisplayName("Body Slam unused target property test")
    void bodySlamUnusedTargetPropertyTest()    {
        assertTrue(BODY_SLAM.isAttack());
    }

    @Test
    @DisplayName("Charm move test")
    void charmMoveTest()    {
        assertEquals("CHARM", CHARM.toString());
    }

    @Test
    @DisplayName("Charm 'is attack' test")
    void charmIsAttackTest() {
        assertFalse(CHARM.isAttack());
    }

    @Test
    @DisplayName("Charm target test")
    void charmTargetTest()  {
        assertEquals(MoveEffect.Target.OPPONENT, CHARM.effect().target());
    }

    @Test
    @DisplayName("Charm type test")
    void charmTypeTest()    {
        assertEquals(NORMAL, CHARM.type());
    }

    @Test
    @DisplayName("Charm PP test")
    void charmPPTest()  {
        assertEquals(32, CHARM.pp());
    }

    @Test
    @DisplayName("Charm accuracy test")
    void charmAccuracyTest()    {
        assertEquals(255, CHARM.accuracy());
    }

    @Test
    @DisplayName("Rest move test")
    void restMoveTest() {
        assertEquals("REST", REST.toString());
    }

    @Test
    @DisplayName("Rest 'is attack' test")
    void restIsAttackTest() {
        assertFalse(REST.isAttack());
    }

    @Test
    @DisplayName("Rest target test")
    void restTargetTest()   {
        assertFalse(REST.isAttack());
    }

    @Test
    @DisplayName("Rest PP test")
    void restPPTest()   {
        assertEquals(16, REST.pp());
    }

    @Test
    @DisplayName("Drill Peck move test")
    void drillPeckMoveTest()    {
        assertEquals("DRILL PECK", DRILL_PECK.toString());
    }

    @Test
    @DisplayName("Drill Peck 'is attack' test")
    void drillPeckIsAttackTest()    {
        assertTrue(DRILL_PECK.isAttack());
    }

    @Test
    @DisplayName("Drill Peck base power test")
    void drillPeckBasePowerTest()   {
        assertEquals(80, DRILL_PECK.basePower());
    }

    @Test
    @DisplayName("Drill Peck PP test")
    void drillPeckPPTest()  {
        assertEquals(32, DRILL_PECK.pp());
    }

    @Test
    @DisplayName("Drill Peck accuracy test")
    void drillPeckAccuracyTest()    {
        assertEquals(255, DRILL_PECK.accuracy());
    }

    @Test
    @DisplayName("Drill Peck type test")
    void drillPeckTypeTest()    {
        assertEquals(FLYING, DRILL_PECK.type());
    }

    @Test
    @DisplayName("Drill Peck secondary effect test")
    void drillPeckSecondaryEffectTest()   {
        assertEquals(MoveEffect.NONE, DRILL_PECK.effect());
    }

    @Test
    @DisplayName("Drill Peck secondary chance test")
    void drillPeckSecondaryChanceTest()   {
        assertEquals(0, DRILL_PECK.effect().chance());
    }

}