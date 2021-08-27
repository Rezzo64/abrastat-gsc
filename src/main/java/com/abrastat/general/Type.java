package com.abrastat.general;

public enum Type {
    NONE("None", 0),
    NORMAL("Normal", 1),
    FIRE("Fire", 2),
    WATER("Water", 3),
    ELECTRIC("Electric", 4),
    GRASS("Grass", 5),
    ICE("Ice", 6),
    FIGHTING("Fighting", 7),
    POISON("Poison", 8),
    GROUND("Ground", 9),
    FLYING("Flying", 10),
    PSYCHIC("Psychic", 11),
    BUG("Bug", 12),
    ROCK("Rock", 13),
    GHOST("Ghost", 14),
    DRAGON("Dragon", 15),
    DARK("Dark", 16),
    STEEL("Steel", 17),
    FAIRY("Fairy", 18);

    public final String NAME;
    public final int INDEX;

    Type(String name, int index) {
        this.NAME = name;
        this.INDEX = index;
    }

//      Not convinced I'll ever need this...
//      Set "None" as secondary typing if Mon has only one type

    public void setTypes(Type type1, Type type2)    {
        this.setType(type1);
        this.setType(type2);
    }

    private void setType(Type type)   {
        switch(type)    {
            case NONE:
                type = NONE;
                break;
            case NORMAL:
                type = NORMAL;
                break;
            case FIRE:
                type = FIRE;
                break;
            case WATER:
                type = WATER;
                break;
            case ELECTRIC:
                type = ELECTRIC;
                break;
            case GRASS:
                type = GRASS;
            case ICE:
                type = ICE;
            case FIGHTING:
                type = FIGHTING;
            case POISON:
                type = POISON;
            case GROUND:
                type = GROUND;
            case FLYING:
                type = FLYING;
            case PSYCHIC:
                type = PSYCHIC;
            case BUG:
                type = BUG;
            case ROCK:
                type = ROCK;
            case GHOST:
                type = GHOST;
            case DRAGON:
                type = DRAGON;
            case DARK:
                type = DARK;
            case STEEL:
                type = STEEL;
            case FAIRY:
                type = FAIRY;
        }
    }
}

