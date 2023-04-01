package com.abrastat.rby

import com.abrastat.general.Move
import com.abrastat.general.MoveEffect
import com.abrastat.general.Type

enum class RBYMove
    constructor (override val type: Type,
                 override val maxPp: Int,
                 override val accuracy: Int,
                 override val basePower: Int,
                 override val effect: MoveEffect,
                 override var isAttack: Boolean)
    : Move {
    // this enum lists all the existing moves in rby
    // http://www.psypokes.com/rby/attacks.php
    ABSORB(Type.GRASS, 20, 255, 20, MoveEffect.ABSORB),
    ACID(Type.POISON, 30, 255, 40, MoveEffect.OPP_DEFENSEDROP1_10),
    ACID_ARMOR(Type.POISON, 40, MoveEffect.SELF_DEFENSERAISE2),
    AGILITY(Type.PSYCHIC, 30, MoveEffect.SELF_SPEEDRAISE2),
    AMNESIA(Type.PSYCHIC, 20, MoveEffect.SELF_SPECIALRAISE2),
    AURORA_BEAM(Type.ICE, 20, 255, 65, MoveEffect.OPP_ATTACKDROP1_10),

    BARRAGE(Type.NORMAL, 20, 216, 15, MoveEffect.MULTIHIT),
    BARRIER(Type.PSYCHIC, 30, MoveEffect.SELF_DEFENSERAISE2),
    BIDE(Type.NORMAL, 10, 255, MoveEffect.BIDE),
    BIND(Type.NORMAL, 20, 216, 15, MoveEffect.WRAP),
    BITE(Type.NORMAL, 25, 255, 60, MoveEffect.FLINCH10),
    BLIZZARD(Type.ICE, 5, 176, 120, MoveEffect.FRZ10),
    BODY_SLAM(Type.NORMAL, 15, 255, 85, MoveEffect.PRZ30),
    BONE_CLUB(Type.GROUND, 20, 216, 65, MoveEffect.FLINCH10),
    BONEMERANG(Type.GROUND, 10, 229, 50, MoveEffect.DOUBLEATTACK),
    BUBBLE(Type.WATER, 30, 255, 20, MoveEffect.OPP_SPEEDDROP1_10),
    BUBBLE_BEAM(Type.WATER, 20, 255, 65, MoveEffect.OPP_SPEEDDROP1_10),

    CLAMP(Type.WATER, 10, 216,35, MoveEffect.WRAP),
    COMET_PUNCH(Type.NORMAL, 15, 216, 18, MoveEffect.MULTIHIT),
    CONFUSE_RAY(Type.GHOST, 10, 255, MoveEffect.CONFUSION),
    CONFUSION(Type.PSYCHIC, 25, 255, 50, MoveEffect.CONFUSION10),
    CONSTRICT(Type.NORMAL, 35, 255, 10, MoveEffect.OPP_SPEEDDROP1_10),
    CONVERSION(Type.NORMAL, 30, MoveEffect.CONVERSION),
    COUNTER(Type.FIGHTING, 20, 255, MoveEffect.COUNTER),
    CRABHAMMER(Type.WATER, 10, 229, 100, MoveEffect.CRITRATE),
    CUT(Type.NORMAL, 30, 242, 50),

    DEFENSE_CURL(Type.NORMAL, 40, MoveEffect.SELF_DEFENSERAISE1),
    DIG(Type.GROUND, 10, 255, 100, MoveEffect.HIDE),
    DISABLE(Type.NORMAL, 20, 140, MoveEffect.DISABLE),
    DIZZY_PUNCH(Type.NORMAL, 10, 255, 70),
    DOUBLE_KICK(Type.FIGHTING, 30, 255, 30, MoveEffect.DOUBLEATTACK),
    DOUBLE_TEAM(Type.NORMAL, 15, MoveEffect.SELF_EVASIONRAISE1),
    DOUBLE_EDGE(Type.NORMAL, 15, 255, 100, MoveEffect.RECOIL25),
    DOUBLESLAP(Type.NORMAL, 10, 216, 15, MoveEffect.MULTIHIT),
    DRAGON_RAGE(Type.DRAGON, 10, 255, MoveEffect.DRAGONRAGE),
    DREAM_EATER(Type.PSYCHIC, 15, 255, 100, MoveEffect.DREAMEATER),
    DRILL_PECK(Type.FLYING, 20, 255, 80),

    EARTHQUAKE(Type.GROUND, 10, 255, 100),
    EGG_BOMB(Type.NORMAL, 10, 191, 100),
    EMBER(Type.FIRE, 25, 255, 40, MoveEffect.BRN10),
    EMPTY(Type.NONE, 0, 0, Int.MIN_VALUE, MoveEffect.NONE),
    EXPLOSION(Type.NORMAL, 5, 255, 340, MoveEffect.SELFDESTRUCT),   // half def == double power

    FIRE_BLAST(Type.FIRE, 5, 216, 120, MoveEffect.BRN30),
    FIRE_PUNCH(Type.FIRE, 15, 255, 75, MoveEffect.BRN10),
    FIRE_SPIN(Type.WATER, 15, 178,35, MoveEffect.WRAP),
    FISSURE(Type.GROUND, 5, 76, MoveEffect.ONEHITKO),
    FLAMETHROWER(Type.FIRE, 15, 255, 95, MoveEffect.BRN10),
    FLASH(Type.NORMAL, 20, 178, MoveEffect.OPP_ACCURACYDROP1),
    FLY(Type.FLYING, 15, 242, 70, MoveEffect.HIDE),
    FOCUS_ENERGY(Type.NORMAL, 30, MoveEffect.FOCUSENERGY),
    FURY_ATTACK(Type.NORMAL, 20, 216, 15, MoveEffect.MULTIHIT),
    FURY_SWIPES(Type.NORMAL, 15, 204, 10, MoveEffect.MULTIHIT),

    GLARE(Type.NORMAL, 30, 191, MoveEffect.PRZ),
    GROWL(Type.NORMAL, 40, 255, MoveEffect.OPP_ATTACKDROP1),
    GROWTH(Type.NORMAL, 40, MoveEffect.SELF_SPECIALRAISE1),
    GUILLOTINE(Type.NORMAL, 5, 76, MoveEffect.ONEHITKO),
    GUST(Type.NORMAL, 35, 255, 40),

    HARDEN(Type.NORMAL, 30, MoveEffect.SELF_DEFENSERAISE1),
    HAZE(Type.ICE, 30, MoveEffect.HAZE),
    HEADBUTT(Type.NORMAL, 15, 255, 70, MoveEffect.FLINCH30),
    HIGH_JUMP_KICK(Type.FIGHTING, 20, 229, 85, MoveEffect.HIGHJUMPKICK),
    HORN_ATTACK(Type.NORMAL, 25, 255, 65),
    HORN_DRILL(Type.NORMAL, 5, 76, MoveEffect.ONEHITKO),
    HYDRO_PUMP(Type.WATER, 5, 204, 120),
    HYPER_BEAM(Type.NORMAL, 5, 229, 150, MoveEffect.HYPERBEAM),
    HYPER_FANG(Type.NORMAL, 15, 229, 80, MoveEffect.FLINCH10),
    HYPNOSIS(Type.PSYCHIC, 20, 153, MoveEffect.SLEEP),

    ICE_BEAM(Type.ICE, 10, 255, 95, MoveEffect.FRZ10),
    ICE_PUNCH(Type.ICE, 15, 255, 75, MoveEffect.FRZ10),

    JUMP_KICK(Type.FIGHTING, 20, 242, 70, MoveEffect.HIGHJUMPKICK),

    KARATE_CHOP(Type.NORMAL, 25, 255, 55, MoveEffect.CRITRATE),
    KINESIS(Type.PSYCHIC, 15, 204, MoveEffect.OPP_ACCURACYDROP1),

    LEECH_LIFE(Type.BUG, 15, 255, 20, MoveEffect.ABSORB),
    LEECH_SEED(Type.GRASS, 10, 229, MoveEffect.LEECHSEED),
    LEER(Type.NORMAL, 30, 255, MoveEffect.OPP_DEFENSEDROP1),
    LICK(Type.GHOST, 30, 255, 20, MoveEffect.PRZ30),
    LIGHT_SCREEN(Type.PSYCHIC, 30, MoveEffect.LIGHTSCREEN),
    LOVELY_KISS(Type.NORMAL, 10, 191, MoveEffect.SLEEP),
    LOW_KICK(Type.FIGHTING, 20, 229, 50, MoveEffect.FLINCH30),

    MEDITATE(Type.PSYCHIC, 30, MoveEffect.SELF_ATTACKRAISE1),
    MEGA_DRAIN(Type.GRASS, 10, 255, 40, MoveEffect.ABSORB),
    MEGA_KICK(Type.NORMAL, 5, 191, 120),
    MEGA_PUNCH(Type.NORMAL, 20, 216, 80),
    METRONOME(Type.NORMAL, 10, MoveEffect.METRONOME),
    MIMIC(Type.NORMAL, 10, 255, MoveEffect.MIMIC),
    MINIMIZE(Type.NORMAL, 20, MoveEffect.SELF_EVASIONRAISE1),
    MIRROR_MOVE(Type.FLYING, 20, MoveEffect.MIRRORMOVE),
    MIST(Type.ICE, 30, MoveEffect.MIST),

    NIGHT_SHADE(Type.GHOST, 15, 255, MoveEffect.SEISMICTOSS),

    PAYDAY(Type.NORMAL, 20, 255, 40),   // effect doesn't matter for battle
    PECK(Type.FLYING, 35, 255, 35),
    PETAL_DANCE(Type.GRASS, 20, 255, 30, MoveEffect.THRASH),
    PIN_MISSILE(Type.BUG, 20, 216, 14, MoveEffect.MULTIHIT),
    POISON_GAS(Type.POISON, 40, 140, MoveEffect.PSN),
    POISON_STING(Type.POISON, 35, 255, 15, MoveEffect.PSN20),
    POISON_POWDER(Type.POISON, 35, 191, MoveEffect.PSN),
    POUND(Type.NORMAL, 35, 255, 40),
    PSYBEAM(Type.PSYCHIC, 20, 255, 65, MoveEffect.CONFUSION10),
    PSYCHIC(Type.PSYCHIC, 10, 255, 90, MoveEffect.OPP_SPECIALDROP1_30),
    PSYWAVE(Type.PSYCHIC, 15, 204, MoveEffect.PSYWAVE),

    QUICK_ATTACK(Type.NORMAL, 30, 255, 40, MoveEffect.QUICKATTACK),

    RAGE(Type.NORMAL, 20, 255, 20, MoveEffect.RAGE),
    RAZOR_LEAF(Type.GRASS, 25, 242, 25, MoveEffect.CRITRATE),
    RAZOR_WIND(Type.NORMAL, 10, 191, 80, MoveEffect.RAZORWIND),
    RECOVER(Type.NORMAL, 20, MoveEffect.RECOVER),
    REFLECT(Type.PSYCHIC, 20, MoveEffect.REFLECT),
    REST(Type.PSYCHIC, 10, MoveEffect.REST),
//    ROAR,   // no effect in trainer battles
    ROCK_SLIDE(Type.ROCK, 10, 229, 75),
    ROCK_THROW(Type.ROCK, 15, 165, 50),
    ROLLING_KICK(Type.FIGHTING, 15, 216, 60, MoveEffect.FLINCH30),

    SAND_ATTACK(Type.NORMAL, 15, 255, MoveEffect.OPP_ACCURACYDROP1),
    SCRATCH(Type.NORMAL, 35, 255, 40),
    SCREECH(Type.NORMAL, 10, 216, MoveEffect.OPP_DEFENSEDROP2),
    SEISMIC_TOSS(Type.NONE, 20, 255, MoveEffect.SEISMICTOSS),
    SELFDESTRUCT(Type.NORMAL, 5, 255, 260, MoveEffect.SELFDESTRUCT),    // half def == double power
    SHARPEN(Type.NORMAL, 30, MoveEffect.SELF_ATTACKRAISE1),
    SING(Type.NORMAL, 15, 140, MoveEffect.SLEEP),
    SKULLBASH(Type.NORMAL, 15, 255, 100, MoveEffect.RAZORWIND),
    SKY_ATTACK(Type.FLYING, 5, 229, 140, MoveEffect.RAZORWIND),
    SLAM(Type.NORMAL, 20, 191, 80),
    SLASH(Type.NORMAL, 20, 255, 70, MoveEffect.CRITRATE),
    SLEEP_POWDER(Type.GRASS, 15, 191, MoveEffect.SLEEP),
    SLUDGE(Type.POISON, 20, 255, 65, MoveEffect.PSN30),
    SMOG(Type.POISON, 20, 255, 20, MoveEffect.PSN30),
    SMOKESCREEN(Type.NORMAL, 20, 255, MoveEffect.OPP_ACCURACYDROP1),
    SOFTBOILED(Type.NORMAL, 10, MoveEffect.RECOVER),
    SOLARBEAM(Type.GRASS, 20, 255, 120, MoveEffect.RAZORWIND),
    SONICBOOM(Type.NORMAL, 20, 229, MoveEffect.SONICBOOM),
    SPIKE_CANNON(Type.NORMAL, 15, 255, 20, MoveEffect.MULTIHIT),
    SPLASH(Type.NORMAL, 40, MoveEffect.NONE),
    SPORE(Type.GRASS, 15, 255, MoveEffect.SLEEP),
    STOMP(Type.NORMAL, 20, 255, 65, MoveEffect.FLINCH30),
    STRENGTH(Type.NORMAL, 15, 255, 80),
    STRING_SHOT(Type.BUG, 40, 242, MoveEffect.OPP_SPEEDDROP1),
    STRUGGLE(Type.NONE, 999, 255, 50, MoveEffect.RECOIL25),
    STUN_SPORE(Type.GRASS, 30, 191, MoveEffect.PRZ),
    SUBMISSION(Type.FIGHTING, 20, 204, 80, MoveEffect.RECOIL25),
    SUBSTITUTE(Type.NORMAL, 10, MoveEffect.SUBSTITUTE),
    SUPER_FANG(Type.NORMAL, 10, 229, MoveEffect.SUPERFANG),
    SUPERSONIC(Type.NORMAL, 20, 140, MoveEffect.CONFUSION),
    SURF(Type.WATER, 15, 255, 95),
    SWIFT(Type.NORMAL, 20, Int.MAX_VALUE, 60, MoveEffect.SWIFT),
    SWORDS_DANCE(Type.NORMAL, 30, MoveEffect.SELF_ATTACKRAISE2),

    TACKLE(Type.NORMAL, 35, 242, 35),
    TAIL_WHIP(Type.NORMAL, 30, 255, MoveEffect.OPP_DEFENSEDROP1),
    TAKE_DOWN(Type.NORMAL, 20, 216, 90, MoveEffect.RECOIL25),
//    TELEPORT,   // no effect in trainer battles
    THRASH(Type.NORMAL, 20, 255, 90, MoveEffect.THRASH),
    THUNDER(Type.ELECTRIC, 10, 178, 120, MoveEffect.PRZ10),
    THUNDER_WAVE(Type.ELECTRIC, 20, 255, MoveEffect.PRZ),
    THUNDERBOLT(Type.ELECTRIC, 15, 255, 95, MoveEffect.PRZ10),
    THUNDERPUNCH(Type.ELECTRIC, 15, 255, 75, MoveEffect.PRZ10),
    THUNDERSHOCK(Type.ELECTRIC, 30, 255, 40, MoveEffect.PRZ10),
    TOXIC(Type.POISON, 10, 216, MoveEffect.TOXIC),
    TRANSFORM(Type.NORMAL, 10, MoveEffect.TRANSFORM),
    TRI_ATTACK(Type.NORMAL, 10, 255, 80),
    TWINEEDLE(Type.BUG, 20, 255, 25, MoveEffect.TWINNEEDLE),

    VICEGRIP(Type.NORMAL, 30, 255, 55),
    VINE_WHIP(Type.GRASS, 10, 255, 35),

    WATER_GUN(Type.WATER, 25, 255, 40),
    WATERFALL(Type.WATER, 15, 255, 80),
//    WHIRLWIND,  // no effect in trainer battles
    WING_ATTACK(Type.FLYING, 35, 255, 35),
    WITHDRAW(Type.WATER, 40, MoveEffect.SELF_DEFENSERAISE1),
    WRAP(Type.NORMAL, 20, 229, 15, MoveEffect.WRAP)
    ;

    // attack with secondary effect
    constructor(type: Type, pp: Int, accuracy: Int, basePower: Int, moveEffect: MoveEffect):
            this(type, pp, accuracy, basePower, moveEffect, true)

    // attack
    constructor(type: Type, pp: Int, accuracy: Int, basePower: Int):
            this(type, pp, accuracy, basePower, MoveEffect.NONE, true)

    // move with acc check
    constructor(type: Type, pp: Int, accuracy: Int, moveEffect: MoveEffect):
            this(type, pp, accuracy, 0, moveEffect, false)

    // move with no acc check
    constructor(type: Type, pp: Int, moveEffect: MoveEffect):
            this(type, pp, Int.MAX_VALUE, 0, moveEffect, false)

    val isPhysical: Boolean
        get() = isPhysical(type)

    override fun toString(): String {
        return super.toString().replace("_", " ")
    }

    companion object {
        fun isPhysical(type: Type): Boolean {
            return when (type) {
                Type.BUG,
                Type.FIGHTING,
                Type.FLYING,
                Type.GHOST,
                Type.GROUND,
                Type.NONE,
                Type.NORMAL,
                Type.POISON,
                Type.ROCK,
                Type.STEEL,
                -> true

                else -> false
            }
        }
    }
}