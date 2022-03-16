package com.abrastat.gsc;

import com.abrastat.general.Messages;
import com.abrastat.general.MoveEffect;
import org.jetbrains.annotations.NotNull;

import static com.abrastat.gsc.GSCDamageCalc.calcHiddenPowerDamage;

public enum GSCStatusEffects {

    INSTANCE;


    public static void applyStatusEffects(
            GSCPokemon attackingPokemon,
            GSCPokemon defendingPokemon,
            @NotNull MoveEffect effect)  {
        switch (effect) {
            // Hidden Power is awkward, so we're treating it as a targeting status to bypass the usual checks
            case HIDDENPOWER:
                int damage = calcHiddenPowerDamage(attackingPokemon, defendingPokemon);
                defendingPokemon.applyDamage(damage);
                defendingPokemon.setLastDamageTaken(damage);
                Messages.logDamageTaken(defendingPokemon, damage);
                break;
        }
    }

}
