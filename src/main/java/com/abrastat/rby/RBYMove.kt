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
    ABSORB(Type.GRASS, 32, 255, 20, MoveEffect.ABSORB),
    ACID(Type.POISON, 48, 255, 40, MoveEffect.ACIDSPDEFDROP),
    ACID_ARMOR(Type.POISON, 61, MoveEffect.SELF_DEFENSERAISE2),
    //    AEROBLAST,
    AGILITY(Type.PSYCHIC, 48, MoveEffect.SELF_SPEEDRAISE2),
    AMNESIA(Type.PSYCHIC, 32, MoveEffect.SELF_SPECIALRAISE2),
    //    ANCIENTPOWER,
    ATTRACT(Type.NORMAL, 24, 255, MoveEffect.ATTRACT),
    //    AURORA_BEAM,

    //    BARRAGE,
    BARRIER(Type.PSYCHIC, 48, MoveEffect.SELF_DEFENSERAISE2),
    //    BATON_PASS,
    BEAT_UP(Type.DARK, 16, 255, 10, MoveEffect.BEATUP),
    BELLY_DRUM(Type.NORMAL, 16, MoveEffect.BELLYDRUM),
    //    BIDE,
    BIND(Type.NORMAL, 20, 216, 15, MoveEffect.WRAP),
    //    BITE,
    BLIZZARD(Type.ICE, 8, 176, 120, MoveEffect.FRZ10),
    BODY_SLAM(Type.NORMAL, 24, 255, 85, MoveEffect.PRZ30),
    //    BONE_CLUB,
    //    BONE_RUSH,
    //    BONEMERANG,
    //    BUBBLE,
    BUBBLE_BEAM(Type.WATER, 20, 255, 65, MoveEffect.OPP_SPEEDDROP1),

    CHARM(Type.NORMAL, 32, 255, MoveEffect.OPP_ATTACKDROP2),
    CLAMP(Type.WATER, 15, 216,35, MoveEffect.WRAP),
    //    COMET_PUNCH,
    CONFUSE_RAY(Type.GHOST, 16, 255, MoveEffect.OPP_CONFUSION),
    //    CONFUSION,
    //    CONSTRICT,
    //    CONVERSION,
    //    CONVERSION2,
    //    COTTON_SPORE,
    COUNTER(Type.FIGHTING, 20, 255, MoveEffect.COUNTER),
    //    CRABHAMMER,
    CROSS_CHOP(Type.FIGHTING, 8, 204, 100, MoveEffect.CRITRATE),
    CRUNCH(Type.DARK, 24, 255, 80, MoveEffect.CRUNCHSPDEFDROP),
    CURSE(Type.NONE, 16, MoveEffect.CURSE),
    //    CUT,

    //    DEFENSE_CURL,
    DESTINY_BOND(Type.GHOST, 8, MoveEffect.DESTINYBOND),
    //    DETECT,
    //    DIG,
    //    DISABLE,
    //    DIZZY_PUNCH,
    DOUBLE_KICK(Type.FIGHTING, 30, 255, 30, MoveEffect.DOUBLEATTACK),
    //    DOUBLE_TEAM,
    DOUBLE_EDGE(Type.NORMAL, 24, 255, 100, MoveEffect.RECOIL25),
    //    DOUBLESLAP,
    //    DRAGON_RAGE,
    //    DRAGONBREATH,
    DREAM_EATER(Type.PSYCHIC, 24, 255, 100, MoveEffect.DREAMEATER),
    DRILL_PECK(Type.FLYING, 32, 255, 80),
    DYNAMICPUNCH(Type.FIGHTING, 8, 127, 100, MoveEffect.CONFUSE100),

    EARTHQUAKE(Type.GROUND, 16, 255, 100),
    //    EGG_BOMB,
    //    EMBER,
    EMPTY(Type.NONE, 0, MoveEffect.NONE),
    ENCORE(Type.NORMAL, 8, 255, MoveEffect.ENCORE),
    //    ENDURE,
    EXPLOSION(Type.NORMAL, 8, 255, 170, MoveEffect.SELFDESTRUCT),
    //    EXTREMESPEED,

    //    FAINT_ATTACK,
    //    FALSE_SWIPE,
    FIRE_BLAST(Type.FIRE, 8, 216, 120, MoveEffect.BRN30),
    FIRE_PUNCH(Type.FIRE, 24, 255, 75, MoveEffect.BRN10),
    FIRE_SPIN(Type.WATER, 15, 216,35, MoveEffect.WRAP),
    //    FISSURE,
    FLAIL(Type.NORMAL, 24, 255, 1, MoveEffect.FLAIL),
    //    FLAME_WHEEL,
    FLAMETHROWER(Type.FIRE, 24, 255, 95, MoveEffect.BRN10),
    //    FLASH,
    //    FLY,
    FOCUS_ENERGY(Type.NORMAL, 48, MoveEffect.FOCUSENERGY),
    //    FORESIGHT,
    //    FRUSTRATION,
    //    FURY_ATTACK,
    //    FURY_CUTTER,
    //    FURY_SWIPES,
    FUTURE_SIGHT(Type.PSYCHIC, 24, 229, 80, MoveEffect.FUTURESIGHT),

    GIGA_DRAIN(Type.GRASS, 8, 255, 60, MoveEffect.ABSORB),
    GLARE(Type.NORMAL, 30, 191, MoveEffect.PRZ),
    GROWL(Type.NORMAL, 32, 255, MoveEffect.OPP_ATTACKDROP1),
    GROWTH(Type.NORMAL, 61, MoveEffect.SELF_SPECIALRAISE1),
    //    GUILLOTINE,
    //    GUST,

    //    HARDEN,
    //    HAZE,
    //    HEADBUTT,
    //    HEAL_BELL,
    HIGH_JUMP_KICK(Type.FIGHTING, 10, 229, 85, MoveEffect.HIGHJUMPKICK),
    //    HIDDEN_POWER,
    //    HORN_ATTACK,
    //    HORN_DRILL,
    HYDRO_PUMP(Type.WATER, 8, 204, 120),
    HYPER_BEAM(Type.NORMAL, 8, 229, 150, MoveEffect.HYPERBEAM),
    //    HYPER_FANG,
    HYPNOSIS(Type.PSYCHIC, 32, 153, MoveEffect.SLEEP),

    ICE_BEAM(Type.ICE, 16, 255, 95, MoveEffect.FRZ10),
    ICE_PUNCH(Type.ICE, 24, 255, 75, MoveEffect.FRZ10),
    //    ICY_WIND,
    //    IRON_TAIL,

    //    JUMP_KICK,

    //    KARATE_CHOP,
    //    KINESIS,

    //    LEECH_LIFE,
    //    LEECH_SEED,
    //    LEER,
    //    LICK,
    //    LIGHT_SCREEN,
    //    LOCK_ON,
    LOVELY_KISS(Type.NORMAL, 191, MoveEffect.SLEEP),
    LOW_KICK(Type.FIGHTING, 20, 229, 50, MoveEffect.FLINCH30),

    //    MACH_PUNCH,
    //    MAGNITUDE,
    //    MEAN_LOOK,
    MEDITATE(Type.PSYCHIC, 61, MoveEffect.SELF_ATTACKRAISE1),
    MEGA_DRAIN(Type.GRASS, 10, 255, 40, MoveEffect.ABSORB),
    //    MEGA_KICK,
    //    MEGA_PUNCH,
    MEGAHORN(Type.BUG, 16, 216, 120),
    //    METAL_CLAW,
    //    METRONOME,
    MILK_DRINK(Type.NORMAL, 16, MoveEffect.RECOVER),
    MIMIC(Type.NORMAL, 10, 255, MoveEffect.MIMIC),
    //    MIND_READER,
    //    MINIMIZE,
    //    MIRROR_COAT,
    MIRROR_MOVE(Type.FLYING, 20, MoveEffect.MIRRORMOVE),
    //    MIST,
    //    MOONLIGHT,
    //    MORNING_SUN,
    //    MUD_SLAP,

    NIGHT_SHADE(Type.NONE, 24, 255, 1, MoveEffect.SEISMICTOSS),
    NIGHTMARE(Type.GHOST, 24, 255, MoveEffect.NIGHTMARE),

    //    OCTAZOOKA,
    //    OUTRAGE,

    //    PAIN_SPLIT,
    //    PAYDAY,
    PECK(Type.FLYING, 35, 255, 35),
    //    PERISH_SONG,
    //    PETAL_DANCE,
    //    PIN_MISSILE,
    //    POISON_GAS,
    //    POISON_STING,
    //    POISON_POWDER,
    //    POUND,
    //    POWDER_SNOW,
    PRESENT(Type.NORMAL, 24, 229, 1, MoveEffect.PRESENT),
    PROTECT(Type.NORMAL, 16, MoveEffect.PROTECT),
    //    PSYBEAM,
    //    PSYCHUP,
    PSYCHIC(Type.PSYCHIC, 16, 255, 90, MoveEffect.OPP_SPECIALDROP1),
    PSYWAVE(Type.PSYCHIC, 15, 204, MoveEffect.PSYWAVE),
    //    PURSUIT,

    //    QUICK_ATTACK,

    //    RAGE,
    //    RAIN_DANCE,
    //    RAPID_SPIN,
    //    RAZOR_LEAF,
    //    RAZOR_WIND,
    RECOVER(Type.NORMAL, 32, MoveEffect.RECOVER),
    REFLECT(Type.PSYCHIC, 20, MoveEffect.REFLECT),
    REST(Type.PSYCHIC, 16, MoveEffect.REST),
    RETURN(Type.NORMAL, 32, 255, 102, MoveEffect.RETURN),
    REVERSAL(Type.FIGHTING, 24, 255, 1, MoveEffect.FLAIL),
    //    ROAR,
    ROCK_SLIDE(Type.ROCK, 16, 229, 75, MoveEffect.NONE),
    //    ROCK_SMASH,
    //    ROCK_THROW,
    //    ROLLING_KICK,
    ROLLOUT(Type.ROCK, 32, 229, 30, MoveEffect.ROLLOUT),

    SACRED_FIRE(Type.FIRE, 8, 242, 100, MoveEffect.SACREDFIRE),
    SAFEGUARD(Type.NORMAL, 40, MoveEffect.SAFEGUARD),
    SAND_ATTACK(Type.GROUND, 15, 255, MoveEffect.OPP_ACCURACYDROP1),
    //    SANDSTORM,
    //    SCARY_FACE,
    //    SCRATCH,
    SCREECH(Type.NORMAL, 40, 216, MoveEffect.OPP_DEFENSEDROP2),
    SEISMIC_TOSS(Type.NONE, 32, 255, 1, MoveEffect.SEISMICTOSS),
    SELFDESTRUCT(Type.NORMAL, 8, 255, 130, MoveEffect.SELFDESTRUCT),
    SHADOW_BALL(Type.GHOST, 24, 255, 80, MoveEffect.CRUNCHSPDEFDROP),
    SHARPEN(Type.NORMAL, 48, MoveEffect.SELF_ATTACKRAISE1),
    SING(Type.NORMAL, 24, 140, MoveEffect.SLEEP),
    //    SKETCH,
    //    SKY_ATTACK,
    //    SLAM,
    //    SLASH,
    SLEEP_POWDER(Type.GRASS, 24, 191, MoveEffect.SLEEP),
    SLEEP_TALK(Type.NORMAL, 16, MoveEffect.SLEEPTALK),
    SLUDGE(Type.POISON, 20, 255, 65, MoveEffect.PSN40),
    SLUDGE_BOMB(Type.POISON, 16, 255, MoveEffect.PSN30),
    //    SMOG,
    //    SMOKESCREEN,
    //    SNORE,
    SOFTBOILED(Type.NORMAL, 10, MoveEffect.RECOVER),
    //    SOLARBEAM,
    //    SONICBOOM,
    //    SPARK,
    //    SPIDER_WEB,
    //    SPIKE_CANNON,
    //    SPIKES,
    //    SPITE,
    //    SPLASH,
    SPORE(Type.GRASS, 15, 255, MoveEffect.SLEEP),
    //    STEEL_WING,
    //    STOMP,
    //    STRENGTH,
    //    STRING_SHOT,
    STRUGGLE(Type.NONE, 999, 255, 50, MoveEffect.STRUGGLE),
    STUN_SPORE(Type.GRASS, 48, 191, MoveEffect.SLEEP),
    SUBMISSION(Type.FIGHTING, 20, 204, 80, MoveEffect.RECOIL25),
    SUBSTITUTE(Type.NORMAL, 16, MoveEffect.SUBSTITUTE),
    //    SUNNY_DAY,
    SUPER_FANG(Type.NORMAL, 10, 229, MoveEffect.SUPERFANG),
    //    SUPERSONIC,
    SURF(Type.WATER, 24, 255, 95),
    SWAGGER(Type.NORMAL, 24, 229, MoveEffect.SWAGGER),
    //    SWEET_KISS,
    //    SWEET_SCENT,
    //    SWIFT,
    SWORDS_DANCE(Type.NORMAL, 48, MoveEffect.SELF_ATTACKRAISE2),
    //    SYNTHESIS,

    //    TACKLE,
    //    TAIL_WHIP,
    //    TAKE_DOWN,
    //    TELEPORT,
    THIEF(Type.DARK, 16, 255, 40, MoveEffect.THIEF),
    //    THRASH,
    THUNDER(Type.ELECTRIC, 10, 178, 120, MoveEffect.PRZ10),
    THUNDER_WAVE(Type.ELECTRIC, 32, 255, MoveEffect.PRZ),
    THUNDERBOLT(Type.ELECTRIC, 24, 255, 95, MoveEffect.PRZ10),
    //    THUNDERPUNCH,
    //    THUNDERSHOCK,
    TOXIC(Type.POISON, 16, 255, MoveEffect.TOXIC),
    TRANSFORM(Type.NORMAL, 10, MoveEffect.TRANSFORM),
    //    TRI_ATTACK,
    //    TRIPLE_KICK,
    TWINEEDLE(Type.BUG, 20, 255, 20, MoveEffect.TWINNEEDLE),
    //    TWISTER,

    //    VICEGRIP,
    //    VINE_WHIP,
    //    VITAL_THROW,

    //    WATER_GUN,
    //    WATERFALL,
    //    WHIRLPOOL,
    //    WHIRLWIND,
    //    WING_ATTACK,
    WRAP(Type.NORMAL, 20, 229, 15, MoveEffect.WRAP),
    
    ZAP_CANNON(Type.ELECTRIC, 8, 127, 100, MoveEffect.PRZ100);

//            private val type: Type
//            private val maxPp: Int
//            private val accuracy: Int
//            private val basePower: Int = 0
//            private val moveEffect: MoveEffect = MoveEffect.NONE
//            override var isAttack: Boolean = true
        // Attack constructor
        constructor(type: Type, pp: Int, accuracy: Int, basePower: Int, moveEffect: MoveEffect) : this(type, pp, accuracy, basePower, moveEffect, true)

        // Attack constructor for moves with no secondary effects
        constructor(type: Type, pp: Int, accuracy: Int, basePower: Int) : this(type, pp, accuracy, basePower, MoveEffect.NONE, true)

        // Status constructor for moves that DO check accuracy
        constructor(type: Type, pp: Int, accuracy: Int, moveEffect: MoveEffect) : this(type, pp, accuracy, 0, moveEffect, false)

        // Status constructor for moves that DON'T check accuracy
        constructor(type: Type, pp: Int, moveEffect: MoveEffect) : this(type, pp, 255, 0, moveEffect, false)

        // override val moveName: String = name

//        override val type: Type = type

//        override fun maxPp(): Int {
//            return maxPp()
//        }

//        override fun accuracy(): Int {
//            return accuracy
//        }

//        override fun basePower(): Int {
//            return basePower
//        }

//        override fun effect(): MoveEffect {
//            return moveEffect
//        }

        val isPhysical: Boolean
            get() = isPhysical(type)

        override fun toString(): String {
            return super.toString().replace("_", " ")
        }

        companion object {
            fun isPhysical(type: Type): Boolean {
                return when (type) {
                    Type.NORMAL, Type.FIGHTING, Type.FLYING, Type.ROCK, Type.STEEL, Type.GHOST, Type.BUG, Type.POISON, Type.GROUND, Type.NONE -> true
                    else -> {
                        false
                    }
                }
            }
        }
}