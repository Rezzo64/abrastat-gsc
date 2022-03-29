package com.abrastat.general;

public class GameResult {
    private final Pokemon pokemon1;
    private final Pokemon pokemon2;



    private final int winsP1, winsP2, draws, avgTurns, avgHpP1, avgHpP2, struggleP1, struggleP2, boomP1, boomP2;
    private final int[] avgPpP1;
    private final int[] avgPpP2;

    public GameResult(
            Pokemon pokemon1,
            Pokemon pokemon2,
            int winsP1,
            int winsP2,
            int draws,
            int avgTurns,
            int avgHpP1,
            int[] avgPpP1,
            int avgHpP2,
            int[] avgPpP2,
            int struggleP1,
            int struggleP2,
            int boomP1,
            int boomP2
            ) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.winsP1 = winsP1;
        this.winsP2 = winsP2;
        this.draws = draws;
        this.avgTurns = avgTurns;
        this.avgHpP1 = avgHpP1;
        this.avgPpP1 = avgPpP1;
        this.avgHpP2 = avgHpP2;
        this.avgPpP2 = avgPpP2;
        this.struggleP1 = struggleP1;
        this.struggleP2 = struggleP2;
        this.boomP1 = boomP1;
        this.boomP2 = boomP2;
    }

    public Pokemon getPokemon1() {
        return pokemon1;
    }

    public Pokemon getPokemon2() {
        return pokemon2;
    }

    public int getWinsP1() {
        return winsP1;
    }

    public int getWinsP2() {
        return winsP2;
    }

    public int getDraws() {
        return draws;
    }

    public int getAvgTurns() {
        return avgTurns;
    }

    public int getAvgHpP1() {
        return avgHpP1;
    }

    public int getAvgHpP2() {
        return avgHpP2;
    }

    public int getStruggleP1() {
        return struggleP1;
    }

    public int getStruggleP2() {
        return struggleP2;
    }

    public int getBoomP1() {
        return boomP1;
    }

    public int getBoomP2() {
        return boomP2;
    }

    public int[] getAvgPpP1() {
        return avgPpP1;
    }

    public int[] getAvgPpP2() {
        return avgPpP2;
    }
}
