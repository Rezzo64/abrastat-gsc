package com.abrastat.gsc;

import com.abrastat.general.*;
import org.jetbrains.annotations.NotNull;

public class GSCPokemon extends Pokemon {

    private int perishCounter = 0;

    private GSCPokemon(String speciesName, Builder builder) {
        super(speciesName, builder);
        this.initIVs();
        this.initEVs();
        this.initHPStat();
        this.initOtherStats();
        this.addMoves(builder.move1, builder.move2, builder.move3, builder.move4);

        // TODO implement 'if' statement to override IVs
        // for when this Pokemon knows the move "Hidden Power"

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

        this.setCurrentHP(this.getStatHP());
    }

    public static class Builder extends Pokemon.Builder<GSCPokemon> {

        private GSCMove move1, move2, move3, move4;
        private GSCPokemon pokemon;
        private final String speciesName;

        public Builder(String speciesName)    {
            super();
            this.speciesName = speciesName;
        }

        public Builder moves(String move1)  {
            this.move1 = new GSCMove(move1);
            return this;
        }
        public Builder moves(String move1, String move2)  {
            this.move1 = new GSCMove(move1);
            this.move2 = new GSCMove(move2);
            return this;
        }
        public Builder moves(String move1, String move2, String move3) {
            this.move1 = new GSCMove(move1);
            this.move2 = new GSCMove(move2);
            this.move3 = new GSCMove(move3);
            return this;
        }
        public Builder moves(String move1, String move2, String move3, String move4)  {
            this.move1 = new GSCMove(move1);
            this.move2 = new GSCMove(move2);
            this.move3 = new GSCMove(move3);
            this.move4 = new GSCMove(move4);
            return this;
        }

        @Override
        public GSCPokemon build() {
            pokemon = new GSCPokemon(speciesName, this);
            return pokemon;

        }
    }

    // DVs are essentially IVs but halved; 15 is the maximum DV you can have in a stat.
    public void initIVs()   {
        final int maxDV = 15;
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
    public void initEVs()   {
        final int maxStatExp = 65535;
        this.setEvHP(maxStatExp);
        this.setEvAtk(maxStatExp);
        this.setEvDef(maxStatExp);
        this.setEvSpA(maxStatExp);
        this.setEvSpD(maxStatExp);
        this.setEvSpe(maxStatExp);
    }

    private void overrideIVs(Type hiddenPowerType)  {
        //TODO
    }

    @Override
    protected void initHPStat()    {
        this.initStatHP(
                (int)
                (Math.floor(((this.getBaseHp() + this.getIvHP()) * 2)
                        + (Math.floor(Math.sqrt(this.getEvHP()) / 4))
                        * this.getLevel() / 100)
                        + this.getLevel() + 10)
        );
    }

    @Override
    protected void initOtherStats() {

        this.initStatAtk(initOtherStatsFormula(this.getBaseAttack(), this.getIvAtk(), this.getEvAtk(), getLevel()));
        this.initStatDef(initOtherStatsFormula(this.getBaseDefense(), this.getIvDef(), this.getEvDef(), getLevel()));
        this.initStatSpA(initOtherStatsFormula(this.getBaseSpecialAttack(), this.getIvSpA(), this.getEvSpA(), getLevel()));
        this.initStatSpD(initOtherStatsFormula(this.getBaseSpecialDefense(), this.getIvSpD(), this.getEvSpD(), getLevel()));
        this.initStatSpe(initOtherStatsFormula(this.getBaseSpeed(), this.getIvSpe(), this.getEvSpe(), getLevel()));

    }

    private static int initOtherStatsFormula(int baseStat, int ivStat, int evStat, int level)   {
        return (int)
                Math.floor(((baseStat + ivStat) * 2)
                    + (Math.floor((Math.sqrt(evStat) / 4)) * level)
                    / 100)
                    + 5;
    }

    public int getPerishCounter() {
        return perishCounter;
    }

    public void resetPerishCounter() {
        this.perishCounter = 0;
    }

    public void incrementPerishCounter() {
        this.perishCounter++;
    }

    @Override
    public void resetAllCounters()  {
        this.resetSleepCounter();
        this.resetToxicCounter();
        this.resetConfuseCounter();
        this.resetDisableCounter();
        this.resetPerishCounter();
    }

    @Override
    public void removeNonVolatileStatusDebuff() {
        {
            switch (this.getNonVolatileStatus()) {
                case PARALYSIS:
                    this.initStatSpe(GSCPokemon.initOtherStatsFormula(this.getBaseSpeed(), this.getIvSpe(), this.getEvSpe(), this.getLevel()));
                    break;
                case BURN:
                    this.initStatAtk(GSCPokemon.initOtherStatsFormula(this.getBaseAttack(), this.getIvAtk(), this.getEvAtk(), this.getLevel()));
                    break;
            }
        }
    }
}
