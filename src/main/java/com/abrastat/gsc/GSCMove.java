package com.abrastat.gsc;

import com.abrastat.general.Move;

import java.util.HashMap;

public class GSCMove extends Move {

    private static final String moveList = "./src/main/resources/movesgsc.json";

    public GSCMove(String move)    {
        super(move);
    }

    public String getMoveList() {
        return moveList;
    }

}
