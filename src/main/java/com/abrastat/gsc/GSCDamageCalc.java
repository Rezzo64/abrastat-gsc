package com.abrastat.gsc;

import com.abrastat.general.Item;
import com.abrastat.general.Type;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static com.abrastat.gsc.GSCTypeEffectiveness.CalcEffectiveness;
import static com.abrastat.general.Item.*;
import static com.abrastat.general.Type.*;
import static java.util.Map.*;

public enum GSCDamageCalc {

    INSTANCE;

    private static Map<Item, Type> damageBoostingItems = Map.ofEntries(
            entry(BLACKGLASSES, DARK),
            entry(BLACK_BELT, FIGHTING),
            entry(CHARCOAL, FIRE),
            entry(DRAGON_SCALE, DRAGON),
            entry(HARD_STONE, ROCK),
            entry(MAGNET, ELECTRIC),
            entry(MIRACLE_SEED, GRASS),
            entry(MYSTIC_WATER, WATER),
            entry(NEVERMELTICE, ICE),
            entry(PINK_BOW, NORMAL),
            entry(POISON_BARB, POISON),
            entry(POLKADOT_BOW, NORMAL),
            entry(SHARP_BEAK, FLYING),
            entry(SILVERPOWDER, BUG),
            entry(SOFT_SAND, GROUND),
            entry(SPELL_TAG, GHOST),
            entry(TWISTEDSPOON, PSYCHIC),
            entry(METAL_COAT, STEEL)
    );

    private static double calcItemBoost(boolean itemBoostsDamage)    {
        if (itemBoostsDamage) {
            return 1.1;
        }
        else    {
            return 1.0;
        }
    }

    private static int critModifier() {
        if (critRoll() < 16) {
            return 2;
        } else {
            return 1;
        }
    }

    private static int critRoll()  {
        return ThreadLocalRandom.current().nextInt(256);
    }
    private static int damageRoll()    {
        return ThreadLocalRandom.current().nextInt(217, 256);
    }

    public static void calcDamage(
            @NotNull GSCPokemon defendingPokemon, @NotNull GSCPokemon attackingPokemon, @NotNull GSCMove attack) {

        int level = attackingPokemon.getLevel();
        int basePower = attack.getBasePower();
        int attackStat = attackingPokemon.getStatAtk();
        int defenseStat = defendingPokemon.getStatDef();
        Item heldItem = attackingPokemon.getHeldItem();

        // comparing item's boosting type against selected attack's type
        boolean doesItemBoostDamage = (damageBoostingItems.get(heldItem) == attack.getMoveType());

        // Modifiers for effects from Growl, Screech, Focus Energy, etc.
        int attackModifier, defenseModifier;

        int itemBoost = 1;

        double typeEffectiveness =
            CalcEffectiveness(attack.getMoveType(),
                    defendingPokemon.getTypes()[0],
                    defendingPokemon.getTypes()[1]);

        int damage = (int)
            ((Math.floor
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
                    * critModifier() * calcItemBoost(doesItemBoostDamage) + 2)
            * typeEffectiveness * damageRoll())
        ;

        defendingPokemon.setCurrentHP(damage);

    }

}
