package com.shilov.models;

import com.shilov.common.enums.SpaceType;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Space implements Serializable {

    private String id;
    private SpaceType type;
    private int hourlyPrice;

    public Space() {
        this.id = UUID.randomUUID().toString();
    }

    public Space(SpaceType type, int hourlyPrice) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.hourlyPrice = hourlyPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SpaceType getType() {
        return type;
    }

    public void setType(SpaceType type) {
        this.type = type;
    }

    public int getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(int hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;
        return hourlyPrice == space.hourlyPrice && Objects.equals(id, space.id) && type == space.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, hourlyPrice);
    }

    @Override
    public String toString() {
        return "Space{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", hourlyPrice=" + hourlyPrice +
                '}';
    }
}
