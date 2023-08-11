package ru.yandex.yandexlavka.pojo.response;

import ru.yandex.yandexlavka.pojo.dto.entity.AssignmentDto;

import java.time.LocalDate;

public class CreateAssignmentResponse {

    private long assignmentId;
    private long courierId;
    private long orderId;
    private LocalDate creationDate;

    public CreateAssignmentResponse() {

    }

    public CreateAssignmentResponse(long assignmentId, long courierId, long orderId, LocalDate creationDate) {
        this.assignmentId = assignmentId;
        this.courierId = courierId;
        this.orderId = orderId;
        this.creationDate = creationDate;
    }

    public CreateAssignmentResponse(AssignmentDto assignmentDto) {
        assignmentId = assignmentDto.getAssignmentId();
        courierId = assignmentDto.getCourierDto().getCourierId();
        orderId = assignmentDto.getOrderDto().getOrderId();
        creationDate = assignmentDto.getCreationDate();
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
