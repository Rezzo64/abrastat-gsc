package com.abrastat.general;

import com.abrastat.gsc.GSCItem;

public interface IItem {

    enum Item {

        BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        BERRY_JUICE(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        BERSERKGENE(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        BITTER_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        BLACK_BELT(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.FIGHTING);
            }
        },
        BLACKGLASSES(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.DARK);
            }
        },
        BRIGHTPOWDER(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        BURNT_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        CHARCOAL(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.FIRE);
            }
        },
        DRAGON_SCALE(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.DRAGON);
            }
        },
        FOCUS_BAND(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        GOLD_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        HARD_STONE(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.ROCK);
            }
        },
        ICE_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        KINGS_ROCK(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        LEFTOVERS(false) {
            public <T extends IItem> void itemEffect(T generation) {
                leftoversEffect();
            }
        },
        LIGHT_BALL(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        LUCKY_PUNCH(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        MAGNET(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.ELECTRIC);
            }
        },
        MAIL(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        METAL_POWDER(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        MINT_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.cureSleep();
            }
        },
        MIRACLEBERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                cureStatus();
            }
        },
        MIRACLE_SEED(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.GRASS);
            }
        },
        MYSTERYBERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        MYSTIC_WATER(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.WATER);
            }
        },
        NEVERMELTICE(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.ICE);
            }
        },
        PINK_BOW(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.NORMAL);
            }
        },
        POISON_BARB(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.POISON);
            }
        },
        POLKADOT_BOW(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.NORMAL);
            }
        },
        PSNCUREBERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        PRZCUREBERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        QUICK_CLAW(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        SCOPE_LENS(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        SHARP_BEAK(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.FLYING);
            }
        },
        SILVERPOWDER(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.BUG);
            }
        },
        SOFT_SAND(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.GROUND);
            }
        },
        SPELL_TAG(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.GHOST);
            }
        },
        STICK(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        THICK_CLUB(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this);
            }
        },
        TWISTEDSPOON(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.PSYCHIC);
            }
        };

        private final boolean isConsumed;

        public abstract <T extends IItem> void itemEffect(T generation);

        Item(boolean isConsumed) {
            this.isConsumed = isConsumed;
        }
    }

    // TODO some of these can be declared default in the future. Possibly even static.
    static void leftoversEffect() {

    }

    static void cureStatus() {

    }

    void damageBoost(Type type);
    void cureSleep();

    static void notImplemented(Enum<Item> item) {
        throw new ItemNotImplementedException(item.toString() + " not implemented");
    }

}

class ItemNotImplementedException extends RuntimeException    {
    public ItemNotImplementedException(String message)  {
        super(message);
    }
}