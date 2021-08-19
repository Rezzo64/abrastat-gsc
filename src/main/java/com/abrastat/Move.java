package com.abrastat;

public abstract class Move {
    public static Type moveType;
    public static boolean isAttack, isStatus, isSecondary;

    //set this as default and override as necessary
    public static boolean isBlockedByTypeImmunity = true;
}
