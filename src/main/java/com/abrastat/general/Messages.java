package com.abrastat.general;

import org.jetbrains.annotations.NotNull;

import static com.abrastat.general.Status.*;

public final class Messages {

    private static String messageBuffer;

    public static void statusFailed(Pokemon pokemon, @NotNull Status status) {
        switch (status) {
            case BURN:
                messageBuffer = (pokemon + " is already burned!");
            case FREEZE:
                messageBuffer = (pokemon + " is already frozen!");
            case PARALYSIS:
                messageBuffer = (pokemon + "is already paralysed!");
            case POISON:
            case TOXIC:
                messageBuffer = (pokemon + "is already poisoned!");
            case SLEEP:
            case REST:
                messageBuffer = (pokemon + " is already asleep!");
            case CONFUSION:
            case FATIGUE:
                messageBuffer = (pokemon + " is already confused!");
        }
    }
}
