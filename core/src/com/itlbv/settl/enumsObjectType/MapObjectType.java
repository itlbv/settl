package com.itlbv.settl.enumsObjectType;

public enum MapObjectType implements GameObjectType {
    GRASS(true),
    WATER(false),
    ROCK(false),
    TREE(false),

    TESTOBJECT(true);

    public final boolean passable;
    MapObjectType(boolean passable) {
        this.passable = passable;
    }
}
