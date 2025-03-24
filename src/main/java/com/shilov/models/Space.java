package com.shilov.models;

import com.shilov.common.enums.SpaceType;

import java.io.Serializable;
import java.util.Objects;

public class Space implements Serializable {

    private Long id;
    private SpaceType type;
    private int hourlyPrice;

    public Space() {
        type = SpaceType.OPEN;
    }

    public Space(SpaceType type, int hourlyPrice) {
        this.type = type;
        this.hourlyPrice = hourlyPrice;
    }

    public Space(Long id, SpaceType type, int hourlyPrice) {
        this.id = id;
        this.type = type;
        this.hourlyPrice = hourlyPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
