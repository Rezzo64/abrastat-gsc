package com.abrastat.general;

import java.util.HashMap;

public enum Format {

    // Just look at the first 3 for now.
    // Numbering to simplify the control flow. If you don't play
    // Pocket Monsters video game: gen 1 = RBY, 2 = GSC, 3 = ADV, etc.

    RBY(1),
    GSC(2),
    ADV(3),
    DPP(4),
    BW(5),
    ORAS(6),
    SM(7),
    SWSH(8);

    final int INDEX;

    Format(int index) {
        this.INDEX = index;
    }

    public void setFormat(Format format)    {
        switch(format)    {
            case RBY:
                format = RBY;
            case GSC:
                format = GSC;
            case ADV:
                format = ADV;
            case DPP:
                format = DPP;
            case BW:
                format = BW;
            case ORAS:
                format = ORAS;
            case SM:
                format = SM;
            case SWSH:
                format = SWSH;
        }
    }

    private static final HashMap<Integer, Format> ALLFORMATS
            = new HashMap<>()   {
        {
        put(0, null);
        put(1, RBY);
        put(2, GSC);
        put(3, ADV);
        }
    };

}
