package ru.yandex.yandexlavka.pojo.dto.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="assignments")
public class AssignmentDto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long assignmentId;

    @OneToOne
    @JoinColumn(name="courier_id")
    private CourierDto courierDto;

    @OneToOne
    @JoinColumn(name="order_id")
    private OrderDto orderDto;

    @Temporal(TemporalType.DATE)
    @Column(name="creation_date")
    private LocalDate creationDate;

    public AssignmentDto() {

    }

    public AssignmentDto(CourierDto courierDto, OrderDto orderDto, LocalDate creationDate) {
        this.courierDto = courierDto;
        this.orderDto = orderDto;
        this.creationDate = creationDate;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public CourierDto getCourierDto() {
        return courierDto;
    }

    public void setCourierDto(CourierDto courierDto) {
        this.courierDto = courierDto;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
