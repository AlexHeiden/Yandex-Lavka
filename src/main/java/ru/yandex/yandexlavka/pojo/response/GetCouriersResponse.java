package ru.yandex.yandexlavka.pojo.response;

import ru.yandex.yandexlavka.pojo.dto.entity.CourierDto;

import java.util.List;

public class GetCouriersResponse {

    private List<CourierDto> couriers;
    private int limit;
    private int offset;

    public GetCouriersResponse() {

    }

    public GetCouriersResponse(List<CourierDto> couriers, int limit, int offset) {
        this.couriers = couriers;
        this.limit = limit;
        this.offset = offset;
    }

    public List<CourierDto> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<CourierDto> couriers) {
        this.couriers = couriers;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
