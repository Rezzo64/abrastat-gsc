package com.abrastat.rby

import com.abrastat.general.*
import com.abrastat.general.Messages.Companion.ppFailedToDeduct
import com.abrastat.rby.RBYDamage.Companion.calcDamageEstimate
import kotlin.math.floor
import kotlin.math.sqrt

class RBYPokemon constructor(speciesName: String, builder: Builder) : Pokemon(speciesName, builder) {
    // this program takes in the name of the pokemon
    // and holds different states
    override val moves: Array<RBYMove> = Array(4) { RBYMove.EMPTY }
    override val movesPp = IntArray(4)
    var originalAttack = 0
        private set
    private var originalSpeed = 0

    // move states
    var struggle = false
    var tookTurn = false
    var extraHit = -1
    var multiTurn = Pair(RBYMove.EMPTY, 0)
    var storedDamage = 0

    init {
        initIVs()
        initEVs()
        for (i in 0 until 4) {
            moves[i] = builder.moves[i]
            movesPp[i] = moves[i].maxPp
        }

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
                if (i == 4)
                    break
                this.moves[i] = move
                i++
            }
            if (i < 4)
                for (j in i..3)
                    this.moves[j] = RBYMove.EMPTY
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

    // DVs are essentially IVs but halved
    // 15 is the maximum DV you can have in a stat
    public override fun initIVs() {
        val maxDV = 15
        ivAtk = maxDV
        ivDef = maxDV
        ivSp  = maxDV
        ivSpe = maxDV

        ivSpA = maxDV   // used in calculateDvHP
        calculateDvHP() // generated by the result of other DVs
    }

    // Stat Experience is essentially EVs multiplied by 256
    // Each base stat can be completely filled with Stat Experience to the maximum
    public override fun initEVs() {
        val maxStatExp = 65535
        evHP  = maxStatExp
        evAtk = maxStatExp
        evDef = maxStatExp
        evSp  = maxStatExp
        evSpe = maxStatExp
    }

    override fun initHPStat() {
        initStatHP((floor((baseHp + ivHP) * 2
                + floor(sqrt(evHP.toDouble()) / 4)
                * level / 100)
                + level + 10).toInt())
    }

    override fun initOtherStats() {
        originalAttack = initOtherStatsFormula(baseAttack, ivAtk, evAtk, level)
        originalSpeed = initOtherStatsFormula(baseSpeed, ivSpe, evSpe, level)
        initStatAtk(originalAttack)
        initStatDef(initOtherStatsFormula(baseDefense, ivDef, evDef, level))
        initStatSpe(originalSpeed)
        initStatSp(initOtherStatsFormula(baseSpecial, ivSp, evSp, level))
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
            moves[0] -> decrementMoveOnePp()
            moves[1] -> decrementMoveTwoPp()
            moves[2] -> decrementMoveThreePp()
            moves[3] -> decrementMoveFourPp()
            else -> ppFailedToDeduct(this, move)
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

    fun validMove(move: RBYMove): Boolean {
        val i = moves.indexOf(move)
        if (i != -1)
            if (movesPp[i] > 0)
                return true
        return false
    }

    override fun resetAllCounters() {
        resetSleepCounter()
        resetToxicCounter()
        resetConfuseCounter()
        resetDisableCounter()
    }

    override fun removeNonVolatileStatusDebuff() {
        // rest bug
    }

    fun resetStat(stat: Stat) {
        when (stat) {
            Stat.ATTACK -> this.initStatAtk(originalAttack)
            Stat.SPEED -> this.initStatSpe(originalSpeed)
            else -> {}
        }
    }

    override fun resetAllPp() {
        for (i in 0 until 4)
            movesPp[i] = moves[i].maxPp
    }

    fun getAttackDamageMaxRoll(opponent: RBYPokemon, move: RBYMove): Int {
        return calcDamageEstimate(this, opponent, move, false)
    }

    fun getAttackDamageCritMaxRoll(opponent: RBYPokemon, move: RBYMove): Int {
        return calcDamageEstimate(this, opponent, move, true)
    }

    fun modifiedStat(stat: Stat): Int {
        return when (stat) {
            Stat.ATTACK -> modifyStat(statAtk, atkMod)
            Stat.DEFENSE -> modifyStat(statDef, defMod)
            Stat.SPEED -> modifyStat(statSpe, speMod)
            Stat.SPECIAL -> modifyStat(statSp, spMod)
            else -> 0
        }
    }

    private fun modifyStat(stat: Int, modifier: Int): Int {
        if (modifier == 0) return stat
        var modifiedStat = floor(multiplier(modifier) * stat.toDouble()).toInt()
        modifiedStat = modifiedStat.coerceIn(1, 999)
        return modifiedStat
    }

    fun multiplier(m: Int): Double {
        // https://gamefaqs.gamespot.com/gameboy/367023-pokemon-red-version/faqs/64175/stat-modifiers
        val statModifier: Array<Double> = arrayOf(0.25, 0.28, 0.33,
                0.4, 0.5, 0.66, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0)
        return statModifier[m + 6]
    }

    override val hiddenPowerType: Type = Type.NONE

    companion object {
        private fun initOtherStatsFormula(baseStat: Int, ivStat: Int, evStat: Int, level: Int): Int {
            // https://www.smogon.com/ingame/guides/rby_gsc_stats#howstatswork
            return floor((baseStat + ivStat) * 2
                    + floor((sqrt(evStat.toDouble() - 1) + 1) / 4)
                    * level / 100).toInt() + 5
        }
    }
}