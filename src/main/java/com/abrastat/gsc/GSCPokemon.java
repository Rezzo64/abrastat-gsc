package com.abrastat.gsc;

import com.abrastat.general.Item;
import com.abrastat.general.Pokemon;
import com.abrastat.general.Status;
import com.abrastat.general.Type;
import org.jetbrains.annotations.NotNull;

public class GSCPokemon extends Pokemon {

    private int statHP, statAtk, statDef, statSpA, statSpD, statSpe;
    private int currentHP;
    private int atkMod = 0, defMod = 0, spAMod = 0, spDMod = 0, speMod = 0, accMod = 0, evaMod = 0;
    private Item heldItem;

    // all counters below to be handled incrementally (for consistency)
    private int sleepCounter = 0;
    private int toxicCounter = 0;
    private int confuseCounter = 0;
    private int disableCounter = 0;
    private int perishCounter = 0;

    public GSCPokemon(String speciesName) {
        super(speciesName);
        this.initIVs();
        this.initEVs();

        // TODO implement 'if' statement to override level due to user definition
        // if (true) {this.setLevel();}

//         Move[] moves = this.getMoves();
//         for (int i = 0; i < 4; i++) {
//              if (moves[i].isHiddenPower())    {
//                  this.overrideIVs(moves[i].getType());
//              }
//         }

        // TODO implement 'if' statement to override Stat Experience due to user definition
        // if (true) {this.overrideEVs();}

        this.initHPStat();
        this.initOtherStats();
        this.currentHP = statHP;
    }

    public static class Builder extends Pokemon.Builder {

    }

    @Override
    protected void initMoves() {

    }

    // DVs are essentially IVs but halved; 15 is the maximum DV you can have in a stat.
    @Override
    protected void initIVs() {
        int maxDV = 15;
        this.setIvHP(maxDV);
        this.setIvAtk(maxDV);
        this.setIvDef(maxDV);
        this.setIvSpA(maxDV);
        this.setIvSpD(maxDV);
        this.setIvSpe(maxDV);
    }
    /*
    // Stat Experience is essentially EVs multiplied by 256.
    // Each base stat can be completely filled with Stat Experience to the maximum.
    */
    @Override
    protected void initEVs() {
        int maxStatExp = 65535;
        this.setEvHP(maxStatExp);
        this.setEvAtk(maxStatExp);
        this.setEvDef(maxStatExp);
        this.setEvSpA(maxStatExp);
        this.setEvSpD(maxStatExp);
        this.setEvSpe(maxStatExp);
    }

    private void overrideIVs(int atk, int def, int spa, int spd, int spe)    {

    }

    //TODO
    private void overrideIVs(Type hiddenPowerType)  {

    }

    private void overrideEVs(int atk, int def, int spa, int spd, int spe)    {

    }

    @Override
    protected void initHPStat()    {
        this.statHP = (int)
                (Math.floor(((this.getBaseHp() + this.getIvHP()) * 2)
                        + (Math.floor(Math.sqrt(this.getEvHP()) / 4))
                        * this.getLevel() / 100)
                        + this.getLevel() + 10);
    }

    @Override
    protected void initOtherStats() {
        int level = this.getLevel();
        this.statAtk = initOtherStatsFormula(this.getBaseAttack(), this.getIvAtk(), this.getEvAtk(), level);
        this.statDef = initOtherStatsFormula(this.getBaseDefense(), this.getIvDef(), this.getEvDef(), level);
        this.statSpA = initOtherStatsFormula(this.getBaseSpecialAttack(), this.getIvSpA(), this.getEvSpA(), level);
        this.statSpD = initOtherStatsFormula(this.getBaseSpecialDefense(), this.getIvSpD(), this.getEvSpD(), level);
        this.statSpe = initOtherStatsFormula(this.getBaseSpeed(), this.getIvSpe(), this.getEvSpe(), level);
    }

    private int initOtherStatsFormula(int baseStat, int ivStat, int evStat, int level)   {
        return (int)
                Math.floor(((baseStat + ivStat) * 2)
                    + (Math.floor((Math.sqrt(evStat) / 4)) * level)
                    / 100)
                    + 5;
    }

    @Override
    protected void initGender() {

    }

    @Override
    public Item getHeldItem() {
        return this.heldItem;
    }

    @Override
    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
    }

    public int getStatHP() {
        return statHP;
    }

    public int getStatAtk() {
        return statAtk;
    }

    public int getStatDef() {
        return statDef;
    }

    public int getStatSpA() {
        return statSpA;
    }

    public int getStatSpD() {
        return statSpD;
    }

    public int getStatSpe() {
        return statSpe;
    }

    public int getCurrentHP() {
        return currentHP;
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

    public int getSleepCounter() {
        return sleepCounter;
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

    public int getPerishCounter() {
        return perishCounter;
    }

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

    public void incrementPerishCounter() {
        this.perishCounter++;
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

    public void resetPerishCounter() {
        this.perishCounter = 0;
    }

    public void resetAllCounters() {
        this.sleepCounter = 0;
        this.toxicCounter = 0;
        this.confuseCounter = 0;
        this.disableCounter = 0;
        this.perishCounter = 0;
    }

    @Override
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

    public void removeNonVolatileStatusDebuff()    {
        switch (this.getNonVolatileStatus()) {
            case PARALYSIS:
                this.statSpe = initOtherStatsFormula(this.getBaseSpeed(), this.getIvSpe(), this.getEvSpe(), this.getLevel());
                break;
            case BURN:
                this.statAtk = initOtherStatsFormula(this.getBaseAttack(), this.getIvAtk(), this.getEvAtk(), this.getLevel());
                break;
        }
    }

    @Override
    public void resetStatHp() {
        this.currentHP = this.statHP;
    }
}
