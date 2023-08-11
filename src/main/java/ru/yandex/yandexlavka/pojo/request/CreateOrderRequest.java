package ru.yandex.yandexlavka.pojo.request;

import ru.yandex.yandexlavka.pojo.dto.CreateOrderDto;

import java.util.List;

public class CreateOrderRequest {

    private List<CreateOrderDto> orders;

    public CreateOrderRequest() {

    }

    public CreateOrderRequest(List<CreateOrderDto> orders) {
        this.orders = orders;
    }

    public List<CreateOrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<CreateOrderDto> orders) {
        this.orders = orders;
    }
}
