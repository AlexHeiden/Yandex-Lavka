package ru.yandex.yandexlavka.pojo.dto.entity;

public class CourierRawMetaInfoDto {

    private Long earningsWithoutStatCoef;
    private Long countOfCompleteOrders;

    public CourierRawMetaInfoDto() {

    }

    public CourierRawMetaInfoDto(Long earningsWithoutStatCoef, Long countOfCompleteOrders) {
        this.earningsWithoutStatCoef = earningsWithoutStatCoef;
        this.countOfCompleteOrders = countOfCompleteOrders;
    }

    public Long getEarningsWithoutStatCoef() {
        return earningsWithoutStatCoef;
    }

    public void setEarningsWithoutStatCoef(Long earningsWithoutStatCoef) {
        this.earningsWithoutStatCoef = earningsWithoutStatCoef;
    }

    public Long getCountOfCompleteOrders() {
        return countOfCompleteOrders;
    }

    public void setCountOfCompleteOrders(Long countOfCompleteOrders) {
        this.countOfCompleteOrders = countOfCompleteOrders;
    }
}
