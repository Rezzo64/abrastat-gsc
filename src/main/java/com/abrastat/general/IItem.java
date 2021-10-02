package com.abrastat.general;

import com.abrastat.exceptions.ItemNotImplementedException;
import com.abrastat.gsc.GSCItem;
import org.jetbrains.annotations.NotNull;
import java.util.function.BiFunction;
import static com.abrastat.general.Item.*;

@FunctionalInterface
public interface IItem {

    <T extends Pokemon> void itemEffect(T generation);
}

