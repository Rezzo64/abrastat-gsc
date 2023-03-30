package com.abrastat.general

enum class Status(val volatility: Volatility, private val target: Target) {
    // NONVOLATILE = Persists through switching
    BURN(Volatility.NONVOLATILE, Target.OPPONENT),
    FAINT(Volatility.NONVOLATILE, Target.SELF),
    FREEZE(Volatility.NONVOLATILE, Target.OPPONENT),
    HEALTHY(Volatility.NONVOLATILE, Target.SELF),
    PARALYSIS(Volatility.NONVOLATILE, Target.OPPONENT),
    POISON(Volatility.NONVOLATILE, Target.OPPONENT),
    SLEEP(Volatility.NONVOLATILE, Target.OPPONENT),
    TOXIC(Volatility.NONVOLATILE, Target.OPPONENT),

    // VOLATILE = Removed by switching
    ATTRACT(Volatility.VOLATILE, Target.OPPONENT),
    CONFUSION(Volatility.VOLATILE, Target.OPPONENT),
    CURSE(Volatility.VOLATILE, Target.OPPONENT),
    DESTINYBOND(Volatility.VOLATILE, Target.SELF),
    DISABLE(Volatility.VOLATILE, Target.OPPONENT),
    ENCORE(Volatility.VOLATILE, Target.OPPONENT),
    ENDURE(Volatility.VOLATILE, Target.SELF),
    FATIGUE(Volatility.VOLATILE, Target.SELF),
    FLINCH(Volatility.VOLATILE, Target.OPPONENT),
    FORESIGHT(Volatility.VOLATILE, Target.OPPONENT),
    HAZE(Volatility.VOLATILE, Target.OPPONENT),
    LEECHSEED(Volatility.VOLATILE, Target.OPPONENT),
    LIGHTSCREEN(Volatility.VOLATILE, Target.SELF),
    LOCKON(Volatility.VOLATILE, Target.OPPONENT),
    NIGHTMARE(Volatility.VOLATILE, Target.OPPONENT),
    PERISHSONG(Volatility.VOLATILE, Target.BOTH),
    PROTECT(Volatility.VOLATILE, Target.SELF),
    REFLECT(Volatility.VOLATILE, Target.SELF),
    SAFEGUARD(Volatility.VOLATILE, Target.SELF),
    SUBSTITUTE(Volatility.VOLATILE, Target.SELF),
    TRANSFORM(Volatility.VOLATILE, Target.SELF),
    WRAP(Volatility.VOLATILE, Target.OPPONENT)
    ;
    // Perish Song seems to be the only move which affects both users.
    // TODO define Baton Pass-able properties

    enum class Volatility {
        VOLATILE, NONVOLATILE
    }

    internal enum class Target {
        SELF, OPPONENT, BOTH
    }
}