package com.abrastat.general;

import org.jetbrains.annotations.NotNull;

public interface Moves {

    String name();
    boolean isAttack();
    Type type();
    int pp();
    int accuracy();
    int basePower();
    MoveEffect effect();

}
