package ru.yandex.yandexlavka.pojo.response;

import ru.yandex.yandexlavka.pojo.dto.entity.CourierDto;

import java.util.List;

public class GetCourierMetaInfoResponse {

    private long courierId;
    private String courierType;
    private List<Integer> regions;
    private List<String> workingHours;
    private int rating;
    private int earnings;

    public GetCourierMetaInfoResponse() {

    }

    public GetCourierMetaInfoResponse(long courierId,
                                      String courierType,
                                      List<Integer> regions,
                                      List<String> workingHours,
                                      int rating,
                                      int earnings) {
        this.courierId = courierId;
        this.courierType = courierType;
        this.regions = regions;
        this.workingHours = workingHours;
        this.rating = rating;
        this.earnings = earnings;
    }

    public GetCourierMetaInfoResponse(CourierDto courierDto, int rating, int earnings) {
        this(courierDto.getCourierId(),
                courierDto.getCourierType(),
                courierDto.getRegions(),
                courierDto.getWorkingHours(),
                rating,
                earnings);
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getEarnings() {
        return earnings;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }
}
