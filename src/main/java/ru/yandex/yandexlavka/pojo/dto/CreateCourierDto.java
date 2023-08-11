package ru.yandex.yandexlavka.pojo.dto;

import java.util.List;

public class CreateCourierDto {

    private String courierType;
    private List<Integer> regions;
    private List<String> workingHours;

    public CreateCourierDto() {

    }

    public CreateCourierDto(String courierType, List<Integer> regions, List<String> workingHours) {
        this.courierType = courierType;
        this.regions = regions;
        this.workingHours = workingHours;
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
