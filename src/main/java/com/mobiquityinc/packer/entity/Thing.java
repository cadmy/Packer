package com.mobiquityinc.packer.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by Cadmy on 04.06.2019.
 */
public class Thing {
    private int index;
    private BigDecimal weight;
    private BigDecimal cost;

    public Thing() {
    }

    public Thing(int index, BigDecimal weight, BigDecimal cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thing thing = (Thing) o;
        return index == thing.index &&
                Objects.equals(weight, thing.weight) &&
                Objects.equals(cost, thing.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, weight, cost);
    }

    @Override
    public String toString() {
        return "Thing{" +
                "index=" + index +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }
}
