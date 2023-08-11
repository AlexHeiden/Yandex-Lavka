package ru.yandex.yandexlavka.pojo.dto;

public class CreateAssignmentDto {

    private Long courierId;
    private Long orderId;
    private String creationDate;

    public CreateAssignmentDto() {

    }

    public CreateAssignmentDto(Long courierId, Long orderId, String creationDate) {
        this.courierId = courierId;
        this.orderId = orderId;
        this.creationDate = creationDate;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
