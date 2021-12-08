package com.abrastat.general;

import org.jetbrains.annotations.NotNull;

import static com.abrastat.general.Status.*;

public final class Messages {

    private static String messageBuffer;

    public static void statusFailed(Pokemon pokemon, @NotNull Status status) {
        switch (status) {
            case BURN:
                messageBuffer = (pokemon + " is already burned!");
                break;
            case FREEZE:
                messageBuffer = (pokemon + " is already frozen!");
                break;
            case PARALYSIS:
                messageBuffer = (pokemon + " is already paralysed!");
                break;
            case POISON:
            case TOXIC:
                messageBuffer = (pokemon + " is already poisoned!");
                break;
            case SLEEP:
            case REST:
                messageBuffer = (pokemon + " is already asleep!");
                break;
            case CONFUSION:
            case FATIGUE:
                messageBuffer = (pokemon + " is already confused!");
                break;
            case ATTRACT:
                messageBuffer = (pokemon + " is already in love!");
                break;
            default:
                messageBuffer = "But it failed!";
        }
    }

    public static void cantAttack(Pokemon pokemon, @NotNull Status status)   {
        switch (status) {
            case PARALYSIS:
                messageBuffer = (pokemon + " is fully paralysed!");
                break;
            case REST:
            case SLEEP:
                messageBuffer = (pokemon + " is fast asleep!");
                break;
            case FREEZE:
                messageBuffer = (pokemon + " is frozen solid!");
                break;
            case CONFUSION:
            case FATIGUE:
                messageBuffer = (pokemon + " hurt itself in confusion!");
                break;
            case ATTRACT:
                messageBuffer = (pokemon + " is immobilised by love!");
                break;
            default:
                messageBuffer = (pokemon + " can't attack because of its " + status + "!");
        }
    }

    public static void statusChanged(Pokemon pokemon, @NotNull Status status)    {
        switch (status) {
            case REST:
            case SLEEP:
                messageBuffer = (pokemon + " woke up!");
                break;
            case FREEZE:
                messageBuffer = (pokemon + " thawed!");
                break;
            case CONFUSION:
            case FATIGUE:
                messageBuffer = (pokemon + " snapped out of its confusion!");
                break;
            default:
                messageBuffer = (pokemon + "is no longer affected by " + status + "!");
        }
    }

    public static void logEffect(Pokemon pokemon, Status status)    {
        switch (status) {
            case BURN:
                messageBuffer = (pokemon + " is hurt by its burn!");
                break;
            case POISON:
            case TOXIC:
                messageBuffer = (pokemon + " is hurt by poison!");
                break;
            case CONFUSION:
            case FATIGUE:
                messageBuffer = (pokemon + " is confused!");
                break;
            case ATTRACT:
                messageBuffer = (pokemon + " is in love with its opponent!");
                break;
            default:
                messageBuffer = (pokemon + " is affected by " + status + "!");
        }
    }
}
