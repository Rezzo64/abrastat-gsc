package com.abrastat.general

enum class Status(val volatility: Volatility, private val target: Target) {
    // VOLATILE = Removed by switching
    // NONVOLATILE = Persists through switching
    // Perish Song seems to be the only move which affects both users.
    // TODO define Baton Pass-able properties
    ATTRACT(Volatility.VOLATILE, Target.OPPONENT),
    BURN(Volatility.NONVOLATILE, Target.OPPONENT),
    CONFUSION(Volatility.VOLATILE, Target.OPPONENT),
    CURSE(Volatility.VOLATILE, Target.OPPONENT),
    DESTINYBOND(Volatility.VOLATILE, Target.SELF),
    DISABLE(Volatility.VOLATILE, Target.OPPONENT),
    ENCORE(Volatility.VOLATILE, Target.OPPONENT),
    ENDURE(Volatility.VOLATILE, Target.SELF),
    FAINT(Volatility.NONVOLATILE, Target.SELF),
    FATIGUE(Volatility.VOLATILE, Target.SELF),
    FLINCH(Volatility.VOLATILE, Target.OPPONENT),
    FORESIGHT(Volatility.VOLATILE, Target.OPPONENT),
    FREEZE(Volatility.NONVOLATILE, Target.OPPONENT),
    HEALTHY(Volatility.NONVOLATILE, Target.SELF),
    LEECHSEED(Volatility.VOLATILE, Target.OPPONENT),
    LOCKON(Volatility.VOLATILE, Target.OPPONENT),
    NIGHTMARE(Volatility.VOLATILE, Target.OPPONENT),
    PARALYSIS(Volatility.NONVOLATILE, Target.OPPONENT),
    PERISHSONG(Volatility.VOLATILE, Target.BOTH),
    POISON(Volatility.NONVOLATILE, Target.OPPONENT),
    PROTECT(Volatility.VOLATILE, Target.SELF),
    SAFEGUARD(Volatility.VOLATILE, Target.SELF),
    SLEEP(Volatility.NONVOLATILE, Target.OPPONENT),  // also Rest
    SUBSTITUTE(Volatility.VOLATILE, Target.SELF),
    TOXIC(Volatility.NONVOLATILE, Target.OPPONENT),
    TRANSFORM(Volatility.VOLATILE, Target.SELF),
    WRAP(Volatility.VOLATILE, Target.OPPONENT);

    enum class Volatility {
        VOLATILE, NONVOLATILE
    }

    internal enum class Target {
        SELF, OPPONENT, BOTH
    }
}