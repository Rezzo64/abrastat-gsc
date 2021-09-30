package com.abrastat.general;

public abstract class Pokemon extends Species {

    private String nickname;
    private Gender gender;
    private Ability ability;
    private Move move1, move2, move3, move4;
    private int ivHP = 31, ivAtk = 31, ivDef = 31, ivSpA = 31, ivSpD = 31, ivSpe = 31;
    private int evHP = 0, evAtk = 0, evDef = 0, evSpA = 0, evSpD = 0, evSpe = 0;
    private int level = 100;

    enum Gender {

        MALE, FEMALE, NONE;
        // TODO: 19/08/2021 read & create getters + setters to read JSON

    }

    public Pokemon(String species)    {
        super(species);
    }

    public boolean hasMove(Move move)   {
        if (move1.equals(move) || move2.equals(move) || move3.equals(move) || move4.equals(move)) {
            return true;
        }
        return false;
    }

    protected abstract void initMoves();
    protected abstract void initIVs();
    protected abstract void initEVs();
    protected abstract void initHPStat();
    protected abstract void initOtherStats();
    protected abstract void initGender();
    protected abstract Pokemon addMove(Move move);

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public int getEvHP() {
        return evHP;
    }

    public void setEvHP(int evHP) {
        this.evHP = evHP;
    }

    public int getEvAtk() {
        return evAtk;
    }

    public void setEvAtk(int evAtk) {
        this.evAtk = evAtk;
    }

    public int getEvDef() {
        return evDef;
    }

    public void setEvDef(int evDef) {
        this.evDef = evDef;
    }

    public int getEvSpA() {
        return evSpA;
    }

    public void setEvSpA(int evSpA) {
        this.evSpA = evSpA;
    }

    public int getEvSpD() {
        return evSpD;
    }

    public void setEvSpD(int evSpD) {
        this.evSpD = evSpD;
    }

    public int getEvSpe() {
        return evSpe;
    }

    public void setEvSpe(int evSpe) {
        this.evSpe = evSpe;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Move[] getMoves()    {
        return new Move[] { move1, move2, move3, move4 };
    }

    public void setMoves(Move move1, Move move2, Move move3, Move move4)    {
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
    }

    public void setMoves(Move move1, Move move2, Move move3)    {
        setMoves(move1, move2, move3, null);
    }

    public void setMoves(Move move1, Move move2)    {
        setMoves(move1, move2, null);
    }

    public void setMoves(Move move) {
        setMoves(move, null);
    }

}
