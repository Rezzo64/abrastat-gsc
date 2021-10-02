package com.abrastat.general;

public enum Item {

    BERRY(true) {public void itemBehaviour() {

    }},
    BERRY_JUICE(true) {public void itemBehaviour() {

    }},
    BERSERKGENE(true) {public void itemBehaviour() {

    }},
    BITTER_BERRY(true) {public void itemBehaviour() {

    }},
    BLACK_BELT(false) {public void itemBehaviour() {

    }},
    BLACKGLASSES(false) {public void itemBehaviour() {

    }},
    BRIGHTPOWDER(false) {public void itemBehaviour() {

    }},
    BURNT_BERRY(true) {public void itemBehaviour() {

    }},
    CHARCOAL(false) {public void itemBehaviour() {

    }},
    DRAGON_SCALE(false) {public void itemBehaviour() {

    }},
    FOCUS_BAND(false) {public void itemBehaviour() {

    }},
    GOLD_BERRY(true) {public void itemBehaviour() {

    }},
    HARD_STONE(false) {public void itemBehaviour() {

    }},
    ICE_BERRY(true) {public void itemBehaviour() {

    }},
    KINGS_ROCK(false) {public void itemBehaviour() {

    }},
    LEFTOVERS(false) {public void itemBehaviour() {

    }},
    LIGHT_BALL(false) {public void itemBehaviour() {

    }},
    LUCKY_PUNCH(false) {public void itemBehaviour() {

    }},
    MAGNET(false) {public void itemBehaviour() {

    }},
    MAIL(false) {public void itemBehaviour() {

    }},
    METAL_POWDER(false) {public void itemBehaviour() {

    }},
    MINT_BERRY(true) {public void itemBehaviour() {

    }},
    MIRACLEBERRY(true) {public void itemBehaviour() {

    }},
    MIRACLE_SEED(false) {public void itemBehaviour() {

    }},
    MYSTERYBERRY(true) {public void itemBehaviour() {

    }},
    MYSTIC_WATER(false) {public void itemBehaviour() {

    }},
    NEVERMELTICE(false) {public void itemBehaviour() {

    }},
    PINK_BOW(false) {public void itemBehaviour() {

    }},
    POISON_BARB(false) {public void itemBehaviour() {

    }},
    POLKADOT_BOW(false) {public void itemBehaviour() {

    }},
    PSNCUREBERRY(true) {public void itemBehaviour() {

    }},
    PRZCUREBERRY(true) {public void itemBehaviour() {

    }},
    QUICK_CLAW(false) {public void itemBehaviour() {

    }},
    SCOPE_LENS(false) {public void itemBehaviour() {

    }},
    SHARP_BEAK(false) {public void itemBehaviour() {

    }},
    SILVERPOWDER(false) {public void itemBehaviour() {

    }},
    SOFT_SAND(false) {public void itemBehaviour() {

    }},
    SPELL_TAG(false) {public void itemBehaviour() {

    }},
    STICK(false) {public void itemBehaviour() {

    }},
    THICK_CLUB(false) {public void itemBehaviour() {

    }},
    TWISTEDSPOON(false) {public void itemBehaviour() {

    }};

    private final boolean isConsumable;
    public abstract void itemBehaviour();

    Item(boolean isConsumable) {
        this.isConsumable = isConsumable;
    }
}

