package com.abrastat.gsc;

import com.abrastat.general.*;
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

    private static boolean sameTypeAttackBonus(@NotNull Pokemon pokemon, @NotNull Move move) {

        Type moveType = move.type();
        if (pokemon.getTypes()[0] == moveType || pokemon.getTypes()[1] == moveType) {
            return true;
        } else {
            return false;
        }
    }

    private static double calcItemBoost(boolean itemBoostsDamage)    {
        if (itemBoostsDamage) {
            return 1.1;
        }
        else    {
            return 1;
        }
    }

    private static int critModifier() {
        if (critRoll() < 16) {
            Messages.logCriticalHit();
            return 2;
        } else {
            return 1;
        }
    }

    private static int critRoll()  {
        return ThreadLocalRandom.current().nextInt(256);
    }
    private static int damageRoll()    {
        return (ThreadLocalRandom.current().nextInt(217, 256));
    }

    public static void calcDamage(
            @NotNull GSCPokemon attackingPokemon, @NotNull GSCPokemon defendingPokemon, @NotNull GSCMove attack) {

        int level = attackingPokemon.getLevel();
        int basePower = attack.basePower();
        int attackStat, defenseStat;

        attackStat = attack.isPhysical() ? attackingPokemon.getStatAtk() : attackingPokemon.getStatSpA();
        defenseStat = attack.isPhysical() ? defendingPokemon.getStatDef() : defendingPokemon.getStatSpD();

        if (attackStat > 255 || defenseStat > 255)  {
            attackStat /= 4;
            defenseStat /= 4;
        }

        Item heldItem = attackingPokemon.getHeldItem();

        boolean doesItemBoostDamage;
        // comparing item's boosting type against selected attack's type
        if (heldItem != null && damageBoostingItems.get(heldItem) != null) {
            doesItemBoostDamage = (damageBoostingItems.get(heldItem) == attack.type());
        } else {
            doesItemBoostDamage = false;
        }

        // Modifiers for effects from Growl, Screech, Focus Energy, etc.
        int attackModifier, defenseModifier;

        double typeEffectiveness =
            CalcEffectiveness(attack.type(),
                    defendingPokemon.getTypes()[0],
                    defendingPokemon.getTypes()[1]);

        var damage = (int)
            ((Math.floor(Math.floor(((Math.floor((level * 2) / 5) + 2) * Math.max(1, attackStat) * basePower) / Math.max(1, defenseStat)) / 50) * critModifier() * calcItemBoost(doesItemBoostDamage) + 2) * typeEffectiveness);

        if (sameTypeAttackBonus(attackingPokemon, attack))  {
            damage = (int) Math.floor(damage * 1.5);
        }

        if (typeEffectiveness != 1) {

            // nve will be 0 < typeEffectiveness < 1, so multiply to get around this
            // dirty fix and not optimised, come back and change if performance needs it

            typeEffectiveness *= 10;
            Messages.logTypeEffectiveness((int) typeEffectiveness);
        }

        damage *= damageRoll();
        damage = (int) Math.floor(damage / 255);

        damage = Math.min(damage, defendingPokemon.getCurrentHP());

        defendingPokemon.applyDamage(damage);
        defendingPokemon.setLastDamageTaken(damage);
        Messages.logDamageTaken(defendingPokemon, damage);
    }

    // This is used for preprocessing - it always assumes max damage roll
    public static int calcDamageEstimate(GSCPokemon attackingPokemon, GSCPokemon defendingPokemon, GSCMove attack)  {

    }
}
