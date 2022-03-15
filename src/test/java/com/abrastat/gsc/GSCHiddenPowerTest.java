package com.abrastat.gsc;

import com.abrastat.general.Item;
import com.abrastat.general.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.abrastat.general.Type.*;
import static com.abrastat.gsc.GSCMove.HIDDEN_POWER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GSCHiddenPowerTest {

    Pokemon abraFire = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(FIRE)
            .build();

    Pokemon abraWater = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(WATER)
            .build();

    Pokemon abraElectric = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(ELECTRIC)
            .build();

    Pokemon abraGrass = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(GRASS)
            .build();

    Pokemon abraIce = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(ICE)
            .build();

    Pokemon abraFighting = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(FIGHTING)
            .build();

    Pokemon abraPoison = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(POISON)
            .build();

    Pokemon abraGround = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(GROUND)
            .build();

    Pokemon abraFlying = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(FLYING)
            .build();

    Pokemon abraPsychic = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(PSYCHIC)
            .build();

    Pokemon abraBug = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(BUG)
            .build();

    Pokemon abraRock = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(ROCK)
            .build();

    Pokemon abraGhost = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(GHOST)
            .build();

    Pokemon abraDragon = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(DRAGON)
            .build();

    Pokemon abraSteel = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(STEEL)
            .build();

    Pokemon abraDark = new GSCPokemon.Builder("abra")
            .moves(HIDDEN_POWER)
            .hiddenPowerType(DARK)
            .build();

    @Test
    @DisplayName("Hidden Power Dark test")
    void hiddenPowerDarkTest()   {
        assertAll("checking Hidden Power Dark DVs are correct",
                () -> assertEquals(15, abraDark.getIvHP()),
                () -> assertEquals(15, abraDark.getIvAtk()),
                () -> assertEquals(15, abraDark.getIvDef())
                );
    }

    @Test
    @DisplayName("Hidden Power Fire test")
    void hiddenPowerFireTest()   {
        assertAll("checking Hidden Power Fire DVs are correct",
                () -> assertEquals(3, abraFire.getIvHP()),
                () -> assertEquals(14, abraFire.getIvAtk()),
                () -> assertEquals(12, abraFire.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Water test")
    void hiddenPowerWaterTest()   {
        assertAll("checking Hidden Power Water DVs are correct",
                () -> assertEquals(7, abraWater.getIvHP()),
                () -> assertEquals(14, abraWater.getIvAtk()),
                () -> assertEquals(13, abraWater.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Electric test")
    void hiddenPowerElectricTest()   {
        assertAll("checking Hidden Power Electric DVs are correct",
                () -> assertEquals(7, abraElectric.getIvHP()),
                () -> assertEquals(14, abraElectric.getIvAtk()),
                () -> assertEquals(15, abraElectric.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Grass test")
    void hiddenPowerGrassTest()   {
        assertAll("checking Hidden Power Grass DVs are correct",
                () -> assertEquals(3, abraGrass.getIvHP()),
                () -> assertEquals(14, abraGrass.getIvAtk()),
                () -> assertEquals(14, abraGrass.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Ice test")
    void hiddenPowerIceTest()   {
        assertAll("checking Hidden Power Ice DVs are correct",
                () -> assertEquals(15, abraIce.getIvHP()),
                () -> assertEquals(15, abraIce.getIvAtk()),
                () -> assertEquals(13, abraIce.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Fighting test")
    void hiddenPowerFightingTest()   {
        assertAll("checking Hidden Power Fighting DVs are correct",
                () -> assertEquals(3, abraFighting.getIvHP()),
                () -> assertEquals(12, abraFighting.getIvAtk()),
                () -> assertEquals(12, abraFighting.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Poison test")
    void hiddenPowerPoisonTest()   {
        assertAll("checking Hidden Power Poison DVs are correct",
                () -> assertEquals(3, abraPoison.getIvHP()),
                () -> assertEquals(12, abraPoison.getIvAtk()),
                () -> assertEquals(14, abraPoison.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Ground test")
    void hiddenPowerGroundTest()   {
        assertAll("checking Hidden Power Ground DVs are correct",
                () -> assertEquals(7, abraGround.getIvHP()),
                () -> assertEquals(12, abraGround.getIvAtk()),
                () -> assertEquals(15, abraGround.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Flying test")
    void hiddenPowerFlyingTest()   {
        assertAll("checking Hidden Power Flying DVs are correct",
                () -> assertEquals(7, abraFlying.getIvHP()),
                () -> assertEquals(12, abraFlying.getIvAtk()),
                () -> assertEquals(13, abraFlying.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Psychic test")
    void hiddenPowerPsychicTest()   {
        assertAll("checking Hidden Power Psychic DVs are correct",
                () -> assertEquals(11, abraPsychic.getIvHP()),
                () -> assertEquals(15, abraPsychic.getIvAtk()),
                () -> assertEquals(12, abraPsychic.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Bug test")
    void hiddenPowerBugTest()   {
        assertAll("checking Hidden Power Bug DVs are correct",
                () -> assertEquals(15, abraBug.getIvHP()),
                () -> assertEquals(13, abraBug.getIvAtk()),
                () -> assertEquals(13, abraBug.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Rock test")
    void hiddenPowerRockTest()   {
        assertAll("checking Hidden Power Rock DVs are correct",
                () -> assertEquals(11, abraRock.getIvHP()),
                () -> assertEquals(13, abraRock.getIvAtk()),
                () -> assertEquals(12, abraRock.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Ghost test")
    void hiddenPowerGhostTest()   {
        assertAll("checking Hidden Power Ghost DVs are correct",
                () -> assertEquals(11, abraGhost.getIvHP()),
                () -> assertEquals(13, abraGhost.getIvAtk()),
                () -> assertEquals(14, abraGhost.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Dragon test")
    void hiddenPowerDragonTest()   {
        assertAll("checking Hidden Power Dragon DVs are correct",
                () -> assertEquals(11, abraDragon.getIvHP()),
                () -> assertEquals(15, abraDragon.getIvAtk()),
                () -> assertEquals(14, abraDragon.getIvDef())
        );
    }

    @Test
    @DisplayName("Hidden Power Steel test")
    void hiddenPowerSteelTest()   {
        assertAll("checking Hidden Power Steel DVs are correct",
                () -> assertEquals(15, abraSteel.getIvHP()),
                () -> assertEquals(13, abraSteel.getIvAtk()),
                () -> assertEquals(15, abraSteel.getIvDef())
        );
    }
}
