package com.abrastat.gsc

import com.abrastat.general.Pokemon
import com.abrastat.general.Type
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class GSCHiddenPowerTest {
    private var abraFire: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.FIRE)
            .build()
    private var abraWater: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.WATER)
            .build()
    private var abraElectric: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.ELECTRIC)
            .build()
    private var abraGrass: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.GRASS)
            .build()
    private var abraIce: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.ICE)
            .build()
    private var abraFighting: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.FIGHTING)
            .build()
    private var abraPoison: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.POISON)
            .build()
    private var abraGround: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.GROUND)
            .build()
    private var abraFlying: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.FLYING)
            .build()
    private var abraPsychic: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.PSYCHIC)
            .build()
    private var abraBug: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.BUG)
            .build()
    private var abraRock: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.ROCK)
            .build()
    private var abraGhost: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.GHOST)
            .build()
    private var abraDragon: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.DRAGON)
            .build()
    private var abraSteel: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.STEEL)
            .build()
    private var abraDark: Pokemon = GSCPokemon.Builder("abra")
            .moves(GSCMove.HIDDEN_POWER)
            .hiddenPowerType(Type.DARK)
            .build()
    private var abras = arrayOf(abraDark, abraFire, abraWater, abraElectric, abraGrass, abraIce,
            abraFighting, abraPoison, abraGround, abraFlying, abraPsychic, abraBug, abraRock,
            abraGhost, abraDragon, abraSteel)

    @Test
    @DisplayName("Hidden Power Dark test")
    fun hiddenPowerDarkTest() {
        Assertions.assertAll("checking Hidden Power Dark DVs are correct",
                Executable { Assertions.assertEquals(15, abraDark.ivHP) },
                Executable { Assertions.assertEquals(15, abraDark.ivAtk) },
                Executable { Assertions.assertEquals(15, abraDark.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Fire test")
    fun hiddenPowerFireTest() {
        Assertions.assertAll("checking Hidden Power Fire DVs are correct",
                Executable { Assertions.assertEquals(3, abraFire.ivHP) },
                Executable { Assertions.assertEquals(14, abraFire.ivAtk) },
                Executable { Assertions.assertEquals(12, abraFire.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Water test")
    fun hiddenPowerWaterTest() {
        Assertions.assertAll("checking Hidden Power Water DVs are correct",
                Executable { Assertions.assertEquals(7, abraWater.ivHP) },
                Executable { Assertions.assertEquals(14, abraWater.ivAtk) },
                Executable { Assertions.assertEquals(13, abraWater.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Electric test")
    fun hiddenPowerElectricTest() {
        Assertions.assertAll("checking Hidden Power Electric DVs are correct",
                Executable { Assertions.assertEquals(7, abraElectric.ivHP) },
                Executable { Assertions.assertEquals(14, abraElectric.ivAtk) },
                Executable { Assertions.assertEquals(15, abraElectric.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Grass test")
    fun hiddenPowerGrassTest() {
        Assertions.assertAll("checking Hidden Power Grass DVs are correct",
                Executable { Assertions.assertEquals(3, abraGrass.ivHP) },
                Executable { Assertions.assertEquals(14, abraGrass.ivAtk) },
                Executable { Assertions.assertEquals(14, abraGrass.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Ice test")
    fun hiddenPowerIceTest() {
        Assertions.assertAll("checking Hidden Power Ice DVs are correct",
                Executable { Assertions.assertEquals(15, abraIce.ivHP) },
                Executable { Assertions.assertEquals(15, abraIce.ivAtk) },
                Executable { Assertions.assertEquals(13, abraIce.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Fighting test")
    fun hiddenPowerFightingTest() {
        Assertions.assertAll("checking Hidden Power Fighting DVs are correct",
                Executable { Assertions.assertEquals(3, abraFighting.ivHP) },
                Executable { Assertions.assertEquals(12, abraFighting.ivAtk) },
                Executable { Assertions.assertEquals(12, abraFighting.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Poison test")
    fun hiddenPowerPoisonTest() {
        Assertions.assertAll("checking Hidden Power Poison DVs are correct",
                Executable { Assertions.assertEquals(3, abraPoison.ivHP) },
                Executable { Assertions.assertEquals(12, abraPoison.ivAtk) },
                Executable { Assertions.assertEquals(14, abraPoison.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Ground test")
    fun hiddenPowerGroundTest() {
        Assertions.assertAll("checking Hidden Power Ground DVs are correct",
                Executable { Assertions.assertEquals(7, abraGround.ivHP) },
                Executable { Assertions.assertEquals(12, abraGround.ivAtk) },
                Executable { Assertions.assertEquals(15, abraGround.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Flying test")
    fun hiddenPowerFlyingTest() {
        Assertions.assertAll("checking Hidden Power Flying DVs are correct",
                Executable { Assertions.assertEquals(7, abraFlying.ivHP) },
                Executable { Assertions.assertEquals(12, abraFlying.ivAtk) },
                Executable { Assertions.assertEquals(13, abraFlying.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Psychic test")
    fun hiddenPowerPsychicTest() {
        Assertions.assertAll("checking Hidden Power Psychic DVs are correct",
                Executable { Assertions.assertEquals(11, abraPsychic.ivHP) },
                Executable { Assertions.assertEquals(15, abraPsychic.ivAtk) },
                Executable { Assertions.assertEquals(12, abraPsychic.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Bug test")
    fun hiddenPowerBugTest() {
        Assertions.assertAll("checking Hidden Power Bug DVs are correct",
                Executable { Assertions.assertEquals(15, abraBug.ivHP) },
                Executable { Assertions.assertEquals(13, abraBug.ivAtk) },
                Executable { Assertions.assertEquals(13, abraBug.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Rock test")
    fun hiddenPowerRockTest() {
        Assertions.assertAll("checking Hidden Power Rock DVs are correct",
                Executable { Assertions.assertEquals(11, abraRock.ivHP) },
                Executable { Assertions.assertEquals(13, abraRock.ivAtk) },
                Executable { Assertions.assertEquals(12, abraRock.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Ghost test")
    fun hiddenPowerGhostTest() {
        Assertions.assertAll("checking Hidden Power Ghost DVs are correct",
                Executable { Assertions.assertEquals(11, abraGhost.ivHP) },
                Executable { Assertions.assertEquals(13, abraGhost.ivAtk) },
                Executable { Assertions.assertEquals(14, abraGhost.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Dragon test")
    fun hiddenPowerDragonTest() {
        Assertions.assertAll("checking Hidden Power Dragon DVs are correct",
                Executable { Assertions.assertEquals(11, abraDragon.ivHP) },
                Executable { Assertions.assertEquals(15, abraDragon.ivAtk) },
                Executable { Assertions.assertEquals(14, abraDragon.ivDef) }
        )
    }

    @Test
    @DisplayName("Hidden Power Steel test")
    fun hiddenPowerSteelTest() {
        Assertions.assertAll("checking Hidden Power Steel DVs are correct",
                Executable { Assertions.assertEquals(15, abraSteel.ivHP) },
                Executable { Assertions.assertEquals(13, abraSteel.ivAtk) },
                Executable { Assertions.assertEquals(15, abraSteel.ivDef) }
        )
    }

    @AfterEach
    @Test
    @DisplayName("Testing Special and Speed stats are unchanged")
    fun otherStatsTest() {
        for (abra in abras) {
            Assertions.assertAll("checking SpA, SpD and Spe are 15",
                    Executable { Assertions.assertEquals(15, abra.ivSpA) },
                    Executable { Assertions.assertEquals(15, abra.ivSpD) },
                    Executable { Assertions.assertEquals(15, abra.ivSpe) }
            )
        }
    }
}