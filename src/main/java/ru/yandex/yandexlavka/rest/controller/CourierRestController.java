package ru.yandex.yandexlavka.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.pojo.dto.entity.CourierDto;
import ru.yandex.yandexlavka.pojo.request.CreateCourierRequest;
import ru.yandex.yandexlavka.pojo.response.CreateCourierResponse;
import ru.yandex.yandexlavka.pojo.response.GetCourierMetaInfoResponse;
import ru.yandex.yandexlavka.pojo.response.GetCouriersResponse;
import ru.yandex.yandexlavka.pojo.response.OrderAssignResponse;
import ru.yandex.yandexlavka.rest.rate_limit.RateLimited;
import ru.yandex.yandexlavka.service.CourierService;

@RestController
@RequestMapping("/couriers")
public class CourierRestController {

    private final CourierService courierService;

    @Autowired
    public CourierRestController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping
    @RateLimited
    public CreateCourierResponse createCouriers(@RequestBody CreateCourierRequest createCourierRequest) {
        return courierService.createCouriers(createCourierRequest);
    }

    @GetMapping
    @RateLimited
    public GetCouriersResponse findCouriers(@RequestParam(defaultValue = "1") int limit,
                                            @RequestParam(defaultValue = "0") int offset) {
        return courierService.findCouriers(limit, offset);
    }

    @GetMapping("/{courier_id}")
    @RateLimited
    public CourierDto findCourierById(@PathVariable("courier_id") long courierId) {
        return courierService.findCourierById(courierId);
    }

    @GetMapping("/assignments")
    @RateLimited
    public OrderAssignResponse getAssignments(@RequestParam(required = false) String date,
                                              @RequestParam(name = "courier_id", required = false) Long courierId) {
        return courierService.findAssignments(date, courierId);
    }

    @GetMapping("/meta-info/{courier_id}")
    @RateLimited
    public GetCourierMetaInfoResponse getCourierMetaInfo(@PathVariable("courier_id") long courierId,
                                                         @RequestParam String startDate,
                                                         @RequestParam String endDate) {
        return courierService.getCourierMetaInfo(courierId, startDate, endDate);
    }
}
