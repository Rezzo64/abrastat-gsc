package com.abrastat.gsc;

import com.abrastat.general.*;

import static com.abrastat.general.PlayerBehaviour.BehaviourGroup.*;
import org.jetbrains.annotations.NotNull;


import java.util.Arrays;
import java.util.HashSet;

import static com.abrastat.gsc.GSCMove.*;

public class GSCPlayer extends Player {

    private final int hasSleepTalk;
    private PlayerBehaviour currentBehaviour;

    public GSCPlayer(Pokemon pokemon)  {
        super();
        addPokemon(pokemon);
        hasSleepTalk = this.getCurrentPokemon().hasMove(SLEEP_TALK);
        this.setBehaviours();
    }

    @Override
    public GSCPokemon getCurrentPokemon()  {
        return (GSCPokemon) this.getPokemon(0);
    }

    public GSCMove chooseMove(GSCPlayer opponent) {

        GSCMove chosenMove = EMPTY;

        if (justUseFirstAttack) { // ignores other moves, use with caution
            if (this.getCurrentPokemon().getMovePp(0) > 0) {
                return this.getCurrentPokemon().getMoves()[0];
            } else {
                return STRUGGLE;
            }
        }

        chosenMove = chooseMoveHelper(opponent);
        return chosenMove;

    }

    // each behaviour should exist as its own entity depending on the simulation state
    public GSCMove chooseMoveHelper(GSCPlayer opponent)    {

        // just choose the first move as default in case it's not clear what should be done
        GSCMove moveChosen = getCurrentPokemon().getMoves()[0];

        if (hasSleepTalk > -1 && notAboutToWake())   {

            // check if any Sleep Talk PP remains
            if (this.getCurrentPokemon().getMovePp(this.getCurrentPokemon().hasMove(SLEEP_TALK)) > 0) {
                return SLEEP_TALK;
            }
        }

        switch (this.currentBehaviour) {
            case JUST_ATTACK:
                // pre-processing damage calculations to choose the strongest attack
                moveChosen = getStrongestAttack(opponent.getCurrentPokemon());
                break;

            case RECOVER_SAFELY:
                if (!this.opponentCritMayKO(opponent)) {

                } else {
                    moveChosen = this.chooseRecoveryMove();
                }
                break;

            case RECOVER_RISKILY:
                if (!this.opponentMayKO(opponent)) {

                } else {
                    moveChosen = this.chooseRecoveryMove();
                }
                break;

            case FISH_FOR_PARALYSIS:
                if (opponent.getCurrentPokemon().getNonVolatileStatus() == Status.HEALTHY) {

                }


        }
        return moveChosen;
    }

    protected void setCurrentBehaviour(PlayerBehaviour behaviour) {
        Messages.logCurrentBehaviour(this, behaviour);
        this.currentBehaviour = behaviour;
    }

    @Override
    public void setBehaviours() {

        if (getCurrentPokemon().getMoves().length == 1) {
            justUseFirstAttack = true;
            return;
        }

        // Firstly, disable mindlessly attacking with the strongest move
        justUseFirstAttack = false;

        final HashSet<PlayerBehaviour.BehaviourGroup> behaviourGroups = new HashSet<>();

        // attribute each attack to a behaviour type & collect each behaviour group
        for (GSCMove move : this.getCurrentPokemon().getMoves()) {

            switch (move) {

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
                    behaviourGroups.add(ATTACK);
                    break;

                case MILK_DRINK:
                case RECOVER:
                case REST:
                case SOFTBOILED:
                case SELFDESTRUCT:
                case EXPLOSION:
                case DESTINY_BOND:
                    behaviourGroups.add(KO_RESPONSE);
                    break;

                case CURSE:
                case GROWTH:
                case MEDITATE:
                case SHARPEN:
                    behaviourGroups.add(SET_UP);
                    break;

                case ACID_ARMOR:
                case AMNESIA:
                case AGILITY:
                case BARRIER:
                case SWORDS_DANCE:
                    behaviourGroups.add(SET_UP_SHARP);
                    break;

                case BELLY_DRUM:
                    behaviourGroups.add(BELLY);
                    break;

                case HYPNOSIS:
                case LOVELY_KISS:
                case SING:
                case SLEEP_POWDER:
                case STUN_SPORE:
                case THUNDER_WAVE:
                    behaviourGroups.add(STATUS_OPPONENT_NON_VOLATILE);
                    break;

                case ATTRACT:
                case CONFUSE_RAY:
                case SWAGGER:
                    behaviourGroups.add(VOLATILES);
                    break;

                case BODY_SLAM:
                case THUNDER:
                case THUNDERBOLT:
                case ZAP_CANNON:

                case FIRE_BLAST:
                case FIRE_PUNCH:
                case FLAMETHROWER:
                case SACRED_FIRE:

                case BLIZZARD:
                case ICE_BEAM:
                case ICE_PUNCH:

                case SLUDGE_BOMB:
                    behaviourGroups.add(ATTACK);
                    behaviourGroups.add(STATUS_FISH);
                    break;

                case CRUNCH:
                case PSYCHIC:
                    behaviourGroups.add(ATTACK);
                    behaviourGroups.add(STAT_RAISE_DROP_FISH);
                    break;

                case CROSS_CHOP:
                    behaviourGroups.add(ATTACK);
                    behaviourGroups.add(FISH_CRIT);
                    break;

                case THIEF:
                    behaviourGroups.add(ATTACK);
                    behaviourGroups.add(STEAL);
                    break;

                case HYPER_BEAM:
                    behaviourGroups.add(GO_FOR_HYPER_BEAM);
                    behaviourGroups.add(KO_RESPONSE);
                    break;

                default:
                    Messages.logNoGSCMoveBehaviourFound(getCurrentPokemon(), move);
                    break;
            }
        }
        // add all discovered behaviours to Player for utilisation
        for (PlayerBehaviour.BehaviourGroup group : behaviourGroups) {
            activeBehaviours.addAll(Arrays.asList(group.getBehaviours()));
        }
    }

    private boolean notAboutToWake() {

        if (this.getCurrentPokemon().getNonVolatileStatus() == Status.SLEEP) {
            return this.getCurrentPokemon().getSleepCounter() > 0;
        }
        return false;
    }

    public GSCMove getStrongestAttack(GSCPokemon opponent)    {
        GSCMove strongestAttack = EMPTY;
        int currentDamage = -1;
        int strongestDamage = 0;
        int emptyMove = 0;

        // cycle through moveslots
        for (int i = 0; i < 4; i++) {

            if (this.getCurrentPokemon().getMovePp(i) < 1)   {
                emptyMove++;
                continue;
            }

            if (this.getCurrentPokemon().getMoves()[i].effect() == MoveEffect.SELFDESTRUCT) {
                if (strongestAttack == EMPTY) {
                    // keep strongestDamage as 0 so that a non-suicidal move can still be selected
                    strongestAttack = this.getCurrentPokemon().getMoves()[i];
                }
                continue;
            }

            if (this.getCurrentPokemon().getMoves()[i].isAttack()) {
                currentDamage = this.getCurrentPokemon().getAttackDamageMaxRoll(
                        opponent,
                        this.getCurrentPokemon().getMoves()[i]
                );

                if (currentDamage > strongestDamage) {
                    strongestDamage = currentDamage;
                }

                // check if there's a move that has better accuracy and can KO in this range
                // TODO this only checks max damage roll. Implement something to assess individual damage rolls
                if (currentDamage == opponent.getCurrentHP()
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

    private GSCMove chooseRecoveryMove() {
        if (this.getCurrentPokemon().hasMove(REST) > -1)   {
            return REST;
        }
        else if (this.getCurrentPokemon().hasMove(RECOVER) > -1)    {
            return RECOVER;
        }
        else if (this.getCurrentPokemon().hasMove(SOFTBOILED) > -1) {
            return SOFTBOILED;
        }
        else if (this.getCurrentPokemon().hasMove(MILK_DRINK) > -1) {
            return MILK_DRINK;
        }
        else    {
            Messages.logNoRecoveryMoveFound(this.getCurrentPokemon());
            return this.getCurrentPokemon().getMoves()[0];
        }
    }

    private boolean opponentMayKO(@NotNull GSCPlayer opponent) {

        int damage = opponent.getCurrentPokemon().getAttackDamageMaxRoll(
                this.getCurrentPokemon(),
                opponent.getStrongestAttack(this.getCurrentPokemon()));

        // double the risk if slower, in order to recover early
        return (this.getCurrentPokemon().getStatSpe() < opponent.getCurrentPokemon().getStatSpe()
                ?
                damage >= 2 * this.getCurrentPokemon().getCurrentHP()
                :
                damage >= this.getCurrentPokemon().getCurrentHP()
                );
    }

    private boolean opponentCritMayKO(@NotNull GSCPlayer opponent)  {
        int damage = opponent.getCurrentPokemon().getAttackDamageCritMaxRoll(
                this.getCurrentPokemon(),
                opponent.getStrongestAttack(this.getCurrentPokemon()));

        // 1.5* the risk if slower in order to recover early. Back-to-back crits are exceedingly unlikely
        return (this.getCurrentPokemon().getStatSpe() < opponent.getCurrentPokemon().getStatSpe()
                ?
                damage >= Math.floor(1.5 * this.getCurrentPokemon().getCurrentHP())
                :
                damage >= this.getCurrentPokemon().getCurrentHP()
        );
    }

}
