package ru.yandex.yandexlavka.pojo.dto.entity;

import jakarta.persistence.*;
import ru.yandex.yandexlavka.common_validation_utils.TimeValidationUtils;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
public class OrderDto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long orderId;

    @Column(name="weight")
    private Float weight;

    @Column(name="regions")
    private Integer regions;

    @Column(name="delivery_hours")
    private List<String> deliveryHours;

    @Column(name="cost")
    private Integer cost;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="completed_time")
    private LocalDateTime completedTime;

    public OrderDto() {

    }

    public OrderDto(Float weight, Integer regions, List<String> deliveryHours, Integer cost, LocalDateTime completedTime) {
        this.weight = weight;
        this.regions = regions;
        this.deliveryHours = deliveryHours;
        this.cost = cost;
        this.completedTime = completedTime;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public String getCompletedTime() {
        return completedTime == null ? null : completedTime.format(TimeValidationUtils.DateTimeFormat);
    }

    public void setCompletedTime(LocalDateTime completedTime) {
        this.completedTime = completedTime;
    }
}
