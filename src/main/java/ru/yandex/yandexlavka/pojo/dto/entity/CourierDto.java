package ru.yandex.yandexlavka.pojo.dto.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="couriers")
public class CourierDto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long courierId;

    @Column(name="courier_type")
    private String courierType;

    @Column(name="regions")
    private List<Integer> regions;

    @Column(name="working_hours")
    private List<String> workingHours;

    public CourierDto() {

    }

    public CourierDto(String courierType, List<Integer> regions, List<String> workingHours) {
        this.courierType = courierType;
        this.regions = regions;
        this.workingHours = workingHours;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    public String getCourierType() {
        return courierType;
    }

    public void setCourierType(String courierType) {
        this.courierType = courierType;
    }

    public List<Integer> getRegions() {
        return regions;
    }

    public void setRegions(List<Integer> regions) {
        this.regions = regions;
    }

    public List<String> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<String> workingHours) {
        this.workingHours = workingHours;
    }
}
