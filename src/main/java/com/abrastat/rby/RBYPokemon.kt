package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.ppFailedToDeduct
import com.abrastat.rby.RBYDamageCalc.Companion.calcDamageEstimate
import kotlin.math.floor
import kotlin.math.sqrt

class RBYPokemon private constructor(speciesName: String, builder: Builder) : Pokemon(speciesName, builder) {
    override val moves: Array<RBYMove> = Array(4) { RBYMove.EMPTY }
    override val movesPp = IntArray(4)

    init {
        initIVs()
        initEVs()
        moves[0] = builder.moves[0]
        moves[1] = builder.moves[1]
        moves[2] = builder.moves[2]
        moves[3] = builder.moves[3]
        movesPp[0] = moves[0]!!.maxPp
        movesPp[1] = moves[1]!!.maxPp
        movesPp[2] = moves[2]!!.maxPp
        movesPp[3] = moves[3]!!.maxPp

        // TODO implement 'if' statement to override level due to user definition
        // TODO implement 'if' statement to override Stat Experience due to user definition
        initHPStat()
        initOtherStats()
        startingHP = if (builder.startingHp == 0) statHP else builder.startingHp
        currentHP = if (builder.startingHp == 0) statHP else builder.startingHp
    }

    class Builder(private val speciesName: String) : Pokemon.Builder<RBYMove>() {
        private var pokemon: RBYPokemon? = null
        val moves = Array(4) { RBYMove.EMPTY }
        var startingHp = 0
        override fun moves(move1: RBYMove): Builder {
            return this.moves(move1, RBYMove.EMPTY, RBYMove.EMPTY, RBYMove.EMPTY)
        }

        override fun moves(move1: RBYMove, move2: RBYMove): Builder {
            return this.moves(move1, move2, RBYMove.EMPTY, RBYMove.EMPTY)
        }

        override fun moves(move1: RBYMove, move2: RBYMove, move3: RBYMove): Builder {
            return this.moves(move1, move2, move3, RBYMove.EMPTY)
        }

        override fun moves(move1: RBYMove, move2: RBYMove, move3: RBYMove, move4: RBYMove): Builder {
            moves[0] = move1
            moves[1] = move2
            moves[2] = move3
            moves[3] = move4
            return this
        }

        fun moves(moves: Array<RBYMove>): Builder {
            var i = 0
            for (move in moves) {
                this.moves[i] = move
                i++
            }
            if (i < 4) {
                for (j in i..3) {
                    this.moves[j] = RBYMove.EMPTY
                }
            }
            return this
        }

        override fun hiddenPowerType(type: Type): Builder {
            return this
        }

        fun startingHp(hp: Int): Builder {
            startingHp = hp
            return this
        }

        override fun build(): RBYPokemon {
            pokemon = RBYPokemon(speciesName, this)
            return pokemon as RBYPokemon
        }
    }

    // DVs are essentially IVs but halved; 15 is the maximum DV you can have in a stat.
    public override fun initIVs() {
        val maxDV = 15
        ivAtk = maxDV
        ivDef = maxDV
        setDvSpecial(maxDV) // special attack and special defense share the same DV
        ivSpe = maxDV
        calculateDvHP() // generated by the result of other DVs
    }

    /*
    // Stat Experience is essentially EVs multiplied by 256.
    // Each base stat can be completely filled with Stat Experience to the maximum.
    */
    public override fun initEVs() {
        val maxStatExp = 65535
        evHP = maxStatExp
        evAtk = maxStatExp
        evDef = maxStatExp
        evSpA = maxStatExp
        evSpD = maxStatExp
        evSpe = maxStatExp
    }

    override fun initHPStat() {
        initStatHP((floor((baseHp + ivHP) * 2
                + floor(sqrt(evHP.toDouble()) / 4)
                * level / 100)
                + level + 10).toInt())
    }

    override fun initOtherStats() {
        initStatAtk(initOtherStatsFormula(baseAttack, ivAtk, evAtk, level))
        initStatDef(initOtherStatsFormula(baseDefense, ivDef, evDef, level))
        initStatSpA(initOtherStatsFormula(baseSpecialAttack, ivSpA, evSpA, level))
        initStatSpD(initOtherStatsFormula(baseSpecialDefense, ivSpD, evSpD, level))
        initStatSpe(initOtherStatsFormula(baseSpeed, ivSpe, evSpe, level))
    }

    override var moveOnePp: Int
        get() = movesPp[0]
        set(pp) {
            movesPp[0] = pp
        }

    override fun decrementMoveOnePp() {
        movesPp[0]--
    }

    override var moveTwoPp: Int
        get() = movesPp[1]
        set(pp) {
            movesPp[1] = pp
        }

    override fun decrementMoveTwoPp() {
        movesPp[1]--
    }

    override var moveThreePp: Int
        get() = movesPp[2]
        set(pp) {
            movesPp[2] = pp
        }

    override fun decrementMoveThreePp() {
        movesPp[2]--
    }

    override var moveFourPp: Int
        get() = movesPp[3]
        set(pp) {
            movesPp[3] = pp
        }

    override fun decrementMoveFourPp() {
        movesPp[3]--
    }

    override fun getMovePp(moveIndex: Int): Int {
        return movesPp[moveIndex]
    }

    override fun decrementMovePp(move: Move) {
        when (move) {
            moves[0] -> {
                decrementMoveOnePp()
            }
            moves[1] -> {
                decrementMoveTwoPp()
            }
            moves[2] -> {
                decrementMoveThreePp()
            }
            moves[3] -> {
                decrementMoveFourPp()
            }
            else -> {
                ppFailedToDeduct(this, move)
            }
        }
    }

    fun countEmptyMoves(): Int {
        var emptyMoveCount = 0
        for (move in moves) {
            if (move === RBYMove.EMPTY) {
                emptyMoveCount++
            }
        }
        return emptyMoveCount
    }

    fun hasMove(move: RBYMove): Int {
        for (i in moves.indices) {
            if (moves[i] === move) {
                return i // return array position of move
            }
        }
        return -1 // return negative number to denote that the move isn't in the moveset
    }

    override fun resetAllCounters() {
        resetSleepCounter()
        resetToxicCounter()
        resetConfuseCounter()
        resetDisableCounter()
        resetPerishCounter()
    }

    override fun removeNonVolatileStatusDebuff() {
        run {
            when (this.nonVolatileStatus) {
                Status.PARALYSIS -> this.initStatSpe(initOtherStatsFormula(this.baseSpeed, this.ivSpe, this.evSpe, this.level))
                Status.BURN -> this.initStatAtk(initOtherStatsFormula(this.baseAttack, this.ivAtk, this.evAtk, this.level))
                else -> {}
            }
        }
    }

    override fun resetAllPp() {
        movesPp[0] = moves[0].maxPp
        movesPp[1] = moves[1].maxPp
        movesPp[2] = moves[2].maxPp
        movesPp[3] = moves[3].maxPp
    }

    fun getAttackDamageMaxRoll(opponent: RBYPokemon?, move: RBYMove): Int {
        return calcDamageEstimate(
                this,
                opponent!!,
                move,
                false)
    }

    fun getAttackDamageCritMaxRoll(opponent: RBYPokemon?, move: RBYMove): Int {
        return calcDamageEstimate(
                this,
                opponent!!,
                move!!,
                true)
    }

//    override var ivHP: Int
//        get() = super.ivHP
//        // these methods are for gen 3+
//        public set(ivHP) {
//            throw UnsupportedOperationException("Trying to manually set HP DVs on a GSC Pokemon.")
//        }
//    override var ivSpA: Int
//        get() = super.ivSpA
//        public set(ivSpA) {
//            throw UnsupportedOperationException("Trying to manually set Special Attack DV (only) on a GSC Pokemon.")
//        }
//    override var ivSpD: Int
//        get() = super.ivSpD
//        public set(ivSpD) {
//            throw UnsupportedOperationException("Trying to manually set Special Defense DV (only) on a GSC Pokemon.")
//        }
    override val hiddenPowerType: Type = Type.NONE

    companion object {
        private fun initOtherStatsFormula(baseStat: Int, ivStat: Int, evStat: Int, level: Int): Int {
            return floor((baseStat + ivStat) * 2
                    + floor(sqrt(evStat.toDouble()) / 4) * level
                    / 100).toInt() + 5
        }
    }
}