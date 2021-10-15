package com.abrastat.gsc;

import com.abrastat.general.Item;
import com.abrastat.general.Pokemon;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static com.abrastat.gsc.GSCTypeEffectiveness.CalcEffectiveness;
import static com.abrastat.general.Item.*;

public final class GSCDamageCalc {

    // singleton
    private GSCDamageCalc() {
    }

    private ArrayList<Item> items = new ArrayList<>();


    public void calcDamage(
            @NotNull GSCPokemon defendingPokemon, @NotNull GSCPokemon attackingPokemon, @NotNull GSCMove attack) {

        int level = attackingPokemon.getLevel();
        int basePower = attack.getBasePower();
        int attackStat = attackingPokemon.getStatAtk();
        int defenseStat = defendingPokemon.getStatDef();
        Item heldItem = attackingPokemon.getHeldItem();
        // Modifiers for effects from Growl, Screech, Focus Energy, etc.
        int attackModifier, defenseModifier;

        int itemBoost = 1;

        final double typeEffectiveness =
            CalcEffectiveness(attack.getMoveType(),
                    defendingPokemon.getTypes()[0],
                    defendingPokemon.getTypes()[1]);

        final int FORMULA = (int)
            (Math.floor
                    (Math.floor
                            (
                                    (
                                            (
                                                    Math.floor(
                                                            (level * 2)
                                                                    / 5)
                                                            + 2)
                                                    * basePower * attackStat)
                                            / defenseStat)
                            / 50)
                    * critModifier() * itemBoost + 2)
            * //modifiers
            1;
    }

    private void calcItemBoost(Pokemon attackingPokemon)    {
        attackingPokemon.getHeldItem().itemEffect();
    }

    private int critRoll = ThreadLocalRandom.current().nextInt(256);
    private int damageRoll = ThreadLocalRandom.current().nextInt(217, 256);

    private int critModifier() {
        if (critRoll < 16) {
            return 2;
        } else {
            return 1;
        }
    }

    private int damageModifier()    {
        return damageRoll;
    }

    private static ArrayList<Item> boostingItems = new ArrayList<>();
}
