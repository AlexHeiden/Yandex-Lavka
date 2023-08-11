package ru.yandex.yandexlavka.pojo.dto;

import ru.yandex.yandexlavka.pojo.dto.entity.OrderDto;

import java.util.List;

public class CouriersOrdersDto {

    private long courierId;
    private List<OrderDto> orders;

    public CouriersOrdersDto() {

    }

    public CouriersOrdersDto(long courierId, List<OrderDto> orders) {
        this.courierId = courierId;
        this.orders = orders;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }
}
