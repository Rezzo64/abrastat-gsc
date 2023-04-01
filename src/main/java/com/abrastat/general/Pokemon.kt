package com.abrastat.general

import com.abrastat.general.Messages.Companion.logStatDrop
import com.abrastat.general.Messages.Companion.logStatRaise
import com.abrastat.general.Messages.Companion.logStatSharplyDrop
import com.abrastat.general.Messages.Companion.logStatSharplyRaise
import com.abrastat.general.Messages.Companion.logStatWontGoAnyHigher
import com.abrastat.general.Messages.Companion.logStatWontGoAnyLower
import com.abrastat.general.Messages.Companion.notImplementedYet
import com.abrastat.general.Messages.Companion.statusFailed
import com.abrastat.gsc.GSCPokemon
import org.slf4j.LoggerFactory
import java.util.concurrent.ThreadLocalRandom

abstract class Pokemon(species: String, builder: Builder<*>) : Species(species) {
    var lastDamageTaken = 0
    private val nickname: String
    @JvmField
    val gender: Gender
    private val ability // PLACEHOLDER - this will be handled by children, delete later
            : Ability?
    var ivHP: Int
        protected set
    var ivAtk: Int
        protected set
    var ivDef: Int
        protected set
    var ivSpA: Int
        protected set
    var ivSpD: Int
        protected set
    var ivSpe: Int
        protected set
    var ivSp: Int
        protected set
    var evHP: Int = 0
        protected set
    var evAtk: Int = 0
        protected set
    var evDef: Int = 0
        protected set
    var evSpA: Int = 0
        protected set
    var evSpD: Int = 0
        protected set
    var evSpe: Int = 0
        protected set
    var evSp: Int = 0
        protected set
    var level: Int = 0
        protected set
    @JvmField
    var heldItem: Item
    var nonVolatileStatus = Status.HEALTHY
        private set
    var volatileStatus: HashSet<Status> = hashSetOf()
    var statHP = 0
        private set
    var statAtk = 0
        private set
    var statDef = 0
        private set
    var statSpA = 0
        private set
    var statSpD = 0
        private set
    var statSpe = 0
        private set
    var statSp = 0
        private set
    var atkMod = 0
    var defMod = 0
    var spAMod = 0
    var spDMod = 0
    var speMod = 0
    var spMod  = 0
    var accMod = 0
    var evaMod = 0
    var id = 0 // used for retrieving db instance
    var startingHP = 0
        protected set
    var currentHP = 0
        protected set
    abstract val hiddenPowerType: Type
    var activeBehaviour: PlayerBehaviour = PlayerBehaviour.JUST_ATTACK

    // all counters below to be handled incrementally (for consistency)
    var sleepCounter = 0
    var toxicCounter = 0
    var confuseCounter = 0
    var disableCounter = 0
    var encoreCounter = 0
    var perishCounter = 0

    fun applyHeal(healAmount: Int) {
        if (currentHP + healAmount >= statHP) {
            currentHP = statHP
        } else {
            currentHP += healAmount
        }
    }

    fun applyDamage(damage: Int) {
        lastDamageTaken = damage
        currentHP -= damage
    }

    fun incrementEncoreCounter() {
        encoreCounter++
    }

    enum class Gender {
        MALE, FEMALE, BOTH, NONE;

        // TODO: 19/08/2021 read & create getters + setters to read JSON
        override fun toString(): String {
            return when (this) {
                MALE -> "M"
                FEMALE -> "F"
                BOTH -> "B"
                NONE -> "N"
            }
        }
    }

    init {
        nickname = builder.nickname.toString()
        ivHP = builder.ivHP
        ivAtk = builder.ivAtk
        ivDef = builder.ivDef
        ivSpA = builder.ivSpA
        ivSpD = builder.ivSpD
        ivSpe = builder.ivSpe
        ivSp = builder.ivSp
        evHP = builder.evHP
        evAtk = builder.evAtk
        evDef = builder.evDef
        evSpA = builder.evSpA
        evSpD = builder.evSpD
        evSpe = builder.evSpe
        evSp = builder.evSp
        level = builder.level
        heldItem = builder.heldItem
        gender = Gender.NONE // TODO: currently unused because no gender ratio values in json
        ability = builder.ability
    }

    abstract class Builder<T : Move> protected constructor() {
        var nickname: String? = null;
        var ivHP = 31
        var ivAtk = 31
        var ivDef = 31
        var ivSpA = 31
        var ivSpD = 31
        var ivSpe = 31 // default max
        var ivSp = 31
        var evHP = 0
        var evAtk = 0
        var evDef = 0
        var evSpA = 0
        var evSpD = 0
        var evSpe = 0 // default min
        var evSp = 0
        var level = 100 // default max
        val ability: Ability? = null
        var heldItem: Item = Item.NONE
        private val gender: Gender? = null
        fun nickname(name: String): Builder<T> {
            nickname = name
            return this
        }

        abstract fun moves(move1: T): Builder<T>
        abstract fun moves(move1: T, move2: T): Builder<T>
        abstract fun moves(move1: T, move2: T, move3: T): Builder<T>
        abstract fun moves(move1: T, move2: T, move3: T, move4: T): Builder<T>
//        abstract fun moves(moves: Array<T>): Builder<T>
        abstract fun hiddenPowerType(type: Type): Builder<T>
        fun ivs(ivHP: Int, ivAtk: Int, ivDef: Int, ivSpA: Int, ivSpD: Int, ivSpe: Int, ivSp: Int = 0): Builder<T> {
            this.ivHP = ivHP
            this.ivAtk = ivAtk
            this.ivDef = ivDef
            this.ivSpA = ivSpA
            this.ivSpD = ivSpD
            this.ivSpe = ivSpe
            this.ivSp = ivSp
            return this
        }

        fun evs(evHP: Int, evAtk: Int, evDef: Int, evSpA: Int, evSpD: Int, evSpe: Int, evSp: Int = 0): Builder<T> {
            this.evHP = evHP
            this.evAtk = evAtk
            this.evDef = evDef
            this.evSpA = evSpA
            this.evSpD = evSpD
            this.evSpe = evSpe
            this.evSp = evSp
            return this
        }

        fun level(level: Int): Builder<T> {
            this.level = level
            return this
        }

        // TODO
        fun gender(): Builder<T> {
            return this
        }

        fun item(item: Item): Builder<T> {
            heldItem = item
            return this
        }

        abstract fun build(): Pokemon
    }

    protected abstract fun initIVs()
    protected abstract fun initEVs()
    protected abstract fun initHPStat()
    protected abstract fun initOtherStats()

    // RBY and GSC method
    fun setDvSpecial(dvSpecial: Int) {
        ivSpA = dvSpecial
        ivSpD = dvSpecial
    }

    fun calculateDvHP() {
        var dvHP = 0
        if (ivAtk % 2 == 1) {
            dvHP += 8
        }
        if (ivDef % 2 == 1) {
            dvHP += 4
        }
        if (ivSpe % 2 == 1) {
            dvHP += 2
        }
        if (ivSpA % 2 == 1) {
            dvHP += 1
        }
        ivHP = dvHP
    }

    protected fun initStatHP(hp: Int) {
        statHP = hp
    }

    fun initStatAtk(atk: Int) {
        statAtk = atk
    }

    protected fun initStatDef(def: Int) {
        statDef = def
    }

    protected fun initStatSpA(spa: Int) {
        statSpA = spa
    }

    protected fun initStatSpD(spd: Int) {
        statSpD = spd
    }

    protected fun initStatSp(sp: Int) {
        statSp = sp
    }

    fun initStatSpe(spe: Int) {
        statSpe = spe
    }

    abstract var moveOnePp: Int
        protected set

    protected abstract fun decrementMoveOnePp()
    abstract var moveTwoPp: Int
        protected set

    protected abstract fun decrementMoveTwoPp()
    abstract var moveThreePp: Int
        protected set

    protected abstract fun decrementMoveThreePp()
    abstract var moveFourPp: Int
        protected set

    protected abstract fun decrementMoveFourPp()
    abstract fun getMovePp(moveIndex: Int): Int
    abstract fun decrementMovePp(move: Move)
    abstract val movesPp: IntArray
    fun raiseStat(stat: Stat) {
        when (stat) {
            Stat.ATTACK -> atkMod = checkModUpperLimit(atkMod, stat)
            Stat.DEFENSE -> defMod = checkModUpperLimit(defMod, stat)
            Stat.SPECIALATTACK -> spAMod = checkModUpperLimit(spAMod, stat)
            Stat.SPECIALDEFENSE -> spDMod = checkModUpperLimit(spDMod, stat)
            Stat.SPEED -> speMod = checkModUpperLimit(speMod, stat)
            Stat.SPECIAL -> spMod = checkModUpperLimit(spMod, stat)
            Stat.ACCURACY -> accMod = checkModUpperLimit(accMod, stat)
            Stat.EVASION -> evaMod = checkModUpperLimit(evaMod, stat)
        }
    }

    fun dropStat(stat: Stat) {
        when (stat) {
            Stat.ATTACK -> atkMod = checkModLowerLimit(atkMod, stat)
            Stat.DEFENSE -> defMod = checkModLowerLimit(defMod, stat)
            Stat.SPECIALATTACK -> spAMod = checkModLowerLimit(spAMod, stat)
            Stat.SPECIALDEFENSE -> spDMod = checkModLowerLimit(spDMod, stat)
            Stat.SPEED -> speMod = checkModLowerLimit(speMod, stat)
            Stat.SPECIAL -> spMod = checkModLowerLimit(spMod, stat)
            Stat.ACCURACY -> accMod = checkModLowerLimit(accMod, stat)
            Stat.EVASION -> evaMod = checkModLowerLimit(evaMod, stat)
        }
    }

    fun raiseStatSharp(stat: Stat) {
        when (stat) {
            Stat.ATTACK -> atkMod = checkSharpModUpperLimit(atkMod, stat)
            Stat.DEFENSE -> defMod = checkSharpModUpperLimit(defMod, stat)
            Stat.SPECIALATTACK -> spAMod = checkSharpModUpperLimit(spAMod, stat)
            Stat.SPECIALDEFENSE -> spDMod = checkSharpModUpperLimit(spDMod, stat)
            Stat.SPEED -> speMod = checkSharpModUpperLimit(speMod, stat)
            Stat.SPECIAL -> spMod = checkSharpModUpperLimit(spMod, stat)
            Stat.ACCURACY -> accMod = checkSharpModUpperLimit(accMod, stat)
            Stat.EVASION -> evaMod = checkSharpModUpperLimit(evaMod, stat)
        }
    }

    fun dropStatSharp(stat: Stat) {
        when (stat) {
            Stat.ATTACK -> atkMod = checkSharpModLowerLimit(atkMod, stat)
            Stat.DEFENSE -> defMod = checkSharpModLowerLimit(defMod, stat)
            Stat.SPECIALATTACK -> spAMod = checkSharpModLowerLimit(spAMod, stat)
            Stat.SPECIALDEFENSE -> spDMod = checkSharpModLowerLimit(spDMod, stat)
            Stat.SPEED -> speMod = checkSharpModLowerLimit(speMod, stat)
            Stat.SPECIAL -> spMod = checkSharpModLowerLimit(spMod, stat)
            Stat.ACCURACY -> accMod = checkSharpModLowerLimit(accMod, stat)
            Stat.EVASION -> evaMod = checkSharpModLowerLimit(evaMod, stat)
        }
    }

    fun checkModUpperLimit(mod: Int, stat: Stat): Int {
        return if (mod + 1 < 6) {
            logStatRaise(this, stat)
            mod + 1
        } else {
            logStatWontGoAnyHigher(this, stat)
            mod
        }
    }

    fun checkModLowerLimit(mod: Int, stat: Stat): Int {
        return if (mod - 1 > -6) {
            logStatDrop(this, stat)
            mod - 1
        } else {
            logStatWontGoAnyLower(this, stat)
            mod
        }
    }

    fun checkSharpModUpperLimit(mod: Int, stat: Stat): Int {
        return if (mod + 1 < 6) {
            logStatSharplyRaise(this, stat)
            (mod + 2).coerceAtMost(6)
        } else {
            logStatWontGoAnyHigher(this, stat)
            mod
        }
    }

    fun checkSharpModLowerLimit(mod: Int, stat: Stat): Int {
        return if (mod - 1 > -6) {
            logStatSharplyDrop(this, stat)
            (mod - 2).coerceAtLeast(-6)
        } else {
            logStatWontGoAnyLower(this, stat)
            mod
        }
    }

    abstract val moves: Array<out Move>
    fun applyNonVolatileStatus(status: Status) {
        nonVolatileStatus = status
        applyNonVolatileStatusDebuff(status)
    }

    val isFainted: Unit
        get() {
            nonVolatileStatus = Status.FAINT
        }

    fun removeNonVolatileStatus() {
        removeNonVolatileStatusDebuff()
        nonVolatileStatus = Status.HEALTHY
    }

    fun getVolatileStatus(): Array<Status> {
        return if (volatileStatus.isNotEmpty()) {
            volatileStatus.toTypedArray()
        } else {
            arrayOf()
        }
    }

    fun applyVolatileStatus(status: Status) {
        if (!volatileStatus.contains(status)) {
            volatileStatus.add(status)
        } else {
            statusFailed(this, status)
        }
    }

    fun removeVolatileStatus(status: Status) {
        if (volatileStatus.contains(status)) {
            volatileStatus.remove(status)
        }
    }

    fun clearVolatileStatus() {
        volatileStatus.clear()
    }

    fun resetMods() {
        atkMod = 0
        defMod = 0
        spAMod = 0
        spDMod = 0
        speMod = 0
        spMod  = 0
        accMod = 0
        evaMod = 0
    }

    fun decrementSleepCounter() {
        sleepCounter--
    }

    fun incrementToxicCounter() {
        toxicCounter++
    }

    fun incrementConfuseCounter() {
        confuseCounter++
    }

    fun incrementDisableCounter() {
        disableCounter++
    }

    fun resetSleepCounter() {
        sleepCounter = 0
    }

    fun resetToxicCounter() {
        toxicCounter = 0
    }

    fun resetConfuseCounter() {
        confuseCounter = 0
    }

    fun resetDisableCounter() {
        disableCounter = 0
    }

    fun resetPerishCounter() {
        perishCounter = 0
    }

    fun incrementPerishCounter() {
        perishCounter++
    }

    abstract fun resetAllCounters()
    fun applyNonVolatileStatusDebuff(status: Status) {
        when (status) {
            Status.PARALYSIS -> statSpe /= 4
            Status.BURN -> statAtk /= 2
            else -> notImplementedYet(status.toString())
        }
    }

    abstract fun removeNonVolatileStatusDebuff()
    fun setSleepCounter(minSleep: Int, maxSleep: Int) {
        sleepCounter = if (maxSleep > minSleep) {
            ThreadLocalRandom.current().nextInt(minSleep, maxSleep)
        } else {
            minSleep
        }
    }

    fun resetStatHp() {
        currentHP = startingHP
    }

    abstract fun resetAllPp()
    override fun toString(): String {
        return species!!
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(GSCPokemon::class.java)
    }
}