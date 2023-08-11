package ru.yandex.yandexlavka.pojo.request;

import ru.yandex.yandexlavka.pojo.dto.CreateCourierDto;

import java.util.List;

public class CreateCourierRequest {

    private List<CreateCourierDto> couriers;

    public CreateCourierRequest() {

    }

    public CreateCourierRequest(List<CreateCourierDto> couriers) {
        this.couriers = couriers;
    }

    public List<CreateCourierDto> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<CreateCourierDto> couriers) {
        this.couriers = couriers;
    }
}
