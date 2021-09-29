package com.abrastat.general;

public abstract class Move {
    public static Type moveType;
    public static byte accuracy;
    public static boolean isAttack, isStatus, hasSecondary;

    //set this as default and override as necessary
    public static boolean isBlockedByTypeImmunity = true;

    public Move(String move)   {

    }

    public Type getType()   {
        return moveType;
    }
}
