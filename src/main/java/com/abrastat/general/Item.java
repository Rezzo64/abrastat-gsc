package com.abrastat.general;

import com.abrastat.general.exceptions.ItemNotImplementedException;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collections;

import static com.abrastat.general.Format.*;

public enum Item  {

    BERRY(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    BERRY_JUICE(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    BERSERKGENE(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    BITTER_BERRY(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    BLACK_BELT(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    BLACKGLASSES(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    BRIGHTPOWDER(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    BURNT_BERRY(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    CHARCOAL(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    DRAGON_SCALE(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    FOCUS_BAND(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    GOLD_BERRY(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    HARD_STONE(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    ICE_BERRY(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    KINGS_ROCK(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    LEFTOVERS(false, new Format[]{GSC, ADV, DPP, BW, ORAS, SM, SWSH}) {
        public void itemEffect() {

            }
    },
    LIGHT_BALL(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    LUCKY_PUNCH(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    MAGNET(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    MAIL(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    METAL_POWDER(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    MINT_BERRY(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    MIRACLEBERRY(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    MIRACLE_SEED(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    MYSTERYBERRY(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    MYSTIC_WATER(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    NEVERMELTICE(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    PINK_BOW(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    POISON_BARB(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    POLKADOT_BOW(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    PSNCUREBERRY(true, new Format[]{GSC}) {public void itemEffect() {

    }},
    PRZCUREBERRY(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    QUICK_CLAW(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    SCOPE_LENS(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    SHARP_BEAK(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    SILVERPOWDER(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    SOFT_SAND(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    SPELL_TAG(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    STICK(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    THICK_CLUB(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    TWISTEDSPOON(false, new Format[]{GSC}) {public void itemEffect() {

    }},
    METAL_COAT(false, new Format[]{GSC})    {public void itemEffect()   {

    }}
    ;

    private boolean isConsumable;
    private ArrayList<Format> formats;
    public abstract void itemEffect();

    Item (boolean isConsumable, Format[] formats) {
        this.isConsumable = isConsumable;
        ArrayList<Format> formatsArrayList = new ArrayList<>();
        Collections.addAll(formatsArrayList, formats);
        this.formats = formatsArrayList;
    }

    @Deprecated
    @Contract("_, _ -> fail")
    static <T extends Item> void notImplemented(Enum<Item> item, T t) {
        throw new ItemNotImplementedException(item.toString() + " not implemented within " + t.toString());
    }
}

