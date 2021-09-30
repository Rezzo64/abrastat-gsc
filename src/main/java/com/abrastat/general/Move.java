package com.abrastat.general;

public abstract class Move {
    public static String move;
    public static Type moveType;
    public static int accuracy;
    public static int pp;
    public static boolean isAttack, isStatus, hasSecondary;

    //set this as default and override as necessary
    public static boolean isBlockedByTypeImmunity = true;

    public Move(String move)   {
        this.move = move;
    }

    public Type getType()   {
        return moveType;
    }

}
