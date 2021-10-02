package com.abrastat.general;

import com.abrastat.exceptions.ItemNotImplementedException;
import org.jetbrains.annotations.NotNull;
import static com.abrastat.general.IItem.*;

public enum Item implements IItem {

    BERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    BERRY_JUICE(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    BERSERKGENE(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    BITTER_BERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    BLACK_BELT(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.FIGHTING);
        }
    },
    BLACKGLASSES(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.DARK);
        }
    },
    BRIGHTPOWDER(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    BURNT_BERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    CHARCOAL(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.FIRE);
        }
    },
    DRAGON_SCALE(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.DRAGON);
        }
    },
    FOCUS_BAND(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    GOLD_BERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    HARD_STONE(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.ROCK);
        }
    },
    ICE_BERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    KINGS_ROCK(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    LEFTOVERS(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            final int currentHP = generation.getCurrentHP();
            final int leftoversRecoveryAmnt = (int) Math.floor((1 / 16) * currentHP);
            generation.setCurrentHP(currentHP + leftoversRecoveryAmnt);
        }
    },
    LIGHT_BALL(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    LUCKY_PUNCH(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    MAGNET(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.ELECTRIC);
        }
    },
    MAIL(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    METAL_POWDER(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    MINT_BERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    MIRACLEBERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    MIRACLE_SEED(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.GRASS);
        }
    },
    MYSTERYBERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    MYSTIC_WATER(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.WATER);
        }
    },
    NEVERMELTICE(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.ICE);
        }
    },
    PINK_BOW(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.NORMAL);
        }
    },
    POISON_BARB(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.POISON);
        }
    },
    POLKADOT_BOW(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.NORMAL);
        }
    },
    PSNCUREBERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    PRZCUREBERRY(true) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    QUICK_CLAW(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    SCOPE_LENS(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    SHARP_BEAK(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.FLYING);
        }
    },
    SILVERPOWDER(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.BUG);
        }
    },
    SOFT_SAND(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.GROUND);
        }
    },
    SPELL_TAG(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            generation.itemDamageBoost(Type.GHOST);
        }
    },
    STICK(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    THICK_CLUB(false) {
        public <T extends Pokemon> void itemEffect(T generation) {
            notImplemented(this, generation);
        }
    },
    TWISTEDSPOON(false) {
        @Override
        public <T extends Pokemon> void itemEffect(T generation) {

        }
    };

    private final boolean isConsumed;

    public static <T extends Pokemon> void notImplemented(Item item, @NotNull T t) {
        throw new ItemNotImplementedException(item + " not implemented for " + t);
    }

    Item(boolean isConsumed) {
        this.isConsumed = isConsumed;
    }
}
