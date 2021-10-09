package com.abrastat.general;

import com.abrastat.general.exceptions.ItemNotImplementedException;
import com.abrastat.gsc.GSCItem;
import com.abrastat.gsc.GSCPokemon;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface IItem {

    enum Item {

        BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        BERRY_JUICE(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        BERSERKGENE(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        BITTER_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
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
                notImplemented(this, generation);
            }
        },
        BURNT_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
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
                notImplemented(this, generation);
            }
        },
        GOLD_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        HARD_STONE(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.ROCK);
            }
        },
        ICE_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        KINGS_ROCK(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        LEFTOVERS(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.leftoversEffect();
            }
        },
        LIGHT_BALL(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        LUCKY_PUNCH(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        MAGNET(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.ELECTRIC);
            }
        },
        MAIL(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        METAL_POWDER(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        MINT_BERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.cureSleep();
            }
        },
        MIRACLEBERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        MIRACLE_SEED(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.GRASS);
            }
        },
        MYSTERYBERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
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
                notImplemented(this, generation);
            }
        },
        PRZCUREBERRY(true) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        QUICK_CLAW(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
            }
        },
        SCOPE_LENS(false) {
            public <T extends IItem> void itemEffect(T generation) {
                notImplemented(this, generation);
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
                notImplemented(this, generation);
            }
        },
        THICK_CLUB(false) {
            public <T extends IItem> void itemEffect(T generation) {

            }
        },
        TWISTEDSPOON(false) {
            public <T extends IItem> void itemEffect(T generation) {
                generation.damageBoost(Type.PSYCHIC);
            }
        };

        private final boolean isConsumed;

        public abstract <T extends IItem> void itemEffect(T generation);

        Function<? extends IItem, Item> itemFunction = (itemGeneration) -> {
            this.itemEffect(itemGeneration);
            return (this);
        };

        Item(boolean isConsumed) {
            this.isConsumed = isConsumed;
        }
    }

    // TODO some of these can be declared default in the future. Possibly even static.

    void leftoversEffect();
    void cureStatus();
    void damageBoost(Type type);
    void cureSleep();

    BiFunction<? extends IItem, ? extends Pokemon, Pokemon> itemFunction = (itemGeneration, pokemon) -> {
        pokemon.getHeldItem().itemEffect(itemGeneration);
        return pokemon;
    };

    static <T extends IItem> void notImplemented(Enum<Item> item, T t) {
        throw new ItemNotImplementedException(item.toString() + " not implemented within " + t.toString());
    }
}