package com.abrastat.general;

import java.io.*;
import javax.json.*;

public abstract class Move {

    //Json mapper variables
    private String move;
    private Type moveType;
    private int accuracy;
    private int pp;
    private boolean isAttack, isStatus, hasSecondary;

    //set this as default and override as necessary
    private boolean isBlockedByTypeImmunity = true;

    public Move(String move)   {
        this.move = move;
        String moveList = "./src/main/resources/movesgsc.json";
        JsonObject jsonObject = null;

        try {
            JsonReader reader = Json.createReader(new FileInputStream(moveList));
            jsonObject = reader.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isHiddenPower()  {
        if (this.move.equals("hiddenpower")) {
            return true;
        } else {
            return false;
        }
    }

    public Type getType()   {
        return moveType;
    }

}
