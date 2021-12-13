package com.abrastat.gsc;

import com.abrastat.general.Move;

public class GSCMove extends Move {

    private static final String moveList = "./src/main/resources/movesgsc.json";
    private int currentPP;

    public GSCMove(String move)    {
        super(move);
    }

    @Override
    public void reduceCurrentPP() {
        this.currentPP--;
    }

    @Override
    public void resetCurrentPP() {
        this.currentPP = this.getMaxPP();
    }

    @Override
    public int getCurrentPP() {
        return this.currentPP;
    }

    @Override
    public void setCurrentPP(int ppValue) {
        this.currentPP = ppValue;
    }

    public String getMoveList() {
        return moveList;
    }

}
