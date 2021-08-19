package com.abrastat;

public abstract class Move {
    public static moveType Type;
    public static boolean isAttack, isStatus, isSecondary;

    //set this as default and override as necessary
    public static boolean isBlockedByTypeImmunity = true;
}
