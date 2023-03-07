package com.abrastat.rby

import com.abrastat.general.MoveEffect
import com.abrastat.general.Type
import com.abrastat.rby.RBYMove
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class RBYMoveTest {
    @Test
    @DisplayName("Body Slam move test")
    fun bodySlamMoveTest() {
        Assertions.assertEquals("BODY SLAM", RBYMove.BODY_SLAM.toString())
    }

    @Test
    @DisplayName("Body Slam 'is attack' test")
    fun bodySlamIsAttackTest() {
        Assertions.assertTrue(RBYMove.BODY_SLAM.isAttack)
    }

    @Test
    @DisplayName("Body Slam type test")
    fun bodySlamTypeTest() {
        Assertions.assertEquals(Type.NORMAL, RBYMove.BODY_SLAM.type)
    }

    @Test
    @DisplayName("Body Slam base power test")
    fun bodySlamBasePowerTest() {
        Assertions.assertEquals(85, RBYMove.BODY_SLAM.basePower)
    }

    @Test
    @DisplayName("Body Slam PP test")
    fun bodySlamPPTest() {
        Assertions.assertEquals(24, RBYMove.BODY_SLAM.maxPp)
    }

    @Test
    @DisplayName("Body Slam accuracy test")
    fun bodySlamAccuracyTest() {
        Assertions.assertEquals(255, RBYMove.BODY_SLAM.accuracy)
    }

    @Test
    @DisplayName("Body Slam secondary effect test")
    fun bodySlamSecondaryEffectTest() {
        Assertions.assertEquals(MoveEffect.PRZ30, RBYMove.BODY_SLAM.effect)
    }

    @Test
    @DisplayName("Body Slam secondary effect chance test")
    fun bodySlamSecondaryEffectChanceTest() {
        Assertions.assertEquals(76, RBYMove.BODY_SLAM.effect.chance())
    }

    @Test
    @DisplayName("Body Slam unused target property test")
    fun bodySlamUnusedTargetPropertyTest() {
        Assertions.assertTrue(RBYMove.BODY_SLAM.isAttack)
    }

    @Test
    @DisplayName("GROWL move test")
    fun charmMoveTest() {
        Assertions.assertEquals("GROWL", RBYMove.GROWL.toString())
    }

    @Test
    @DisplayName("Growl 'is attack' test")
    fun charmIsAttackTest() {
        Assertions.assertFalse(RBYMove.GROWL.isAttack)
    }

    @Test
    @DisplayName("Growl target test")
    fun charmTargetTest() {
        Assertions.assertEquals(MoveEffect.Target.OPPONENT, RBYMove.GROWL.effect.target())
    }

    @Test
    @DisplayName("Charm type test")
    fun charmTypeTest() {
        Assertions.assertEquals(Type.NORMAL, RBYMove.GROWL.type)
    }

    @Test
    @DisplayName("Charm PP test")
    fun charmPPTest() {
        Assertions.assertEquals(32, RBYMove.GROWL.maxPp)
    }

    @Test
    @DisplayName("Charm accuracy test")
    fun charmAccuracyTest() {
        Assertions.assertEquals(255, RBYMove.GROWL.accuracy)
    }

    @Test
    @DisplayName("Rest move test")
    fun restMoveTest() {
        Assertions.assertEquals("REST", RBYMove.REST.toString())
    }

    @Test
    @DisplayName("Rest 'is attack' test")
    fun restIsAttackTest() {
        Assertions.assertFalse(RBYMove.REST.isAttack)
    }

    @Test
    @DisplayName("Rest target test")
    fun restTargetTest() {
        Assertions.assertFalse(RBYMove.REST.isAttack)
    }

    @Test
    @DisplayName("Rest PP test")
    fun restPPTest() {
        Assertions.assertEquals(16, RBYMove.REST.maxPp)
    }

    @Test
    @DisplayName("Drill Peck move test")
    fun drillPeckMoveTest() {
        Assertions.assertEquals("DRILL PECK", RBYMove.DRILL_PECK.toString())
    }

    @Test
    @DisplayName("Drill Peck 'is attack' test")
    fun drillPeckIsAttackTest() {
        Assertions.assertTrue(RBYMove.DRILL_PECK.isAttack)
    }

    @Test
    @DisplayName("Drill Peck base power test")
    fun drillPeckBasePowerTest() {
        Assertions.assertEquals(80, RBYMove.DRILL_PECK.basePower)
    }

    @Test
    @DisplayName("Drill Peck PP test")
    fun drillPeckPPTest() {
        Assertions.assertEquals(32, RBYMove.DRILL_PECK.maxPp)
    }

    @Test
    @DisplayName("Drill Peck accuracy test")
    fun drillPeckAccuracyTest() {
        Assertions.assertEquals(255, RBYMove.DRILL_PECK.accuracy)
    }

    @Test
    @DisplayName("Drill Peck type test")
    fun drillPeckTypeTest() {
        Assertions.assertEquals(Type.FLYING, RBYMove.DRILL_PECK.type)
    }

    @Test
    @DisplayName("Drill Peck secondary effect test")
    fun drillPeckSecondaryEffectTest() {
        Assertions.assertEquals(MoveEffect.NONE, RBYMove.DRILL_PECK.effect)
    }

    @Test
    @DisplayName("Drill Peck secondary chance test")
    fun drillPeckSecondaryChanceTest() {
        Assertions.assertEquals(0, RBYMove.DRILL_PECK.effect.chance())
    }
}