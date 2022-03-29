package com.abrastat.general;

public enum Type {
    NONE,
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY;

//      Not convinced I'll ever need this...
//      Set "None" as secondary typing if Mon has only one type

    @Override
    public String toString() {
        return this.name();
    }

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

