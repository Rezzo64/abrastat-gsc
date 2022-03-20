package com.abrastat.general;

import org.jetbrains.annotations.NotNull;

import static com.abrastat.general.PlayerBehaviour.BehaviourGroup.*;

public enum PlayerBehaviour {

    MOVE_FORCED,
    JUST_ATTACK,                // chooses attack which will do the most damage to the opponent
    RECOVER_RISKILY,            // recovers without considering for critical hits
    RECOVER_SAFELY,             // recovers while considering for crit max roll on opponent's next move
    SET_UP_SINGLE_USE_MOVE,     // Uses a defensive move like Safeguard, Light Screen, etc.
    SET_UP_ONCE,
    SET_UP_TWICE,
    SET_UP_THRICE,
    SET_UP_FOUR_TIMES,
    SET_UP_FIVE_TIMES,
    SET_UP_SIX_TIMES,
    SET_UP_SHARP_ONCE,
    SET_UP_SHARP_TWICE,
    SET_UP_THRICE_SHARP,
    STATUS_OPPONENT_PARALYSIS,
    STATUS_OPPONENT_POISON,
    STATUS_OPPONENT_SLEEP,
    STATUS_OPPONENT_VOLATILE,   // Stackable status effects
    FISH_FOR_PARALYSIS,         // Tries to paralyse the opponent
    FISH_FOR_FREEZE,            // Tries to freeze the opponent
    FISH_FOR_BURN,              // Tries to burn the opponent
    FISH_FOR_POISON,            // Tries to poison the opponent with a secondary effect
    FISH_FOR_FLINCHES,          // Uses flinching moves rather than attacking strongly
    FISH_FOR_SPDEF_DROP,        // Tries to drop the opponent's special defence with a certain attack
    FISH_FOR_CRIT,              //
    STEAL_ITEM,                 // Uses Thief
    GO_FOR_BIG_KO,              // Hyper Beam test behaviour
    ATTEMPT_TO_DRAW             // Self-destruct, Explosion and Destiny Bond behaviour
    ;

    @Override
    public @NotNull String toString() {
        return super.toString().replace("_", " ");
    }

    private BehaviourGroup group;

    public enum BehaviourGroup {

        FORCED (new PlayerBehaviour[]{MOVE_FORCED}),
        ATTACK (new PlayerBehaviour[]{JUST_ATTACK}),
        SET_UP (new PlayerBehaviour[]{SET_UP_ONCE, SET_UP_TWICE, SET_UP_THRICE, SET_UP_FOUR_TIMES, SET_UP_FIVE_TIMES, SET_UP_SIX_TIMES}),
        SET_UP_SHARP (new PlayerBehaviour[]{SET_UP_ONCE, SET_UP_TWICE, SET_UP_THRICE}),
        BELLY (new PlayerBehaviour[]{SET_UP_ONCE}),
        KO_RESPONSE (new PlayerBehaviour[]{RECOVER_SAFELY, RECOVER_RISKILY, ATTEMPT_TO_DRAW}),
        STATUS_OPPONENT_NON_VOLATILE (new PlayerBehaviour[]{STATUS_OPPONENT_PARALYSIS, STATUS_OPPONENT_SLEEP, STATUS_OPPONENT_POISON}),
        VOLATILES (new PlayerBehaviour[]{STATUS_OPPONENT_VOLATILE}),
        STATUS_FISH (new PlayerBehaviour[]{FISH_FOR_PARALYSIS, FISH_FOR_BURN, FISH_FOR_POISON, FISH_FOR_FREEZE, FISH_FOR_FLINCHES}),
        STAT_RAISE_DROP_FISH (new PlayerBehaviour[]{FISH_FOR_SPDEF_DROP}),
        FISH_CRIT (new PlayerBehaviour[]{FISH_FOR_CRIT}),
        STEAL (new PlayerBehaviour[]{STEAL_ITEM}),
        GO_FOR_HYPER_BEAM (new PlayerBehaviour[]{GO_FOR_BIG_KO});

        private final PlayerBehaviour[] behaviours;

        BehaviourGroup(PlayerBehaviour[] behaviours) {
            this.behaviours = behaviours;
        }

        public PlayerBehaviour[] getBehaviours() {
            return behaviours;
        }
    }

    PlayerBehaviour() {
        this.group = group;
    }

    public boolean isGroupBehaviour(BehaviourGroup group) {
        return this.group == group;
    }
}
