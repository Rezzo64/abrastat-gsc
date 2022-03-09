package com.abrastat.gsc;

import com.abrastat.general.*;

import static com.abrastat.general.PlayerBehaviour.*;
import org.jetbrains.annotations.NotNull;

import static com.abrastat.gsc.GSCMove.*;

public class GSCPlayer extends Player {

    private boolean hasSleepTalk;

    public GSCPlayer(Pokemon pokemon)  {
        super();
        addPokemon(pokemon);
        if (getCurrentPokemon().hasMove(SLEEP_TALK) > -1)    {
            hasSleepTalk = true;
        }
    }

    @Override
    public GSCPokemon getCurrentPokemon()  {
        return (GSCPokemon) this.getPokemon(0);
    }

    public GSCMove chooseMove(GSCPlayer opponent) {

        GSCMove chosenMove = EMPTY;

        if (justUseFirstAttack) {
            return this.getCurrentPokemon().getMoves()[0];
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

            GSCMove currentMove = this.getCurrentPokemon().getMoves()[i];

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


    // each behaviour should exist as its own entity depending on the simulation state

    public GSCMove chooseMoveHelper(@NotNull PlayerBehaviour behaviour, GSCPlayer opponent)    {

        // just choose the first move as default in case it's not clear what should be done
        GSCMove moveChosen = getCurrentPokemon().getMoves()[0];

        if (hasSleepTalk && notAboutToWake())   {
            return SLEEP_TALK;
        }

        switch (behaviour) {

            case JUST_ATTACK: // pre-processing damage calculations to choose the strongest attack
                moveChosen = getStrongestAttack(opponent.getCurrentPokemon());

            case RECOVER_RISKILY:

        }

        return moveChosen;
    }

    private boolean notAboutToWake() {
        if(getCurrentPokemon().getNonVolatileStatus() == Status.REST)   {
            return this.getCurrentPokemon().getSleepCounter() > 2;
        }

        if (getCurrentPokemon().getNonVolatileStatus() == Status.SLEEP) {
            return this.getCurrentPokemon().getSleepCounter() > 6;
        }

        return false;
    }

    public GSCMove getStrongestAttack(GSCPokemon defendingPokemon)    {
        GSCMove strongestAttack = EMPTY;
        int currentDamage = -1;
        int strongestDamage = 0;
        int emptyMove = 0;

        for (int i = 0; i < 4; i++) {

            if (this.getCurrentPokemon().getMovePp(i) < 1)   {
                emptyMove++;
                continue;
            }

            if (this.getCurrentPokemon().getMoves()[i].isAttack()) {
                currentDamage = GSCDamageCalc.calcDamageEstimate(
                        this.getCurrentPokemon(),
                        defendingPokemon,
                        this.getCurrentPokemon().getMoves()[i],
                        false);

                if (currentDamage > strongestDamage) {
                    strongestDamage = currentDamage;
                }

                // check if there's a move that has better accuracy and can KO in this range
                // TODO this only checks max damage roll. Implement something to assess individual damage rolls
                if (currentDamage == defendingPokemon.getCurrentHP()
                &&
                this.getCurrentPokemon().getMoves()[i].accuracy() > strongestAttack.accuracy())
                {
                    strongestAttack = this.getCurrentPokemon().getMoves()[i];
                }

            } else {
                strongestAttack = this.getCurrentPokemon().getMoves()[i];
            }

        }
        return (emptyMove == 4 ? STRUGGLE : strongestAttack); // only and always struggle when all moves are out of pp
    }
}
