package com.abrastat.general;

import java.io.*;
import javax.json.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Move {

    private static final Logger LOGGER = LoggerFactory.getLogger(Move.class);

    //Json mapper variables
    private String move;
    private Type moveType;
    private int basePower;
    private int accuracy;
    private int pp;
    private String secondaryEffect;
    private int secondaryChance;

    public boolean isAttack, hasSecondary = false, opponentIsTarget = true;

    //set this as default and override as necessary
    private boolean isBlockedByTypeImmunity = true;

    // Camel case for move names
    protected Move(String moveName)   {

        String moveList = "./src/main/resources/movesgsc.json";
        JsonObject jsonObject = null;

        try {
            JsonReader reader = Json.createReader(new FileInputStream(moveList));
            jsonObject = reader.readObject();

        } catch (FileNotFoundException e) {
            LOGGER.error("Error reading movesgsc file at: {}", moveList);
        }

        JsonObject jsonMove = jsonObject.getJsonObject(moveName);
        this.setMove(moveName);
        this.setPP(jsonMove);

        if (jsonMove.getJsonString("move").toString().equals("\"status\""))    {
            this.isAttack = false;
            this.opponentIsTarget = false;

            if (jsonMove.getJsonString("target").toString().equals("\"oppo\""))    {
                this.opponentIsTarget = true;
                initMoveTypeAndAccuracy(jsonMove);
            }

        } else  {
            this.isAttack = true;
            initMoveTypeAndAccuracy(jsonMove);
            this.setBasePower(jsonMove);
            this.setSecondaryEffect(jsonMove);

            if (jsonMove.getJsonString("secondary").toString().equals("\"none\"") == false)  {
                this.hasSecondary = true;
                this.setSecondaryChance(jsonMove);
            }
        }
    }

    private void initMoveTypeAndAccuracy(JsonObject jsonMove)  {
        this.setMoveType(jsonMove);
        this.setAccuracy(jsonMove);
    }
    public boolean isHiddenPower()  {
        if (this.move.equals("hiddenpower")) {
            return true;
        } else {
            return false;
        }
    }

    public String getMove() {
        return this.move;
    }

    private void setMove(String moveName) {
        this.move = WordUtils.capitalizeFully(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(moveName), ' '));
    }

    public Type getMoveType() {
        return moveType;
    }

    public void setMoveType(JsonObject moveType) {
        String type = moveType.getJsonString("type")
                .toString()
                .replace("\"", "")
                .toUpperCase();
        this.moveType = Type.valueOf(type);
    }

    public int getBasePower() {
        return basePower;
    }

    public void setBasePower(JsonObject basePower) {
        this.basePower = basePower.getInt("basepower");
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(JsonObject jsonMove) {
        this.accuracy = jsonMove.getInt("accuracy");
    }

    public int getPP() {
        return pp;
    }

    public void setPP(JsonObject jsonMove) {
        this.pp = jsonMove.getInt("pp");
    }

    public String getSecondaryEffect() {
        return secondaryEffect;
    }

    public void setSecondaryEffect(JsonObject secondaryEffect) {
        this.secondaryEffect = secondaryEffect.getString("secondary");
    }

    public int getSecondaryChance() {
        return secondaryChance;
    }

    public void setSecondaryChance(JsonObject secondaryChance) {
        this.secondaryChance = secondaryChance.getInt("chance");
    }
}
