package ru.yandex.yandexlavka.pojo.dto;

public class CompleteOrderDto {

    private Long courierId;
    private Long orderId;
    private String completeTime;

    public CompleteOrderDto() {

    }

    public CompleteOrderDto(Long courierId, Long orderId, String completeTime) {
        this.courierId = courierId;
        this.orderId = orderId;
        this.completeTime = completeTime;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }
}
