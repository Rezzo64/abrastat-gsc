package com.abrastat.general;

@FunctionalInterface
public interface IItem {

    <T extends Pokemon> void itemEffect(T generation);
}

