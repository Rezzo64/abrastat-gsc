package com.abrastat.general;

import com.abrastat.gsc.GSCPokemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;




public abstract class Pokemon extends Species {

    private static final Logger LOGGER = LoggerFactory.getLogger(GSCPokemon.class);

    private String nickname;
    private Gender gender;
    private Ability ability;
    private Move move1, move2, move3, move4;
    private int ivHP = 31, ivAtk = 31, ivDef = 31, ivSpA = 31, ivSpD = 31, ivSpe = 31;  // default max
    private int evHP = 0, evAtk = 0, evDef = 0, evSpA = 0, evSpD = 0, evSpe = 0;        // default min
    private int level = 100;

    private Status nonVolatileStatus = null;
    private HashSet<Status> volatileStatus = new HashSet<>();

    public abstract Item getHeldItem();
    public abstract void setHeldItem(Item heldItem);
    public abstract int getCurrentHP();
    public abstract void applyHeal(int healAmount);
    public abstract void applyDamage(int damage);

    enum Gender {

        MALE, FEMALE, NONE;
        // TODO: 19/08/2021 read & create getters + setters to read JSON

    }

    public Pokemon(String species)    {
        super(species);
    }

    public boolean hasMove(Move move)   {
        return move1.equals(move) || move2.equals(move) || move3.equals(move) || move4.equals(move);
    }

    protected abstract void initMoves();
    protected abstract void initIVs();
    protected abstract void initEVs();
    protected abstract void initHPStat();
    protected abstract void initOtherStats();
    protected abstract void initGender();


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

    private void addMove(Move move, int moveslot) {
        switch (moveslot)   {
            case 1:
                this.move1 = move;
            case 2:
                this.move2 = move;
            case 3:
                this.move3 = move;
            case 4:
                this.move4 = move;
        }

    }

    public Pokemon addMove(Move move) {

        Move[] currentMoves = this.getMoves();

        // Java convention says the iterator starts at 0, whereas Pokemon convention
        // indicates that the first moveslot starts at 1.
        // This method just checks for the first null slot and fills it in.
        for (int i = 0; i < 4; i++) {
            if (currentMoves[i] == null) {
                this.addMove(move, i + 1);
            }
            return (this);
        }
        LOGGER.error("tried to add a move to {} but it already has" +
                "4 moves. Please remove a move or resubmit all moves.", this.getSpecies());
        return (this);
    }

    public Pokemon addMoves(Move move1, Move move2, Move move3, Move move4)    {
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
        return (this);
    }

    private void addMoves(Move move1, Move move2, Move move3)    {
        this.addMoves(move1, move2, move3, null);
    }

    private void addMoves(Move move1, Move move2)    {
        this.addMoves(move1, move2, null);
    }

    private void addMoves(Move move) {
        this.addMoves(move, null);
    }

    public Move[] getMoves()    {
        return new Move[] { move1, move2, move3, move4 };
    }

    public Status getNonVolatileStatus()  {
        return this.nonVolatileStatus;
    }

    // only one nv status can be applied at a time.
    public void applyNonVolatileStatus(Status status)   {
        if (this.nonVolatileStatus == null) {
            this.nonVolatileStatus = status;
        } else {
            Messages.statusFailed(this, this.nonVolatileStatus);
        }
    }

    public void removeNonVolatileStatus()  {
        this.nonVolatileStatus = null;
    }

    public Status[] getVolatileStatus()   {
        return (Status[]) volatileStatus.toArray();
    }

    public void applyVolatileStatus(Status status)  {
        if (!(volatileStatus.contains(status)))    {
            volatileStatus.add(status);
        } else {
            Messages.statusFailed(this, status);
        }
    }

    public void removeVolatileStatus(Status status) {
        if (this.volatileStatus.contains(status)) {
            this.volatileStatus.remove(status);
        }
    }

    public void clearVolatileStatus()   {
        this.volatileStatus.clear();
    }
}
