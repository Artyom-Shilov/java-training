package com.shilov.models.builders;

import com.shilov.common.enums.SpaceType;
import com.shilov.models.Space;

public class SpaceBuilder {
    private int id;
    private SpaceType type;
    private boolean isAvailable;
    private int price;

    public SpaceBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public SpaceBuilder setType(SpaceType type) {
        this.type = type;
        return this;
    }

    public SpaceBuilder setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public SpaceBuilder setPrice(int price) {
        this.price = price;
        return this;
    }

    public Space createSpace() {
        return new Space(id, type, isAvailable, price);
    }
}