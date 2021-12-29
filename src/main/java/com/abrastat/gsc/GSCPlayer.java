package com.abrastat.gsc;

import com.abrastat.general.Messages;
import com.abrastat.general.Move;
import com.abrastat.general.Player;
import com.abrastat.general.PlayerBehaviour;
import static com.abrastat.general.PlayerBehaviour.*;
import org.jetbrains.annotations.NotNull;

import static com.abrastat.gsc.GSCMove.*;

public class GSCPlayer extends Player {

    public GSCPlayer()  {
        super();
    }

    @Override
    public GSCPokemon getCurrentPokemon()  {
        return (GSCPokemon) this.getPokemon(0);
    }

    public GSCMove chooseMove(GSCPlayer opponent) {

        GSCMove chosenMove = EMPTY;

        if (justUseFirstAttack) {
            return (GSCMove) this.getCurrentPokemon().getMoves()[0];
        }

        for (PlayerBehaviour behaviour : behaviours)    {
            chosenMove = chooseMoveHelper(behaviour, opponent);
        }

        return chosenMove;

    }

    @Override
    public void setBehaviours() {

        if (getCurrentPokemon().getMoves().length == 1) {
            justUseFirstAttack = true;
            return;
        }

        // Firstly, disable mindlessly attacking with the strongest move
        justUseFirstAttack = false;

        // attribute each attack to a behaviour type
        for (int i = 0; i < getCurrentPokemon().getMoves().length; i++) {

            GSCMove currentMove = (GSCMove) getCurrentPokemon().getMoves()[i];

            switch (currentMove) {

                // attacks without notable special secondary effects
                case BEAT_UP:
                case DOUBLE_EDGE:
                case DRILL_PECK:
                case EARTHQUAKE:
                case FLAIL:
                case GIGA_DRAIN:
                case HIDDEN_POWER:
                case HYDRO_PUMP:
                case MEGAHORN:
                case NIGHT_SHADE:
                case RETURN:
                case REVERSAL:
                case ROLLOUT:
                case SEISMIC_TOSS:
                case SURF:
                    this.behaviours.add(JUST_ATTACK);
                    break;

                case MILK_DRINK:
                case RECOVER:
                case REST:
                case SOFTBOILED:
                    this.behaviours.add(RECOVER_SAFELY);
                    this.behaviours.add(RECOVER_RISKILY);
                    break;

                case CURSE:
                case GROWTH:
                case MEDITATE:
                case SHARPEN:
                    this.behaviours.add(SET_UP_SIX_TIMES);
                    this.behaviours.add(SET_UP_FIVE_TIMES);
                    this.behaviours.add(SET_UP_FOUR_TIMES);
                    this.behaviours.add(SET_UP_THRICE);
                    this.behaviours.add(SET_UP_TWICE);
                    this.behaviours.add(SET_UP_ONCE);
                    break;

                case ACID_ARMOR:
                case AMNESIA:
                case AGILITY:
                case BARRIER:
                case BELLY_DRUM:
                case SWORDS_DANCE:
                    this.behaviours.add(SET_UP_THRICE);
                    this.behaviours.add(SET_UP_TWICE);
                    this.behaviours.add(SET_UP_ONCE);
                    break;

                case HYPNOSIS:
                case LOVELY_KISS:
                case SING:
                case SLEEP_POWDER:
                case STUN_SPORE:
                case THUNDER_WAVE:
                case TOXIC:
                    this.behaviours.add(STATUS_OPPONENT_NON_VOLATILE);
                    break;

                case ATTRACT:
                case CONFUSE_RAY:
                case SWAGGER:
                    this.behaviours.add(STATUS_OPPONENT_VOLATILE);
                    break;

                case BODY_SLAM:
                case THUNDER:
                case THUNDERBOLT:
                case ZAP_CANNON:
                    this.behaviours.add(JUST_ATTACK);
                    this.behaviours.add(FISH_FOR_PARALYSIS);
                    break;

                case BLIZZARD:
                case ICE_BEAM:
                case ICE_PUNCH:
                    this.behaviours.add(JUST_ATTACK);
                    this.behaviours.add(FISH_FOR_FREEZE);
                    break;

                case FIRE_BLAST:
                case FIRE_PUNCH:
                case FLAMETHROWER:
                case SACRED_FIRE:
                    this.behaviours.add(JUST_ATTACK);
                    this.behaviours.add(FISH_FOR_BURN);
                    break;

                case SLUDGE_BOMB:
                    this.behaviours.add(JUST_ATTACK);
                    this.behaviours.add(FISH_FOR_POISON);
                    break;

                case CRUNCH:
                case PSYCHIC:
                    this.behaviours.add(JUST_ATTACK);
                    this.behaviours.add(FISH_FOR_SPDEF_DROP);
                    break;

                case CROSS_CHOP:
                    this.behaviours.add(JUST_ATTACK);
                    this.behaviours.add(FISH_FOR_CRIT);
                    break;

                case THIEF:
                    this.behaviours.add(JUST_ATTACK);
                    this.behaviours.add(STEAL_ITEM);
                    break;

                case HYPER_BEAM:
                    this.behaviours.add(GO_FOR_BIG_KO);
                    break;

                default:
                    Messages.logNoGSCMoveBehaviourFound(getCurrentPokemon(), currentMove);
                    break;
            }

        }
    }

    // Helper works in ascending order, that is, the lowest behaviour in the switch
    // will be the one who chooses which move to use

    public GSCMove chooseMoveHelper(@NotNull PlayerBehaviour behaviour, GSCPlayer opponent)    {

        GSCMove moveChosen = getCurrentPokemon().getMoves()[0];

        switch (behaviour) {

            case JUST_ATTACK: // pre-processing damage calculations to choose the strongest attack
                int strongestAttack = 0;

                // just choose the first move as default in case it's not clear what should be done

                for (GSCMove move : getCurrentPokemon().getMoves()) {
                    if (move.isAttack()) {
                        int dmg = GSCDamageCalc.calcDamageEstimate(
                                this.getCurrentPokemon(),
                                opponent.getCurrentPokemon(),
                                move,
                                false);

                        if (dmg > strongestAttack) {
                            moveChosen = move;
                        }
                    }
                }
        }

        return moveChosen;
    }
}
