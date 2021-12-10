package com.abrastat.general;

import static com.abrastat.general.Status.Volatility.*;
import static com.abrastat.general.Status.Target.*;

public enum Status {

    // VOLATILE = Removed by switching
    // NONVOLATILE = Persists through switching
    // Perish Song seems to be the only move which affects both users.
    // TODO define Baton Pass-able properties

    ATTRACT(VOLATILE, OPPONENT),
    BURN(NONVOLATILE, OPPONENT),
    CONFUSION(VOLATILE, OPPONENT),
    CURSE(VOLATILE, OPPONENT),
    DESTINYBOND(VOLATILE, SELF),
    DISABLE(VOLATILE, OPPONENT),
    ENCORE(VOLATILE,OPPONENT),
    ENDURE(VOLATILE, SELF),
    FATIGUE(VOLATILE, SELF),
    FLINCH(VOLATILE,OPPONENT),
    FORESIGHT(VOLATILE,OPPONENT),
    FREEZE(NONVOLATILE, OPPONENT),
    HEALTHY(NONVOLATILE, SELF),
    LEECHSEED(VOLATILE,OPPONENT),
    LOCKON(VOLATILE,OPPONENT),
    NIGHTMARE(VOLATILE,OPPONENT),
    PARALYSIS(NONVOLATILE, OPPONENT),
    PERISHSONG(VOLATILE, BOTH),
    POISON(NONVOLATILE, OPPONENT),
    PROTECT(VOLATILE, SELF),
    REST(NONVOLATILE, SELF),
    SAFEGUARD(VOLATILE, SELF),
    SLEEP(NONVOLATILE, OPPONENT),
    SUBSTITUTE(VOLATILE, SELF),
    TOXIC(NONVOLATILE, OPPONENT),
    TRANSFORM(VOLATILE, SELF),
    WRAP(VOLATILE, OPPONENT);



    private Volatility volatility;
    private Target target;

    Status(Volatility volatility, Target target)  {
        this.volatility = volatility;
        this.target = target;
    }

    enum Volatility {
        VOLATILE,
        NONVOLATILE
    }

    enum Target {
        SELF,
        OPPONENT,
        BOTH
    }

}

