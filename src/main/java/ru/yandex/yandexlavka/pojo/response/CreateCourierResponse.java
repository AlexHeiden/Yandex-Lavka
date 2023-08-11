package ru.yandex.yandexlavka.pojo.response;

import ru.yandex.yandexlavka.pojo.dto.entity.CourierDto;

import java.util.List;

public class CreateCourierResponse {

    private List<CourierDto> couriers;

    public CreateCourierResponse() {

    }

    public CreateCourierResponse(List<CourierDto> couriers) {
        this.couriers = couriers;
    }

    public List<CourierDto> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<CourierDto> couriers) {
        this.couriers = couriers;
    }
}
