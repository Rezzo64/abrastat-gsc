package com.abrastat.general;

import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;
import org.jetbrains.annotations.NotNull;

import static com.abrastat.general.Status.*;

public enum Messages {

    INSTANCE;

    private static String messageBuffer;

    private static void handleMessage() { // handle logging from here and whether it's enabled or not
        System.out.println(messageBuffer);
    }

    // For debugging purposes only, records any effects which aren't yet handled.
    public static void notImplementedYet(Object o)  {
        messageBuffer = ("DEBUG: " + o + " not implemented yet. No effect logged.");
        handleMessage();
    }

    public static void announceTeam(@NotNull Player player) {
        StringBuilder s = new StringBuilder((player.getName() + ": " + System.lineSeparator()));
        for (Pokemon pokemon : player.getCurrentTeam()) {
            if (pokemon != null) {
                s.append(pokemon.getSpecies() + " ");
            } else {
                s.append("(-) ");
            }
        }
        s.append(System.lineSeparator());
        messageBuffer = s.toString();
        handleMessage();
    }

    public static void announceSwitch(@NotNull Player player, @NotNull Pokemon pokemon) {
        messageBuffer = (player.getName() + " sent out " + pokemon.getSpecies() + "!");
        handleMessage();
    }

    public static void statusFailed(Pokemon pokemon, @NotNull Status status) {
        switch (status) {
            case BURN:
                messageBuffer = (pokemon.getSpecies() + " is already burned!");
                break;
            case FREEZE:
                messageBuffer = (pokemon.getSpecies() + " is already frozen!");
                break;
            case PARALYSIS:
                messageBuffer = (pokemon.getSpecies() + " is already paralysed!");
                break;
            case POISON:
            case TOXIC:
                messageBuffer = (pokemon.getSpecies() + " is already poisoned!");
                break;
            case SLEEP:
            case REST:
                messageBuffer = (pokemon.getSpecies() + " is already asleep!");
                break;
            case CONFUSION:
            case FATIGUE:
                messageBuffer = (pokemon.getSpecies() + " is already confused!");
                break;
            case ATTRACT:
                messageBuffer = (pokemon.getSpecies() + " is already in love!");
                break;
            default:
                messageBuffer = "But it failed!";
        }

        handleMessage();
    }

    public static void cantAttack(Pokemon pokemon, @NotNull Status status)   {
        switch (status) {
            case PARALYSIS:
                messageBuffer = (pokemon.getSpecies() + " is fully paralysed!");
                break;
            case REST:
            case SLEEP:
                messageBuffer = (pokemon.getSpecies() + " is fast asleep!");
                break;
            case FREEZE:
                messageBuffer = (pokemon.getSpecies() + " is frozen solid!");
                break;
            case CONFUSION:
            case FATIGUE:
                messageBuffer = (pokemon.getSpecies() + " hurt itself in confusion!");
                break;
            case ATTRACT:
                messageBuffer = (pokemon.getSpecies() + " is immobilised by love!");
                break;
            default:
                messageBuffer = (pokemon.getSpecies() + " can't attack because of its " + status + "!");
        }

        handleMessage();
    }

    public static void statusChanged(Pokemon pokemon, @NotNull Status status)    {
        switch (status) {
            case REST:
            case SLEEP:
                messageBuffer = (pokemon.getSpecies() + " woke up!");
                break;
            case FREEZE:
                messageBuffer = (pokemon.getSpecies() + " thawed!");
                break;
            case CONFUSION:
            case FATIGUE:
                messageBuffer = (pokemon.getSpecies() + " snapped out of its confusion!");
                break;
            default:
                messageBuffer = (pokemon.getSpecies() + "is no longer affected by " + status + "!");
        }

        handleMessage();
    }

    public static void logEffect(Pokemon pokemon, @NotNull Status status)    {
        switch (status) {
            case BURN:
                messageBuffer = (pokemon.getSpecies() + " is hurt by its burn!");
                break;
            case POISON:
            case TOXIC:
                messageBuffer = (pokemon.getSpecies() + " is hurt by poison!");
                break;
            case CONFUSION:
            case FATIGUE:
                messageBuffer = (pokemon.getSpecies() + " is confused!");
                break;
            case ATTRACT:
                messageBuffer = (pokemon.getSpecies() + " is in love with its opponent!");
                break;
            default:
                messageBuffer = (pokemon.getSpecies() + " is affected by " + status + "!");
        }

        handleMessage();
    }

    public static void leftoversHeal(@NotNull Pokemon pokemon)   {
        messageBuffer = (pokemon.getSpecies() + " restored health using its Leftovers!");
        handleMessage();
    }

    public static void logCriticalHit() {
        messageBuffer = "A critical hit!";
        handleMessage();
    }

    public static void logAttack(@NotNull Pokemon pokemon, @NotNull Move move)    {
        messageBuffer = (pokemon.getSpecies() + " used " + move.getMove() + "!");
        handleMessage();
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

    public static void logFainted(@NotNull Pokemon pokemon) {
        messageBuffer = (pokemon.getSpecies() + " fainted!");
        handleMessage();
    }

    public static void logDamageTaken(@NotNull Pokemon pokemon, int damage)  {
        messageBuffer = (pokemon.getSpecies() + " took " + damage + " hit points of damage.");
        handleMessage();
    }

    public static void logMissedAttack(@NotNull Pokemon pokemon) {
        messageBuffer = (pokemon.getSpecies()) + "'s attack missed!";
        handleMessage();
    }

    public static void logNewStatus(Pokemon pokemon, @NotNull Status status) {
        switch (status) {
            case PARALYSIS:
                messageBuffer = (pokemon.getSpecies() + " is paralysed! It may be unable to move!");
                break;
            case SLEEP:
            case REST:
                messageBuffer = (pokemon.getSpecies() + " has fallen asleep!");
                break;
            case FREEZE:
                messageBuffer = (pokemon.getSpecies() + " was frozen!");
            case BURN:
                messageBuffer = (pokemon.getSpecies() + " was burned by the attack!");
            case FATIGUE:
            case CONFUSION:
                messageBuffer = (pokemon.getSpecies() + " became confused!");
            case POISON:
                messageBuffer = (pokemon.getSpecies() + " is poisoned!");
            case TOXIC:
                messageBuffer = (pokemon.getSpecies() + " is badly poisoned!");
            default:
                messageBuffer = (pokemon.getSpecies() + " is afflicted by " + status + "!");
        }
        handleMessage();
    }

    public static void announceTurn(int turnNumber) {
        messageBuffer = System.lineSeparator() + ("Turn " + turnNumber) + System.lineSeparator();
        handleMessage();
    }

    public static void gameOver() {
        messageBuffer = "Game over!";
        handleMessage();
    }

    public static void displayCurrentHP(@NotNull GSCPokemon pokemon) {
        messageBuffer = (pokemon.getSpecies() +
                " current HP: " + pokemon.getCurrentHP() +
                " / " +
                pokemon.getStatHP());
        handleMessage();
    }
}
