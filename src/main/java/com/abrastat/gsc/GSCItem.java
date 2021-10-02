package com.abrastat.gsc;

import com.abrastat.general.IItem;
import com.abrastat.general.Item;
import com.abrastat.general.Pokemon;
import com.abrastat.general.Type;

public class GSCItem implements IItem {

    Item item;


    @Override
    public <T extends Pokemon> void itemEffect(T generation) {

    }
}