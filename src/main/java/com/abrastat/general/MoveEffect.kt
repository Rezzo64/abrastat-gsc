package com.abrastat.general

// Chance of occurring out of a factor of 256.
// 0 indicates that the effect will always occur.
enum class MoveEffect(private val target: Target, private val effectChance: Int) {
    // none
    NONE(Target.NONE, 0),

    // guaranteed
    ABSORB(Target.OPPONENT, 0),
    ACIDSPDEFDROP(Target.OPPONENT, 0),
    ATTRACT(Target.OPPONENT, 0),
    BATONPASS(Target.SELF, 0),
    BEATUP(Target.SELF, 0),
    BELLYDRUM(Target.SELF, 0),
    BIDE(Target.SELF, 0),
    BIND(Target.OPPONENT, 0),
    CONFUSION(Target.OPPONENT, 0),
    CONVERSION(Target.SELF, 0),
    COUNTER(Target.OPPONENT, 0),
    CRITRATE(Target.SELF, 0),
    CRUNCHSPDEFDROP(Target.OPPONENT, 0),
    CURSE(Target.BOTH, 0),
    CURSE_GHOST(Target.BOTH, 0),
    DEFENSECURL(Target.SELF, 0),
    DESTINYBOND(Target.SELF, 0),
    DISABLE(Target.OPPONENT, 0),
    DOUBLEATTACK(Target.OPPONENT, 0),
    DRAGONRAGE(Target.OPPONENT, 0),
    DREAMEATER(Target.BOTH, 0),
    EARTHQUAKE(Target.OPPONENT, 0),
    ENCORE(Target.OPPONENT, 0),
    ENDURE(Target.SELF, 0),
    FALSESWIPE(Target.OPPONENT, 0),
    FLAIL(Target.SELF, 0),
    FOCUSENERGY(Target.SELF, 0),
    FORESIGHT(Target.OPPONENT, 0),
    FRUSTRATION(Target.SELF, 0),
    FURYCUTTTER(Target.SELF, 0),
    FUTURESIGHT(Target.SELF, 0),
    GUST(Target.OPPONENT, 0),
    HAZE(Target.BOTH, 0),
    HIDDENPOWER(Target.SELF, 0),
    HIDE(Target.SELF, 0),
    HIGHJUMPKICK(Target.SELF, 0),
    HYPERBEAM(Target.SELF, 0),
    LEECHSEED(Target.SELF, 0),
    LIGHTSCREEN(Target.SELF, 0),
    LOCKON(Target.SELF, 0),
    MEANLOOK(Target.OPPONENT, 0),
    METRONOME(Target.SELF, 0),
    MIMIC(Target.SELF, 0),
    MIRRORCOAT(Target.SELF, 0),
    MIRRORMOVE(Target.BOTH, 0),
    MIST(Target.SELF, 0),
    MOONLIGHT(Target.SELF, 0),
    MORNINGSUN(Target.SELF, 0),
    MULTIHIT(Target.OPPONENT, 0),
    NEVERMISS(Target.SELF, 0),
    NIGHTMARE(Target.OPPONENT, 0),
    ONEHITKO(Target.OPPONENT, 0),
    OPP_ACCURACYDROP1(Target.OPPONENT, 0),
    OPP_ATTACKDROP1(Target.OPPONENT, 0),
    OPP_ATTACKDROP2(Target.OPPONENT, 0),
    OPP_DEFENSEDROP1(Target.OPPONENT, 0),
    OPP_DEFENSEDROP2(Target.OPPONENT, 0),
    OPP_EVASIONDROP1(Target.OPPONENT, 0),
    OPP_SPEEDDROP1(Target.OPPONENT, 0),
    OPP_SPEEDDROP2(Target.OPPONENT, 0),
    PAYDAY(Target.SELF, 0),
    PERISHSONG(Target.BOTH, 0),
    PETALDANCE(Target.SELF, 0),
    PRESENT(Target.SELF, 0),
    PROTECT(Target.SELF, 0),
    PRZ(Target.OPPONENT, 0),
    PSN(Target.OPPONENT, 0),
    PSYCHICSPDEFDROP(Target.OPPONENT, 0),
    PSYCHUP(Target.BOTH, 0),
    PSYWAVE(Target.SELF, 0),
    PURSUIT(Target.OPPONENT, 0),
    QUICKATTACK(Target.SELF, 0),
    RAGE(Target.SELF, 0),
    RAINDANCE(Target.BOTH, 0),
    RAPIDSPIN(Target.SELF, 0),
    RAZORWIND(Target.BOTH, 0),
    RECOIL25(Target.SELF, 0),
    RECOVER(Target.SELF, 0),
    REFLECT(Target.SELF, 0),
    REST(Target.SELF, 0),
    RETURN(Target.SELF, 0),
    ROAR(Target.OPPONENT, 0),
    ROLLOUT(Target.SELF, 0),
    SAFEGUARD(Target.SELF, 0),
    SANDSTORM(Target.BOTH, 0),
    SEISMICTOSS(Target.SELF, 0),
    SELFDESTRUCT(Target.SELF, 0),
    SELF_ATTACKRAISE1(Target.SELF, 0),
    SELF_ATTACKRAISE2(Target.SELF, 0),
    SELF_DEFENSERAISE1(Target.SELF, 0),
    SELF_DEFENSERAISE2(Target.SELF, 0),
    SELF_EVASIONRAISE1(Target.SELF, 0),
    SELF_SPATKRAISE1(Target.SELF, 0),
    SELF_SPDEFRAISE2(Target.SELF, 0),
    SELF_SPECIALRAISE1(Target.SELF, 0),
    SELF_SPECIALRAISE2(Target.SELF, 0),
    SELF_SPEEDRAISE2(Target.SELF, 0),
    SKETCH(Target.SELF, 0),
    SKULLBASH(Target.SELF, 0),
    SKYATTACK(Target.BOTH, 0),
    SLEEP(Target.OPPONENT, 0),
    SLEEPTALK(Target.SELF, 0),
    SOLARBEAM(Target.SELF, 0),
    SONICBOOM(Target.OPPONENT, 0),
    SPITE(Target.OPPONENT, 0),
    SPLASH(Target.NONE, 0),
    STRUGGLE(Target.SELF, 0),
    SUBSTITUTE(Target.SELF, 0),
    SUNNYDAY(Target.BOTH, 0),
    SUPERFANG(Target.OPPONENT, 0),
    SWAGGER(Target.OPPONENT, 0),
    SWIFT(Target.OPPONENT, 0),
    SYNTHESIS(Target.SELF, 0),
    TELEPORT(Target.SELF, 0),
    TOXIC(Target.OPPONENT, 0),
    TRANSFORM(Target.SELF, 0),
    TWINNEEDLE(Target.OPPONENT, 0),
    VITALTHROW(Target.SELF, 0),

    // chance
    ANCIENTPOWER(Target.SELF, 25),
    BRN10(Target.OPPONENT, 25),
    BRN30(Target.OPPONENT, 76),
    CONFUSION10(Target.OPPONENT, 25),
    CONFUSION100(Target.OPPONENT, 255),
    FLAMEWHEEL(Target.BOTH, 25),
    FLINCH10(Target.OPPONENT, 25),
    FLINCH30(Target.OPPONENT, 76),
    FRZ10(Target.OPPONENT, 25),
    MAGNITUDE(Target.OPPONENT, 0),
    OPP_ATTACKDROP1_10(Target.OPPONENT, 25),
    OPP_DEFENSEDROP1_10(Target.OPPONENT, 25),
    OPP_SPECIALDROP1_30(Target.OPPONENT, 76),
    OPP_SPEEDDROP1_10(Target.OPPONENT, 25),
    PRZ10(Target.OPPONENT, 25),
    PRZ100(Target.OPPONENT, 255),
    PRZ30(Target.OPPONENT, 76),
    PSN20(Target.OPPONENT, 51),
    PSN30(Target.OPPONENT, 76),
    SACREDFIRE(Target.BOTH, 127),
    STOMP(Target.OPPONENT, 76),
    THIEF(Target.OPPONENT, 255),
    THUNDER(Target.OPPONENT, 76),
    TRIATTACK(Target.OPPONENT, 51),
    TRIPLEKICK(Target.OPPONENT, 85),
    TWISTER(Target.OPPONENT, 51),
    ;


    enum class Target {
        SELF, OPPONENT, BOTH, NONE
    }

    fun target(): Target {
        return target
    }

    fun chance(): Int {
        return effectChance
    }
}