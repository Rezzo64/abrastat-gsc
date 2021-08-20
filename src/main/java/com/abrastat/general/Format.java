package com.abrastat.general;

public final class Format {

    //Just the first 3 for now.
    // TODO: 20/08/2021 implement this the same way that Type.java has been. 
    public static final Format RBY = new Format(1);
    public static final Format GSC = new Format(2);
    public static final Format ADV = new Format(3);
    private Format format;

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
    }
}
