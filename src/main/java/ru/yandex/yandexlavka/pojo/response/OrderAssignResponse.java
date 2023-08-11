package ru.yandex.yandexlavka.pojo.response;

import ru.yandex.yandexlavka.pojo.dto.CouriersOrdersDto;

import java.util.List;

public class OrderAssignResponse {

    private String date;
    private List<CouriersOrdersDto> couriers;

    public OrderAssignResponse() {

    }

    public OrderAssignResponse(String date, List<CouriersOrdersDto> couriers) {
        this.date = date;
        this.couriers = couriers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CouriersOrdersDto> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<CouriersOrdersDto> couriers) {
        this.couriers = couriers;
    }
}
