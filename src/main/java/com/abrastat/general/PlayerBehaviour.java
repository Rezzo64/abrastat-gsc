package com.abrastat.general;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum PlayerBehaviour {

    JUST_ATTACK,                    // chooses attack which will do the most damage to the opponent
    RECOVER_RISKILY,                // recovers without considering for critical hits
    RECOVER_SAFELY,                 // recovers while considering for crit max roll on opponent's next move
    SET_UP_SINGLE_USE_MOVE,         // Uses a defensive move like Safeguard, Light Screen, etc.
    SET_UP_ONCE,
    SET_UP_TWICE,
    SET_UP_THRICE,
    SET_UP_FOUR_TIMES,
    SET_UP_FIVE_TIMES,
    SET_UP_SIX_TIMES,
    STATUS_OPPONENT_NON_VOLATILE,   // Selects a non-volatile status move to use
    STATUS_OPPONENT_VOLATILE,       // Stackable status effects
    FISH_FOR_PARALYSIS,             // Tries to paralyse the opponent
    FISH_FOR_FREEZE,                // Tries to freeze the opponent
    FISH_FOR_BURN,                  // Tries to burn the opponent
    FISH_FOR_POISON,                // Tries to poison the opponent with a secondary effect
    FISH_FOR_FLINCHES,              // Uses flinching moves rather than attacking strongly
    FISH_FOR_SPDEF_DROP,            // Tries to drop the opponent's special defence with a certain attack
    FISH_FOR_CRIT,                  //
    STEAL_ITEM,                     // Uses Thief
    GO_FOR_BIG_KO                   // Hyper Beam test behaviour

}
