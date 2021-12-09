package com.abrastat.general;

import java.io.*;
import javax.json.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.*;
import org.jetbrains.annotations.NotNull;
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

    // inherits the movelist json file from the correct gen
    public abstract String getMoveList();

    public boolean isAttack, hasSecondary = false, opponentIsTarget = true;

    //set this as default and override as necessary
    private boolean isBlockedByTypeImmunity = true;

    // Camel case for move names
    // Honestly I've just coded in the GSC logic here for now as I'm lazy.
    // But this does need refactoring.

    protected Move(String moveName)   {

        String moveList = getMoveList();
        JsonObject jsonObject = null;

        try {
            JsonReader reader = Json.createReader(new FileInputStream(moveList));
            jsonObject = reader.readObject();

        } catch (FileNotFoundException e) {
            LOGGER.error("Error reading file at: {}", moveList);
            e.printStackTrace();
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
        return (this.move.equals("Hidden Power"));
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

    private void setMoveType(@NotNull JsonObject moveType) {
        String type = moveType.getJsonString("type")
                .toString()
                .replace("\"", "")
                .toUpperCase();
        this.moveType = Type.valueOf(type);
    }

    public int getBasePower() {
        return basePower;
    }

    private void setBasePower(@NotNull JsonObject basePower) {
        this.basePower = basePower.getInt("basepower");
    }

    public int getAccuracy() {
        return accuracy;
    }

    private void setAccuracy(@NotNull JsonObject jsonMove) {
        this.accuracy = jsonMove.getInt("accuracy");
    }

    public int getPP() {
        return pp;
    }

    private void setPP(@NotNull JsonObject jsonMove) {
        this.pp = jsonMove.getInt("pp");
    }

    public String getSecondaryEffect() {
        return secondaryEffect;
    }

    private void setSecondaryEffect(@NotNull JsonObject secondaryEffect) {
        this.secondaryEffect = secondaryEffect.getString("secondary");
    }

    public int getSecondaryChance() {
        return secondaryChance;
    }

    private void setSecondaryChance(@NotNull JsonObject secondaryChance) {
        this.secondaryChance = secondaryChance.getInt("chance");
    }
}
