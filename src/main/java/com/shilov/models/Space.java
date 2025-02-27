package com.shilov.models;

import com.shilov.common.enums.SpaceType;

import java.util.Objects;

public class Space {

    private int id;
    private SpaceType type;
    private boolean isAvailable;
    private int price;

    public Space() {}

    public Space(int id, SpaceType type, boolean isAvailable, int price) {
        this.id = id;
        this.type = type;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpaceType getType() {
        return type;
    }

    public void setType(SpaceType type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;
        return id == space.id && isAvailable == space.isAvailable && price == space.price && type == space.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, isAvailable, price);
    }

    @Override
    public String toString() {
        return "Space{" +
                "id=" + id +
                ", type=" + type +
                ", isAvailable=" + isAvailable +
                ", price=" + price +
                '}';
    }
}
