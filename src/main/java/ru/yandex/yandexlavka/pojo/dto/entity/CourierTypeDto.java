package ru.yandex.yandexlavka.pojo.dto.entity;

import jakarta.persistence.*;

@Entity
@Table(name="courier_types")
public class CourierTypeDto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long courierTypeId;

    @Column(name="name")
    private String name;

    @Column(name="max_weight")
    private Float maxWeight;

    @Column(name="max_order_number")
    private Integer maxOrderNumber;

    @Column(name="max_region_number")
    private Integer maxRegionNumber;

    @Column(name="first_order_minutes")
    private Integer firstOrderMinutes;

    @Column(name="other_order_minutes")
    private Integer otherOrderMinutes;

    @Column(name="stat_salary_coef")
    private Integer statSalaryCoef;

    @Column(name="first_order_salary_coef")
    private Float firstOrderSalaryCoef;

    @Column(name="other_order_salary_coef")
    private Float otherOrderSalaryCoef;

    @Column(name="rating_coef")
    private Integer ratingCoef;

    public CourierTypeDto() {

    }

    public CourierTypeDto(String name,
                          Float maxWeight,
                          Integer maxOrderNumber,
                          Integer maxRegionNumber,
                          Integer firstOrderMinutes,
                          Integer otherOrderMinutes,
                          Integer statSalaryCoef,
                          Float firstOrderSalaryCoef,
                          Float otherOrderSalaryCoef,
                          Integer ratingCoef) {
        this.name = name;
        this.maxWeight = maxWeight;
        this.maxOrderNumber = maxOrderNumber;
        this.maxRegionNumber = maxRegionNumber;
        this.firstOrderMinutes = firstOrderMinutes;
        this.otherOrderMinutes = otherOrderMinutes;
        this.statSalaryCoef = statSalaryCoef;
        this.firstOrderSalaryCoef = firstOrderSalaryCoef;
        this.otherOrderSalaryCoef = otherOrderSalaryCoef;
        this.ratingCoef = ratingCoef;
    }

    public long getCourierTypeId() {
        return courierTypeId;
    }

    public void setCourierTypeId(long courierTypeId) {
        this.courierTypeId = courierTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getMaxOrderNumber() {
        return maxOrderNumber;
    }

    public void setMaxOrderNumber(Integer maxOrderNumber) {
        this.maxOrderNumber = maxOrderNumber;
    }

    public Integer getMaxRegionNumber() {
        return maxRegionNumber;
    }

    public void setMaxRegionNumber(Integer maxRegionNumber) {
        this.maxRegionNumber = maxRegionNumber;
    }

    public Integer getFirstOrderMinutes() {
        return firstOrderMinutes;
    }

    public void setFirstOrderMinutes(Integer firstOrderMinutes) {
        this.firstOrderMinutes = firstOrderMinutes;
    }

    public Integer getOtherOrderMinutes() {
        return otherOrderMinutes;
    }

    public void setOtherOrderMinutes(Integer otherOrderMinutes) {
        this.otherOrderMinutes = otherOrderMinutes;
    }

    public Integer getStatSalaryCoef() {
        return statSalaryCoef;
    }

    public void setStatSalaryCoef(Integer statSalaryCoef) {
        this.statSalaryCoef = statSalaryCoef;
    }

    public Float getFirstOrderSalaryCoef() {
        return firstOrderSalaryCoef;
    }

    public void setFirstOrderSalaryCoef(Float firstOrderSalaryCoef) {
        this.firstOrderSalaryCoef = firstOrderSalaryCoef;
    }

    public Float getOtherOrderSalaryCoef() {
        return otherOrderSalaryCoef;
    }

    public void setOtherOrderSalaryCoef(Float otherOrderSalaryCoef) {
        this.otherOrderSalaryCoef = otherOrderSalaryCoef;
    }

    public Integer getRatingCoef() {
        return ratingCoef;
    }

    public void setRatingCoef(Integer ratingCoef) {
        this.ratingCoef = ratingCoef;
    }
}
