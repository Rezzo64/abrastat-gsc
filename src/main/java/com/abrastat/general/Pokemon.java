package com.abrastat.general;

import com.abrastat.gsc.GSCPokemon;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;




public abstract class Pokemon extends Species {

    private static final Logger LOGGER = LoggerFactory.getLogger(GSCPokemon.class);
    private int lastDamageTaken;

    private String nickname;
    private Gender gender;
    private Ability ability;
    private Move move1, move2, move3, move4;
    private int ivHP, ivAtk, ivDef, ivSpA, ivSpD, ivSpe;
    private int evHP, evAtk, evDef, evSpA, evSpD, evSpe;
    private int level;
    private Item heldItem;
    private Status nonVolatileStatus = Status.HEALTHY;
    private HashSet<Status> volatileStatus = new HashSet<>();
    private int statHP, statAtk, statDef, statSpA, statSpD, statSpe;
    private int atkMod = 0, defMod = 0, spAMod = 0, spDMod = 0, speMod = 0, accMod = 0, evaMod = 0;
    private int currentHP;

    // all counters below to be handled incrementally (for consistency)
    private int sleepCounter = 0;
    private int toxicCounter = 0;
    private int confuseCounter = 0;
    private int disableCounter = 0;

    public void applyHeal(int healAmount) {
        if (this.currentHP + healAmount >= this.statHP) {
            this.currentHP = this.statHP;
        } else {
            this.currentHP += healAmount;
        }
    }

    public void applyDamage(int damage) {
        if (damage >= this.currentHP)   {
            this.setLastDamageTaken(this.currentHP);
            this.currentHP = 0;

        }
        else    {
            this.setLastDamageTaken(damage);
            this.currentHP -= damage;
        }
    }

    enum Gender {

        MALE, FEMALE, NONE
        // TODO: 19/08/2021 read & create getters + setters to read JSON

    }

    public Pokemon(String species, @NotNull Builder builder)   {

        super(species);

        this.nickname = builder.nickname;
        this.ivHP = builder.ivHP;
        this.ivAtk = builder.ivAtk;
        this.ivDef = builder.ivDef;
        this.ivSpA = builder.ivSpA;
        this.ivSpD = builder.ivSpD;
        this.ivSpe = builder.ivSpe;
        this.evHP = builder.evHP;
        this.evAtk = builder.evAtk;
        this.evDef = builder.evDef;
        this.evSpA = builder.evSpA;
        this.evSpD = builder.evSpD;
        this.evSpe = builder.evSpe;
        this.level = builder.level;
        this.heldItem = builder.heldItem;
        this.gender = builder.gender;
        this.ability = builder.ability;

    }
    public abstract static class Builder {

        private String nickname;
        private int ivHP = 31, ivAtk = 31, ivDef = 31, ivSpA = 31, ivSpD = 31, ivSpe = 31;  // default max
        private int evHP = 0, evAtk = 0, evDef = 0, evSpA = 0, evSpD = 0, evSpe = 0;        // default min
        private int level = 100; // default max
        private Ability ability;
        private Item heldItem;
        private Gender gender;

        public Builder()  {}

        public Builder nickname(String name)    {
            this.nickname = name;
            return this;
        }

        public abstract Builder moves(String move1);
        public abstract Builder moves(String move1, String move2);
        public abstract Builder moves(String move1, String move2, String move3);
        public abstract Builder moves(String move1, String move2, String move3, String move4);

        public Builder ivs(int ivHP, int ivAtk, int ivDef, int ivSpA, int ivSpD, int ivSpe)    {
            this.ivHP = ivHP;
            this.ivAtk = ivAtk;
            this.ivDef = ivDef;
            this.ivSpA = ivSpA;
            this.ivSpD = ivSpD;
            this.ivSpe = ivSpe;
            return this;
        }
        public Builder evs(int evHP, int evAtk, int evDef, int evSpA, int evSpD, int evSpe) {
            this.evHP = evHP;
            this.evAtk = evAtk;
            this.evDef = evDef;
            this.evSpA = evSpA;
            this.evSpD = evSpD;
            this.evSpe = evSpe;
            return this;
        }

        public Builder level(int level)   {
            this.level = level;
            return this;
        }

        // TODO
        public Builder gender()   {
            return this;
        }

        public Builder item(Item item)  {
            this.heldItem = item;
            return this;
        }

        public abstract Pokemon build();

    }

    protected abstract void initIVs();
    protected abstract void initEVs();
    protected abstract void initHPStat();
    protected abstract void initOtherStats();

    public boolean hasMove(Move move)   {
        return move1.equals(move) || move2.equals(move) || move3.equals(move) || move4.equals(move);
    }

    public Gender getGender() {
        return gender;
    }

    public int getIvHP() {
        return ivHP;
    }

    protected void setIvHP(int ivHP) {
        this.ivHP = ivHP;
    }

    public int getIvAtk() {
        return ivAtk;
    }

    protected void setIvAtk(int ivAtk) {
        this.ivAtk = ivAtk;
    }

    public int getIvDef() {
        return ivDef;
    }

    protected void setIvDef(int ivDef) {
        this.ivDef = ivDef;
    }

    public int getIvSpA() {
        return ivSpA;
    }

    protected void setIvSpA(int ivSpA) {
        this.ivSpA = ivSpA;
    }

    public int getIvSpD() {
        return ivSpD;
    }

    protected void setIvSpD(int ivSpD) {
        this.ivSpD = ivSpD;
    }

    public int getIvSpe() {
        return ivSpe;
    }

    protected void setIvSpe(int ivSpe) {
        this.ivSpe = ivSpe;
    }

    public int getEvHP() {
        return evHP;
    }

    protected void setEvHP(int evHP) {
        this.evHP = evHP;
    }

    public int getEvAtk() {
        return evAtk;
    }

    protected void setEvAtk(int evAtk) {
        this.evAtk = evAtk;
    }

    public int getEvDef() {
        return evDef;
    }

    protected void setEvDef(int evDef) {
        this.evDef = evDef;
    }

    public int getEvSpA() {
        return evSpA;
    }

    protected void setEvSpA(int evSpA) {
        this.evSpA = evSpA;
    }

    public int getEvSpD() {
        return evSpD;
    }

    protected void setEvSpD(int evSpD) {
        this.evSpD = evSpD;
    }

    public int getEvSpe() {
        return evSpe;
    }

    protected void setEvSpe(int evSpe) {
        this.evSpe = evSpe;
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    protected void setCurrentHP(int hp) {
        this.currentHP = hp;
    }

    public int getStatHP() {
        return statHP;
    }

    protected void initStatHP(int hp) {
        this.statHP = hp;
    }

    public int getStatAtk() {
        return statAtk;
    }

    protected void initStatAtk(int atk)    {
        this.statAtk = atk;
    }

    public int getStatDef() {
        return statDef;
    }

    protected void initStatDef(int def) {
        this.statDef = def;
    }

    public int getStatSpA() {
        return statSpA;
    }

    protected void initStatSpA(int spa) {
        this.statSpA = spa;
    }

    public int getStatSpD() {
        return statSpD;
    }

    protected void initStatSpD(int spd) {
        this.statSpD = spd;
    }

    public int getStatSpe() {
        return statSpe;
    }

    protected void initStatSpe(int spe) {
        this.statSpe = spe;
    }

    public int getAtkMod() {
        return atkMod;
    }

    public int getDefMod() {
        return defMod;
    }

    public int getSpAMod() {
        return spAMod;
    }

    public int getSpDMod() {
        return spDMod;
    }

    public int getSpeMod() {
        return speMod;
    }

    public int getAccMod() {
        return accMod;
    }

    public int getEvaMod() {
        return evaMod;
    }

    protected void raiseStat(@NotNull Stat stat, int amount)    {
        switch (stat)   {
            case ATTACK:
                this.atkMod = checkModUpperLimit(atkMod, amount);
                break;
            case DEFENSE:
                this.defMod = checkModUpperLimit(defMod, amount);
                break;
            case SPECIALATTACK:
                this.spAMod = checkModUpperLimit(spAMod, amount);
                break;
            case SPECIALDEFENSE:
                this.spDMod = checkModUpperLimit(spDMod, amount);
                break;
            case SPEED:
                this.speMod = checkModUpperLimit(spDMod, amount);
                break;
            case ACCURACY:
                this.accMod = checkModUpperLimit(accMod, amount);
                break;
            case EVASION:
                this.evaMod = checkModUpperLimit(evaMod, amount);
        }
    }

    protected void dropStat(@NotNull Stat stat, int amount)    {
        switch (stat)   {
            case ATTACK:
                this.atkMod = checkModLowerLimit(atkMod, amount);
                break;
            case DEFENSE:
                this.defMod = checkModLowerLimit(defMod, amount);
                break;
            case SPECIALATTACK:
                this.spAMod = checkModLowerLimit(spAMod, amount);
                break;
            case SPECIALDEFENSE:
                this.spDMod = checkModLowerLimit(spDMod, amount);
                break;
            case SPEED:
                this.speMod = checkModLowerLimit(spDMod, amount);
                break;
            case ACCURACY:
                this.accMod = checkModLowerLimit(accMod, amount);
                break;
            case EVASION:
                this.evaMod = checkModLowerLimit(evaMod, amount);
        }
    }

    public static int checkModUpperLimit(int mod, int increase)   {
        if (mod + Math.abs(increase) >= 5)   {
            return 6;
        } else {
            return (mod + Math.abs(increase));
        }
    }

    public static int checkModLowerLimit(int mod, int decrease)   {
        if (mod - Math.abs(decrease) <= -5)   {
            return -6;
        } else {
            return (mod - Math.abs(decrease));
        }
    }

    public int getToxicCounter() {
        return toxicCounter;
    }

    public int getConfuseCounter() {
        return confuseCounter;
    }

    public int getDisableCounter() {
        return disableCounter;
    }

    public int getLevel() {
        return level;
    }

    protected void setLevel(int level) {
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

    protected Pokemon addMoves(Move move1, Move move2, Move move3, Move move4)    {
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
        return (this);
    }

    public Move[] getMoves()    {
        return new Move[] { move1, move2, move3, move4 };
    }

    public Status getNonVolatileStatus()  {
        return this.nonVolatileStatus;
    }

    public Item getHeldItem()  {
        return this.heldItem;
    }

    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
    }

    public void applySecondaryEffect(@NotNull MoveEffect effect)    {
        // TODO write cases for every effect
        switch (effect) {
            case PRZ30:
                applyNonVolatileStatus(Status.PARALYSIS);
                break;
            case RECOIL25:
                this.applyDamage(lastDamageTaken / 4);
                break;
        }
    }
    // only one non-volatile status can be applied at a time.
    public void applyNonVolatileStatus(Status status)   {
        if (this.nonVolatileStatus == Status.HEALTHY) {
            this.nonVolatileStatus = status;
            applyNonVolatileStatusDebuff(status);
        } else {
            Messages.statusFailed(this, this.nonVolatileStatus);
        }
    }

    public void isFainted() {
        this.nonVolatileStatus = Status.FAINT;
    }

    public void removeNonVolatileStatus()  {

        this.removeNonVolatileStatusDebuff();
        this.nonVolatileStatus = Status.HEALTHY;

    }

    public Status[] getVolatileStatus()   {
        if (!(this.volatileStatus.isEmpty()))   {
            return (Status[]) this.volatileStatus.toArray();
        } else  {
            return new Status[]{};
        }
    }

    public void applyVolatileStatus(Status status)  {
        if (!(this.volatileStatus.contains(status)))    {
            this.volatileStatus.add(status);
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

    public int getLastDamageTaken()    {
        return this.lastDamageTaken;
    }

    public void setLastDamageTaken(int damage)   {
        this.lastDamageTaken = damage;
    }

    public void setAtkMod(int atkMod) {
        this.atkMod = atkMod;
    }

    public void setDefMod(int defMod) {
        this.defMod = defMod;
    }

    public void setSpAMod(int spAMod) {
        this.spAMod = spAMod;
    }

    public void setSpDMod(int spDMod) {
        this.spDMod = spDMod;
    }

    public void setSpeMod(int speMod) {
        this.speMod = speMod;
    }

    public void setAccMod(int accMod) {
        this.accMod = accMod;
    }

    public void setEvaMod(int evaMod) {
        this.evaMod = evaMod;
    }

    public void resetMods() {
        this.atkMod = 0;
        this.defMod = 0;
        this.spAMod = 0;
        this.spDMod = 0;
        this.speMod = 0;
        this.accMod = 0;
        this.evaMod = 0;
    }

    public int getSleepCounter() {
        return sleepCounter;
    }

    public void incrementSleepCounter() {
        this.sleepCounter++;
    }

    public void incrementToxicCounter() {
        this.toxicCounter++;
    }

    public void incrementConfuseCounter() {
        this.confuseCounter++;
    }

    public void incrementDisableCounter() {
        this.disableCounter++;
    }

    public void resetSleepCounter() {
        this.sleepCounter = 0;
    }

    public void resetToxicCounter() {
        this.toxicCounter = 0;
    }

    public void resetConfuseCounter() {
        this.confuseCounter = 0;
    }

    public void resetDisableCounter() {
        this.disableCounter = 0;
    }

    public abstract void resetAllCounters();

    public void applyNonVolatileStatusDebuff(@NotNull Status status) {
        switch (status) {
            case PARALYSIS:
                this.statSpe = (statSpe / 4);
                break;
            case BURN:
                this.statAtk = (statAtk / 2);
                break;
        }
    }

    public abstract void removeNonVolatileStatusDebuff();

    public void resetStatHp() {
        this.currentHP = this.statHP;
    }

    public void resetAllPP()    {
        this.move1.resetCurrentPP();
        this.move2.resetCurrentPP();
        this.move3.resetCurrentPP();
        this.move4.resetCurrentPP();
    }

    public void resetMoveOnePP()  {
        this.move1.resetCurrentPP();
    }
    
}