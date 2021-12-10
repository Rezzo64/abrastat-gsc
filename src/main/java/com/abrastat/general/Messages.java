package com.abrastat.general;

import org.jetbrains.annotations.NotNull;

import static com.abrastat.general.Status.*;

public enum Messages {

    INSTANCE;

    private static String messageBuffer;

    private static void handleMessage() { // handle logging from here and whether it's enabled or not
        System.out.println(messageBuffer);
    }

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

        handleMessage();
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

        handleMessage();
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

        handleMessage();
    }

    public static void logEffect(Pokemon pokemon, @NotNull Status status)    {
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

        handleMessage();
    }

    public static void leftoversHeal(Pokemon pokemon)   {
        messageBuffer = (pokemon + " restored health using its Leftovers!");
        handleMessage();
    }

    public static void logCriticalHit() {
        messageBuffer = "A critical hit!";
        handleMessage();
    }

    public static void logAttack(Pokemon pokemon, Move move)    {
        messageBuffer = (pokemon + " used " + move + "!");
    }

    public static void logTypeEffectiveness(int typeEffectiveness)  {
        if (typeEffectiveness == 0) {
            messageBuffer = "It didn't affect the opponent!";
        }
        else if (typeEffectiveness < 10)    {
            messageBuffer = "It's not very effective...";
        }
        else if (typeEffectiveness > 10)    {
            messageBuffer = "It's super effective!";
        }
        else    {
            return;
        }

        handleMessage();
    }
}
