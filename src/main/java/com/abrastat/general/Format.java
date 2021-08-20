package com.abrastat.general;

public final class Format {

    //Just the first 3 for now.
    // TODO: 20/08/2021 implement this the same way that Type.java has been. 
    public static final Format RBY = new Format(1);
    public static final Format GSC = new Format(2);
    public static final Format ADV = new Format(3);
    private Format format;

    public Format getFormat()    {  return format;  }

    public void setFormatAsRBY()  {
        this.format = RBY;
    }

    public void setFormatAsGSC()    {
        this.format = GSC;
    }

    public void setFormatAsADV()    {
        this.format = ADV;
    }

    public Format(int gen)  {

    }

    public Format() {
        System.out.println("Format not selected. Assuming and defaulting to GSC.");
    }
}
