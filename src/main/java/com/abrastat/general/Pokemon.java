package com.abrastat.general;

public abstract class Pokemon extends Species {
    private Species species;
    private Pokemon pokemon;
    private Gender gender;
    private Ability ability;
    private int ivHP, ivAtk, ivDef, ivSpA, ivSpD, ivSpe = 31;
    private byte evHP, evAtk, evDef, evSpA, evSpD, evSpe;
    private int level = 100;

    public Pokemon(String species)    {
        super(species);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getIvHP() {
        return ivHP;
    }

    public void setIvHP(int ivHP) {
        this.ivHP = ivHP;
    }

    public int getIvAtk() {
        return ivAtk;
    }

    public void setIvAtk(int ivAtk) {
        this.ivAtk = ivAtk;
    }

    public int getIvDef() {
        return ivDef;
    }

    public void setIvDef(int ivDef) {
        this.ivDef = ivDef;
    }

    public int getIvSpA() {
        return ivSpA;
    }

    public void setIvSpA(int ivSpA) {
        this.ivSpA = ivSpA;
    }

    public int getIvSpD() {
        return ivSpD;
    }

    public void setIvSpD(int ivSpD) {
        this.ivSpD = ivSpD;
    }

    public int getIvSpe() {
        return ivSpe;
    }

    public void setIvSpe(int ivSpe) {
        this.ivSpe = ivSpe;
    }

    public byte getEvHP() {
        return evHP;
    }

    public void setEvHP(byte evHP) {
        this.evHP = evHP;
    }

    public byte getEvAtk() {
        return evAtk;
    }

    public void setEvAtk(byte evAtk) {
        this.evAtk = evAtk;
    }

    public byte getEvDef() {
        return evDef;
    }

    public void setEvDef(byte evDef) {
        this.evDef = evDef;
    }

    public byte getEvSpA() {
        return evSpA;
    }

    public void setEvSpA(byte evSpA) {
        this.evSpA = evSpA;
    }

    public byte getEvSpD() {
        return evSpD;
    }

    public void setEvSpD(byte evSpD) {
        this.evSpD = evSpD;
    }

    public byte getEvSpe() {
        return evSpe;
    }

    public void setEvSpe(byte evSpe) {
        this.evSpe = evSpe;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    enum Gender {

        MALE, FEMALE, NONE;
        // TODO: 19/08/2021 read & create getters + setters to read JSON

    }
}
