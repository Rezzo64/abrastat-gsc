package com.abrastat.gsc;

import com.abrastat.general.*;
import org.jetbrains.annotations.NotNull;

import static com.abrastat.general.Type.*;

public class GSCPokemon extends Pokemon {

    private final GSCMove[] moves = new GSCMove[4];
    private int[] movesPp = new int[4];
    private final Type gscHiddenPowerType;
    private final int gscHiddenPowerBasePower;

    private GSCPokemon(String speciesName, Builder builder) {
        super(speciesName, builder);
        this.initIVs();
        this.initEVs();
        this.moves[0] = builder.moves[0];
        this.moves[1] = builder.moves[1];
        this.moves[2] = builder.moves[2];
        this.moves[3] = builder.moves[3];
        this.movesPp[0] = moves[0].maxPp();
        this.movesPp[1] = moves[1].maxPp();
        this.movesPp[2] = moves[2].maxPp();
        this.movesPp[3] = moves[3].maxPp();

        if (this.hasMove(GSCMove.SLEEP_TALK) > -1) { // put Sleep Talk as the last attack for efficiency
            this.moves[this.hasMove(GSCMove.SLEEP_TALK)] = this.moves[3];
            this.moves[3] = GSCMove.SLEEP_TALK;
        }

        if (builder.hiddenPowerType != DARK) {
            this.overrideIVs(builder.hiddenPowerType);
        }

        this.gscHiddenPowerType = builder.hiddenPowerType;
        this.gscHiddenPowerBasePower = calcHiddenPowerBaseDamage();

        // TODO implement 'if' statement to override level due to user definition
        // TODO implement 'if' statement to override Stat Experience due to user definition

        this.initHPStat();
        this.initOtherStats();
        this.setStartingHP(builder.startingHp == 0 ? this.getStatHP() : builder.startingHp);
        this.setCurrentHP(builder.startingHp == 0 ? this.getStatHP() : builder.startingHp);

    }

    public static class Builder extends Pokemon.Builder<GSCMove> {

        private GSCPokemon pokemon;
        private final String speciesName;
        private GSCMove[] moves = new GSCMove[4];
        private Type hiddenPowerType = DARK;
        private int startingHp = 0;

        public Builder(String speciesName)    {
            super();
            this.speciesName = speciesName;
        }

        @Override
        public Builder moves(@NotNull GSCMove move1)  {
            return this.moves(move1, GSCMove.EMPTY, GSCMove.EMPTY, GSCMove.EMPTY);
        }

        @Override
        public Builder moves(@NotNull GSCMove move1, @NotNull GSCMove move2)  {
            return this.moves(move1, move2, GSCMove.EMPTY, GSCMove.EMPTY);
        }

        @Override
        public Builder moves(@NotNull GSCMove move1, @NotNull GSCMove move2, @NotNull GSCMove move3) {
            return this.moves(move1, move2, move3, GSCMove.EMPTY);
        }

        @Override
        public Builder moves(@NotNull GSCMove move1, @NotNull GSCMove move2, @NotNull GSCMove move3, @NotNull GSCMove move4)  {
            this.moves[0] = move1;
            this.moves[1] = move2;
            this.moves[2] = move3;
            this.moves[3] = move4;
            return this;
        }

        public Builder hiddenPowerType(@NotNull Type type)   {
            this.hiddenPowerType = type;
            return this;
        }

        public Builder startingHp(@NotNull int hp) {
            this.startingHp = hp;
            return this;
        }

        @Override
        public GSCPokemon build() {
            pokemon = new GSCPokemon(this.speciesName, this);
            return pokemon;
        }
    }

    // DVs are essentially IVs but halved; 15 is the maximum DV you can have in a stat.
    public void initIVs()   {
        final int maxDV = 15;
        this.setIvAtk(maxDV);
        this.setIvDef(maxDV);
        this.setDvSpecial(maxDV);   // special attack and special defense share the same DV
        this.setIvSpe(maxDV);
        this.calculateDvHP();       // generated by the result of other DVs
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

    private void overrideIVs(@NotNull Type hiddenPowerType)  {
        switch (hiddenPowerType) {
            case NONE:
            case FAIRY: // no Fairy Hidden Power type
            case NORMAL: // no Normal Hidden Power type
                throw new UnsupportedOperationException("Attempting to create invalid Hidden Power type " + hiddenPowerType + ".");

            case DARK: // max DVs
                break;

            case FIRE:
                this.setIvAtk(14);
                this.setIvDef(12);
                break;

            case WATER:
                this.setIvAtk(14);
                this.setIvDef(13);
                break;

            case ELECTRIC:
                this.setIvAtk(14);
                break;

            case GRASS:
                this.setIvAtk(14);
                this.setIvDef(14);
                break;

            case ICE:
                this.setIvDef(13);
                break;

            case FIGHTING:
                this.setIvAtk(12);
                this.setIvDef(12);
                break;

            case POISON:
                this.setIvAtk(12);
                this.setIvDef(14);
                break;

            case GROUND:
                this.setIvAtk(12);
                break;

            case FLYING:
                this.setIvAtk(12);
                this.setIvDef(13);
                break;

            case PSYCHIC:
                this.setIvDef(12);
                break;

            case BUG:
                this.setIvAtk(13);
                this.setIvDef(13);
                break;

            case ROCK:
                this.setIvAtk(13);
                this.setIvDef(12);
                break;

            case GHOST:
                this.setIvAtk(13);
                this.setIvDef(14);
                break;

            case DRAGON:
                this.setIvDef(14);
                break;

            case STEEL:
                this.setIvAtk(13);
                break;
        }
        this.calculateDvHP();
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

    @Override
    public int getMoveOnePp() {
        return this.movesPp[0];
    }

    @Override
    protected void setMoveOnePp(int pp) {
        this.movesPp[0] = pp;
    }

    @Override
    protected void decrementMoveOnePp() {
        this.movesPp[0]--;
    }

    @Override
    public int getMoveTwoPp() {
        return this.movesPp[1];
    }

    @Override
    protected void setMoveTwoPp(int pp) {
        this.movesPp[1] = pp;
    }

    @Override
    protected void decrementMoveTwoPp() {
        this.movesPp[1]--;
    }

    @Override
    public int getMoveThreePp() {
        return this.movesPp[2];
    }

    @Override
    protected void setMoveThreePp(int pp) {
        this.movesPp[2] = pp;
    }

    @Override
    protected void decrementMoveThreePp() {
        this.movesPp[2]--;
    }

    @Override
    public int getMoveFourPp() {
        return this.movesPp[3];
    }

    @Override
    protected void setMoveFourPp(int pp) {
        this.movesPp[3] = pp;
    }

    @Override
    protected void decrementMoveFourPp() {
        this.movesPp[3]--;
    }

    @Override
    public int getMovePp(int moveIndex) {
        return this.movesPp[moveIndex];
    }

    @Override
    public void decrementMovePp(@NotNull Move move) {
        if (move.equals(moves[0]))         {
            decrementMoveOnePp();
        }
        else if (move.equals(moves[1]))    {
            decrementMoveTwoPp();
        }
        else if (move.equals(moves[2]))    {
            decrementMoveThreePp();
        }
        else if (move.equals(moves[3]))    {
            decrementMoveFourPp();
        }
        else                            {
            Messages.ppFailedToDeduct(this, move);
        }
    }

    @Override
    public int[] getMovesPp() {
        return this.movesPp;
    }

    @Override
    public GSCMove[] getMoves() {
        return this.moves;
    }

    public int countEmptyMoves() {
        int emptyMoveCount = 0;
        for (GSCMove move : this.moves) {
            if (move == GSCMove.EMPTY) {
                emptyMoveCount++;
            }
        }
        return emptyMoveCount;
    }

    public int hasMove(GSCMove move)    {

        for (int i = 0; i < getMoves().length; i++) {
            if (getMoves()[i] == move)  {
                return i; // return array position of move
            }
        }
        return -1; // return negative number to denote that the move isn't in the moveset
    }

    public Type getGscHiddenPowerType() {
        return this.gscHiddenPowerType;
    }

    public int getGscHiddenPowerBasePower() {
        return this.gscHiddenPowerBasePower;
    }

    private int calcHiddenPowerBaseDamage() {
        int atkMod = this.getIvAtk() <= 7 ? 8 : 0;
        int defMod = this.getIvDef() <= 7 ? 4 : 0;
        int spcMod = this.getIvSpA() <= 7 ? 2 : 0;
        int speMod = this.getIvSpe() <= 7 ? 1 : 0;
        int spcMod2 = Math.min(this.getIvSpA(), 3);
        return (((5 * (atkMod + defMod + spcMod + speMod) + spcMod2) / 2) + 31);
    }

    private static int initOtherStatsFormula(int baseStat, int ivStat, int evStat, int level)   {
        return (int)
                Math.floor(((baseStat + ivStat) * 2)
                    + (Math.floor((Math.sqrt(evStat) / 4)) * level)
                    / 100)
                    + 5;
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
                default:
                    break;
            }
        }
    }

    @Override
    public void resetAllPp() {
        this.movesPp[0] = moves[0].maxPp();
        this.movesPp[1] = moves[1].maxPp();
        this.movesPp[2] = moves[2].maxPp();
        this.movesPp[3] = moves[3].maxPp();
    }

    public int getAttackDamageMaxRoll(GSCPokemon opponent, GSCMove move) {
        return GSCDamageCalc.calcDamageEstimate(
                this,
                opponent,
                move,
                false);
    }

    public int getAttackDamageCritMaxRoll(GSCPokemon opponent, GSCMove move)   {
        return GSCDamageCalc.calcDamageEstimate(
                this,
                opponent,
                move,
                true);
    }

    // these methods are for gen 3+
    // instead, use the "setDvSpecial(DV)" and "calculateDvHP()" methods
    @Override
    public void setIvHP(int ivHP) {
        throw new UnsupportedOperationException("Trying to manually set HP DVs on a GSC Pokemon.");
    }

    @Override
    public void setIvSpA(int ivSpA) {
        throw new UnsupportedOperationException("Trying to manually set Special Attack DV (only) on a GSC Pokemon.");
    }

    @Override
    public void setIvSpD(int ivSpD) {
        throw new UnsupportedOperationException("Trying to manually set Special Defense DV (only) on a GSC Pokemon.");
    }
}
