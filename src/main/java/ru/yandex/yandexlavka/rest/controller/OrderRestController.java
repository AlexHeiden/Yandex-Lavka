package ru.yandex.yandexlavka.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.pojo.dto.entity.OrderDto;
import ru.yandex.yandexlavka.pojo.request.CompleteOrderRequest;
import ru.yandex.yandexlavka.pojo.request.CreateAssignmentRequest;
import ru.yandex.yandexlavka.pojo.request.CreateOrderRequest;
import ru.yandex.yandexlavka.pojo.response.CreateAssignmentResponse;
import ru.yandex.yandexlavka.rest.rate_limit.RateLimited;
import ru.yandex.yandexlavka.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @RateLimited
    public List<OrderDto> createOrders(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrders(createOrderRequest);
    }

    @PostMapping("/complete")
    @RateLimited
    public List<OrderDto> completeOrders(@RequestBody CompleteOrderRequest completeOrderRequest) {
        return orderService.completeOrders(completeOrderRequest);
    }

    @PostMapping("/assign")
    @RateLimited
    public List<CreateAssignmentResponse> createAssignments(@RequestBody CreateAssignmentRequest createAssignmentRequest) {
        return orderService.createAssignments(createAssignmentRequest);
    }

    @GetMapping
    @RateLimited
    public List<OrderDto> findOrders(@RequestParam(defaultValue = "1") int limit,
                                     @RequestParam(defaultValue = "0") int offset) {
        return orderService.findOrders(limit, offset);
    }

    @GetMapping("/{order_id}")
    @RateLimited
    public OrderDto findOrderById(@PathVariable("order_id") long orderId) {
        return orderService.findOrderById(orderId);
    }
}
