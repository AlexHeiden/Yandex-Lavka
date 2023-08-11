package ru.yandex.yandexlavka.pojo.dto;

import java.util.List;

public class CreateOrderDto {

    private Float weight;
    private Integer regions;
    private List<String> deliveryHours;
    private Integer cost;

    public CreateOrderDto() {

    }

    public CreateOrderDto(Float weight, Integer regions, List<String> deliveryHours, Integer cost) {
        this.weight = weight;
        this.regions = regions;
        this.deliveryHours = deliveryHours;
        this.cost = cost;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getRegions() {
        return regions;
    }

    public void setRegions(Integer regions) {
        this.regions = regions;
    }

    public List<String> getDeliveryHours() {
        return deliveryHours;
    }

    public void setDeliveryHours(List<String> deliveryHours) {
        this.deliveryHours = deliveryHours;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
