package com.abrastat.gsc;

import com.abrastat.general.Messages;
import com.abrastat.general.MoveEffect;
import com.abrastat.general.Stat;
import com.abrastat.general.Type;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

import static com.abrastat.general.Status.*;

import static com.abrastat.gsc.GSCDamageCalc.calcHiddenPowerDamage;

public enum GSCStatusEffects {

    INSTANCE;

    public static void applyStatusEffects(
            GSCPokemon attackingPokemon,
            GSCPokemon defendingPokemon,
            @NotNull MoveEffect effect)  {
        switch (effect) {

            // Hidden Power is awkward, so we're treating it as a targeting status to bypass the calc's method signature
            case HIDDENPOWER:
                int damage = calcHiddenPowerDamage(attackingPokemon, defendingPokemon);
                defendingPokemon.applyDamage(damage);
                defendingPokemon.setLastDamageTaken(damage);
                Messages.logDamageTaken(defendingPokemon, damage);
                break;

            case REST:
                if (attackingPokemon.getCurrentHP() < attackingPokemon.getStatHP()) {
                    Messages.logRest(attackingPokemon);
                    attackingPokemon.applyHeal(attackingPokemon.getStatHP());
                    attackingPokemon.applyNonVolatileStatus(SLEEP);
                    attackingPokemon.setSleepCounter(2, 2);
                } else {
                    Messages.cantRestFullHp(attackingPokemon);
                }
                break;

            case SLEEPTALK:
                if (attackingPokemon.getMoves()[3] != GSCMove.SLEEP_TALK) {
                    throw new IllegalArgumentException("Sleep Talk called, but not found in " +
                            attackingPokemon + "'s moveset.");
                }
                int randomMove = ThreadLocalRandom.current().nextInt(0, 4);
                Messages.logAttack(attackingPokemon, attackingPokemon.getMoves()[randomMove]);
                GSCTurn.doAttack(attackingPokemon,
                        defendingPokemon,
                        attackingPokemon.getMoves()[randomMove]
                );
                break;

            case CURSE:
                if (attackingPokemon.getTypes()[0] == Type.GHOST || attackingPokemon.getTypes()[1] == Type.GHOST) {

                } else {
                    attackingPokemon.dropStat(Stat.SPEED, 1);
                    attackingPokemon.raiseStat(Stat.ATTACK, 1);
                    attackingPokemon.raiseStat(Stat.DEFENSE, 1);
                }
                break;
        }
    }

}
