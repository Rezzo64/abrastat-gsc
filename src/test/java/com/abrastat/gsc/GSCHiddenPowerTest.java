package com.abrastat.gsc;

import com.abrastat.general.Item;
import com.abrastat.general.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.abrastat.general.Type.*;
import static com.abrastat.gsc.GSCMove.HIDDEN_POWER;
import static org.junit.jupiter.api.Assertions.assertAll;

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
//
//    @Test
//    @DisplayName("Hidden Power DVs test")
//    void hiddenPowerDVsTest()   {
//        assertAll("checking all individual types against the resulting DVs",
//                () -> assertEquals());
//    }


}
