package com.abrastat.general

enum class PlayerBehaviour {
    ATTEMPT_TO_DRAW, // Self-destruct, Explosion and Destiny Bond behaviour
    FISH_FOR_BURN,  // Tries to burn the opponent
    FISH_FOR_CRIT,
    FISH_FOR_FLINCHES,  // Uses flinching moves rather than attacking strongly
    FISH_FOR_FREEZE,  // Tries to freeze the opponent
    FISH_FOR_PARALYSIS,  // Tries to paralyse the opponent
    FISH_FOR_POISON,  // Tries to poison the opponent with a secondary effect
    FISH_FOR_SPDEF_DROP,  // Tries to drop the opponent's special defence with a certain attack
    GO_FOR_BIG_KO,  // Hyper Beam test behaviour
    MOVE_FORCED, JUST_ATTACK,  // chooses attack which will do the most damage to the opponent
    RECOVER_RISKILY,  // recovers without considering for critical hits
    RECOVER_SAFELY,  // recovers while considering for crit max roll on opponent's next move
    SET_UP_FIVE_TIMES,
    SET_UP_FOUR_TIMES,
    SET_UP_ONCE,
    SET_UP_SHARP_ONCE,
    SET_UP_SHARP_THRICE,
    SET_UP_SHARP_TWICE,
    SET_UP_SINGLE_USE_MOVE,  // Uses a defensive move like Safeguard, Light Screen, etc.
    SET_UP_SIX_TIMES,
    SET_UP_THRICE,
    SET_UP_TWICE,
    STATUS_OPPONENT_PARALYSIS,
    STATUS_OPPONENT_POISON,
    STATUS_OPPONENT_SLEEP,
    STATUS_OPPONENT_VOLATILE,  // Stackable status effects
    STEAL_ITEM,  // Uses Thief
    ;

    override fun toString(): String {
        return super.toString().replace("_", " ")
    }

    enum class BehaviourGroup {
        FORCED(arrayOf(MOVE_FORCED)),
        ATTACK(arrayOf(JUST_ATTACK)),
        SET_UP(arrayOf(SET_UP_ONCE,
                SET_UP_TWICE,
                SET_UP_THRICE,
                SET_UP_FOUR_TIMES,
                SET_UP_FIVE_TIMES,
                SET_UP_SIX_TIMES)),
        SET_UP_SHARP(arrayOf(SET_UP_ONCE,
                SET_UP_TWICE,
                SET_UP_THRICE)),
        BELLY(arrayOf(SET_UP_ONCE)),
        KO_RESPONSE(arrayOf(RECOVER_SAFELY,
                RECOVER_RISKILY,
                ATTEMPT_TO_DRAW)),
        STATUS_OPPONENT_NON_VOLATILE(arrayOf(STATUS_OPPONENT_PARALYSIS,
                STATUS_OPPONENT_SLEEP,
                STATUS_OPPONENT_POISON)),
        VOLATILES(arrayOf(STATUS_OPPONENT_VOLATILE)),
        STATUS_FISH(arrayOf(FISH_FOR_PARALYSIS,
                FISH_FOR_BURN,
                FISH_FOR_POISON,
                FISH_FOR_FREEZE,
                FISH_FOR_FLINCHES)),
        STAT_RAISE_DROP_FISH(arrayOf(FISH_FOR_SPDEF_DROP)),
        FISH_CRIT(arrayOf(FISH_FOR_CRIT)),
        STEAL(arrayOf(STEAL_ITEM)),
        GO_FOR_HYPER_BEAM(arrayOf(GO_FOR_BIG_KO));

        val behaviours: Array<PlayerBehaviour>

        constructor(behaviours: Array<PlayerBehaviour>) {
            this.behaviours = behaviours
        }
    }
}