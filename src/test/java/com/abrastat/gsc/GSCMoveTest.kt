package com.abrastat.gsc

import com.abrastat.general.MoveEffect
import com.abrastat.general.Type
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class GSCMoveTest {
    @Test
    @DisplayName("Body Slam move test")
    fun bodySlamMoveTest() {
        Assertions.assertEquals("BODY SLAM", GSCMove.BODY_SLAM.toString())
    }

    @Test
    @DisplayName("Body Slam 'is attack' test")
    fun bodySlamIsAttackTest() {
        Assertions.assertTrue(GSCMove.BODY_SLAM.isAttack)
    }

    @Test
    @DisplayName("Body Slam type test")
    fun bodySlamTypeTest() {
        Assertions.assertEquals(Type.NORMAL, GSCMove.BODY_SLAM.type)
    }

    @Test
    @DisplayName("Body Slam base power test")
    fun bodySlamBasePowerTest() {
        Assertions.assertEquals(85, GSCMove.BODY_SLAM.basePower)
    }

    @Test
    @DisplayName("Body Slam PP test")
    fun bodySlamPPTest() {
        Assertions.assertEquals(24, GSCMove.BODY_SLAM.maxPp)
    }

    @Test
    @DisplayName("Body Slam accuracy test")
    fun bodySlamAccuracyTest() {
        Assertions.assertEquals(255, GSCMove.BODY_SLAM.accuracy)
    }

    @Test
    @DisplayName("Body Slam secondary effect test")
    fun bodySlamSecondaryEffectTest() {
        Assertions.assertEquals(MoveEffect.PRZ30, GSCMove.BODY_SLAM.effect)
    }

    @Test
    @DisplayName("Body Slam secondary effect chance test")
    fun bodySlamSecondaryEffectChanceTest() {
        Assertions.assertEquals(76, GSCMove.BODY_SLAM.effect.chance())
    }

    @Test
    @DisplayName("Body Slam unused target property test")
    fun bodySlamUnusedTargetPropertyTest() {
        Assertions.assertTrue(GSCMove.BODY_SLAM.isAttack)
    }

    @Test
    @DisplayName("Charm move test")
    fun charmMoveTest() {
        Assertions.assertEquals("CHARM", GSCMove.CHARM.toString())
    }

    @Test
    @DisplayName("Charm 'is attack' test")
    fun charmIsAttackTest() {
        Assertions.assertFalse(GSCMove.CHARM.isAttack)
    }

    @Test
    @DisplayName("Charm target test")
    fun charmTargetTest() {
        Assertions.assertEquals(MoveEffect.Target.OPPONENT, GSCMove.CHARM.effect.target())
    }

    @Test
    @DisplayName("Charm type test")
    fun charmTypeTest() {
        Assertions.assertEquals(Type.NORMAL, GSCMove.CHARM.type)
    }

    @Test
    @DisplayName("Charm PP test")
    fun charmPPTest() {
        Assertions.assertEquals(32, GSCMove.CHARM.maxPp)
    }

    @Test
    @DisplayName("Charm accuracy test")
    fun charmAccuracyTest() {
        Assertions.assertEquals(255, GSCMove.CHARM.accuracy)
    }

    @Test
    @DisplayName("Rest move test")
    fun restMoveTest() {
        Assertions.assertEquals("REST", GSCMove.REST.toString())
    }

    @Test
    @DisplayName("Rest 'is attack' test")
    fun restIsAttackTest() {
        Assertions.assertFalse(GSCMove.REST.isAttack)
    }

    @Test
    @DisplayName("Rest target test")
    fun restTargetTest() {
        Assertions.assertFalse(GSCMove.REST.isAttack)
    }

    @Test
    @DisplayName("Rest PP test")
    fun restPPTest() {
        Assertions.assertEquals(16, GSCMove.REST.maxPp)
    }

    @Test
    @DisplayName("Drill Peck move test")
    fun drillPeckMoveTest() {
        Assertions.assertEquals("DRILL PECK", GSCMove.DRILL_PECK.toString())
    }

    @Test
    @DisplayName("Drill Peck 'is attack' test")
    fun drillPeckIsAttackTest() {
        Assertions.assertTrue(GSCMove.DRILL_PECK.isAttack)
    }

    @Test
    @DisplayName("Drill Peck base power test")
    fun drillPeckBasePowerTest() {
        Assertions.assertEquals(80, GSCMove.DRILL_PECK.basePower)
    }

    @Test
    @DisplayName("Drill Peck PP test")
    fun drillPeckPPTest() {
        Assertions.assertEquals(32, GSCMove.DRILL_PECK.maxPp)
    }

    @Test
    @DisplayName("Drill Peck accuracy test")
    fun drillPeckAccuracyTest() {
        Assertions.assertEquals(255, GSCMove.DRILL_PECK.accuracy)
    }

    @Test
    @DisplayName("Drill Peck type test")
    fun drillPeckTypeTest() {
        Assertions.assertEquals(Type.FLYING, GSCMove.DRILL_PECK.type)
    }

    @Test
    @DisplayName("Drill Peck secondary effect test")
    fun drillPeckSecondaryEffectTest() {
        Assertions.assertEquals(MoveEffect.NONE, GSCMove.DRILL_PECK.effect)
    }

    @Test
    @DisplayName("Drill Peck secondary chance test")
    fun drillPeckSecondaryChanceTest() {
        Assertions.assertEquals(0, GSCMove.DRILL_PECK.effect.chance())
    }
}