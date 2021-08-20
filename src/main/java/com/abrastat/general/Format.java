package com.abrastat.general;

import java.util.HashMap;

public final class Format {

    // Just the first 3 for now.
    // Numbering to simplify the control flow. If you don't play
    // Pocket Monsters video game: gen 1 = RBY, 2 = GSC, 3 = ADV, etc.

    // TODO: 20/08/2021 implement this the same way that Type.java has been. 
    public static final Format RBY = new Format(1);
    public static final Format GSC = new Format(2);
    public static final Format ADV = new Format(3);
    private Format format;

    private static final HashMap<Integer, Format> ALLFORMATS
            = new HashMap<>()   {
        {
        put(0, null);
        put(1, RBY);
        put(2, GSC);
        put(3, ADV);
        }
    };

    public Format getFormat()    {  return format;  }

    public Format(int gen)  {
        switch(gen) {
            case 1:
                this.format = RBY;
                // call method to implement RBY battle mechanics here
                break;
            case 2:
                this.format = GSC;
                // TODO call method to implement GSC battle mechanics here
                break;
            case 3:
                this.format = ADV;
                // call method to implement ADV battle mechanics here
                break;
        }
    }

    public Format() {
        System.out.println("Format not selected. Assuming and defaulting to GSC.");
        new Format(2);
    }
}
