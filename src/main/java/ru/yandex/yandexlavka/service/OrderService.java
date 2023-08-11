package ru.yandex.yandexlavka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.common_validation_utils.EntityValidationUtils;
import ru.yandex.yandexlavka.common_validation_utils.TimeValidationUtils;
import ru.yandex.yandexlavka.dao.AssignmentRepository;
import ru.yandex.yandexlavka.dao.CourierRepository;
import ru.yandex.yandexlavka.dao.OrderRepository;
import ru.yandex.yandexlavka.pojo.dto.CompleteOrderDto;
import ru.yandex.yandexlavka.pojo.dto.CreateAssignmentDto;
import ru.yandex.yandexlavka.pojo.dto.entity.AssignmentDto;
import ru.yandex.yandexlavka.pojo.dto.entity.CourierDto;
import ru.yandex.yandexlavka.pojo.request.CompleteOrderRequest;
import ru.yandex.yandexlavka.pojo.request.CreateAssignmentRequest;
import ru.yandex.yandexlavka.pojo.response.CreateAssignmentResponse;
import ru.yandex.yandexlavka.rest.exception_handling.exception.IllegalRequestArgumentException;
import ru.yandex.yandexlavka.rest.exception_handling.exception.ResourceNotFoundException;
import ru.yandex.yandexlavka.pojo.dto.CreateOrderDto;
import ru.yandex.yandexlavka.pojo.dto.entity.OrderDto;
import ru.yandex.yandexlavka.pojo.request.CreateOrderRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AssignmentRepository assignmentRepository;
    private final CourierRepository courierRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        AssignmentRepository assignmentRepository,
                        CourierRepository courierRepository) {
        this.orderRepository = orderRepository;
        this.assignmentRepository = assignmentRepository;
        this.courierRepository = courierRepository;
    }

    @Transactional
    public List<OrderDto> createOrders(CreateOrderRequest createOrderRequest) {
        List<OrderDto> orderDtos = validateCreateOrderRequest(createOrderRequest);

        //save in db
        for (OrderDto orderDto : orderDtos) {
            orderRepository.persist(orderDto);
        }

        return orderDtos;
    }

    @Transactional
    public List<OrderDto> completeOrders(CompleteOrderRequest completeOrderRequest) {
        List<OrderDto> orderDtos = validateCompleteOrderRequest(completeOrderRequest);

        //save in db
        for (OrderDto orderDto : orderDtos) {
            orderRepository.persist(orderDto);
        }

        return orderDtos;
    }

    @Transactional
    public List<CreateAssignmentResponse> createAssignments(CreateAssignmentRequest createAssignmentRequest) {
        List<AssignmentDto> assignmentDtos = validateCreateAssignmentRequest(createAssignmentRequest);

        //save in db
        List<CreateAssignmentResponse> createAssignmentResponses = new ArrayList<>();
        for (AssignmentDto assignmentDto : assignmentDtos) {
            assignmentRepository.persist(assignmentDto);
            createAssignmentResponses.add(new CreateAssignmentResponse(assignmentDto));
        }

        return createAssignmentResponses;
    }

    public List<OrderDto> findOrders(int limit, int offset) {
        StringBuilder sb = new StringBuilder();
        if (limit < 1) {
            sb.append("Invalid parameter 'limit': ")
                    .append(limit)
                    .append("; ");
        }

        if (offset < 0) {
            sb.append("Invalid parameter 'offset': ")
                    .append(offset)
                    .append("; ");
        }

        if (sb.length() != 0) {
            sb.delete(sb.length() - 2, sb.length());
            throw new IllegalRequestArgumentException(sb.toString());
        }

        return orderRepository.find(limit, offset);
    }

    public OrderDto findOrderById(long orderId) {
        OrderDto orderDto = orderRepository.findById(orderId);
        if (orderDto == null) {
            throw new ResourceNotFoundException("The order with id = " + orderId + " does not exist");
        }

        return orderDto;
    }

    private List<OrderDto> validateCreateOrderRequest(CreateOrderRequest createOrderRequest) {
        //check orders for validity
        List<CreateOrderDto> createOrderDtos = createOrderRequest.getOrders();
        if (createOrderDtos == null || createOrderDtos.isEmpty()) {
            throw new IllegalRequestArgumentException("The parameter 'orders' is missing");
        }

        //fill OrderDto list
        List<OrderDto> orderDtos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < createOrderDtos.size(); i++) {
            OrderDto orderDto = validateOrderDto(createOrderDtos.get(i),
                    sb,
                    i);
            if (orderDto != null) {
                orderDtos.add(orderDto);
            }
        }

        //check for validity
        if (sb.length() != 0) {
            sb.delete(sb.length() - 3, sb.length());
            throw new IllegalRequestArgumentException(sb.toString());
        }

        return orderDtos;
    }

    private List<AssignmentDto> validateCreateAssignmentRequest(CreateAssignmentRequest createAssignmentRequest) {
        //check assignments
        List<CreateAssignmentDto> createAssignmentDtos = createAssignmentRequest.getAssignments();
        if (createAssignmentDtos == null || createAssignmentDtos.isEmpty()) {
            throw new IllegalRequestArgumentException("The parameter 'assignments' is missing");
        }

        //fill AssignmentDto list
        List<AssignmentDto> assignmentDtos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < createAssignmentDtos.size(); i++) {
            AssignmentDto assignmentDto = validateCreateAssignmentDto(createAssignmentDtos.get(i),
                    sb,
                    i);
            if (assignmentDto != null) {
                assignmentDtos.add(assignmentDto);
            }
        }

        //check for validity
        if (sb.length() != 0) {
            sb.delete(sb.length() - 3, sb.length());
            throw new IllegalRequestArgumentException(sb.toString());
        }

        return assignmentDtos;
    }

    private List<OrderDto> validateCompleteOrderRequest(CompleteOrderRequest completeOrderRequest) {
        //check complete_info for validity
        List<CompleteOrderDto> completeOrderDtos = completeOrderRequest.getCompleteInfo();
        if (completeOrderDtos == null || completeOrderDtos.isEmpty()) {
            throw new IllegalRequestArgumentException("The parameter 'complete_info' is missing");
        }

        //fill OrderDto list
        List<OrderDto> orderDtos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < completeOrderDtos.size(); i++) {
            OrderDto orderDto = validateCompleteOrderDto(completeOrderDtos.get(i),
                    sb,
                    i);
            if (orderDto != null) {
                orderDtos.add(orderDto);
            }
        }

        //check for validity
        if (sb.length() != 0) {
            sb.delete(sb.length() - 3, sb.length());
            throw new IllegalRequestArgumentException(sb.toString());
        }

        return orderDtos;
    }

    private OrderDto validateCompleteOrderDto(CompleteOrderDto completeOrderDto, StringBuilder sb, int index) {
        StringBuilder completeOrderStringBuilder = new StringBuilder();

        //check courier_id
        Long courierId = completeOrderDto.getCourierId();
        if (courierId == null) {
            completeOrderStringBuilder.append("The parameter 'courier_id' is missing; ");
        }

        //check order_id
        Long orderId = completeOrderDto.getOrderId();
        if (orderId == null) {
            completeOrderStringBuilder.append("The parameter 'order_id' is missing; ");
        }

        //check complete_time
        LocalDateTime completeTime = LocalDateTime.now();
        String completeTimeString = completeOrderDto.getCompleteTime();
        if (completeTimeString == null || completeTimeString.trim().isEmpty()) {
            completeOrderStringBuilder.append("The parameter 'complete_time' is missing; ");
        } else {
            try {
                completeTime = TimeValidationUtils.parseDateTime(completeOrderDto.getCompleteTime());
            } catch (DateTimeParseException ex) {
                completeOrderStringBuilder.append("Invalid time: ")
                        .append(completeOrderDto.getCompleteTime())
                        .append("; ");
            }
        }

        if (!EntityValidationUtils.isEntityValid(sb, "Order", index, completeOrderStringBuilder)) {
            return null;
        }

        //check courier existence
        CourierDto courierDto = courierRepository.findById(courierId);
        if (courierDto == null) {
            completeOrderStringBuilder.append("The courier with id = ")
                    .append(courierId)
                    .append(" does not exist; ");
        }

        //check order existence
        OrderDto orderDto = orderRepository.findById(orderId);
        if (orderDto == null) {
            completeOrderStringBuilder.append("The order with id = ")
                    .append(orderId)
                    .append(" does not exist; ");
        }

        if (!EntityValidationUtils.isEntityValid(sb, "Order", index, completeOrderStringBuilder)) {
            return null;
        }

        //check if order is completed
        if (orderDto.getCompletedTime() != null) {
            completeOrderStringBuilder.append("The order is already completed; ");
        }

        if (!EntityValidationUtils.isEntityValid(sb, "Order", index, completeOrderStringBuilder)) {
            return null;
        }

        //check order assignment existence
        List<AssignmentDto> assignmentDtos = assignmentRepository.findAssignmentByOrder(orderId);
        if (assignmentDtos.isEmpty()) {
            completeOrderStringBuilder.append("The order has not been assigned yet; ");
        }

        if (!EntityValidationUtils.isEntityValid(sb, "Order", index, completeOrderStringBuilder)) {
            return null;
        }

        //check if courier is assigned to order
        AssignmentDto assignmentDto = assignmentDtos.get(0);
        if (assignmentDto.getCourierDto().getCourierId() != courierId) {
            completeOrderStringBuilder.append("The order was assigned for another courier; ");
        }

        if (!EntityValidationUtils.isEntityValid(sb, "Order", index, completeOrderStringBuilder)) {
            return null;
        }

        orderDto.setCompletedTime(completeTime);

        return orderDto;
    }

    private OrderDto validateOrderDto(CreateOrderDto createOrderDto,
                                      StringBuilder sb,
                                      int index) {
        StringBuilder orderStringBuilder = new StringBuilder();

        //check weight
        Float weight = createOrderDto.getWeight();
        if (weight == null) {
            orderStringBuilder.append("The parameter 'weight' is missing; ");
        } else if (weight <= 0) {
            orderStringBuilder.append("Invalid weight: ")
                    .append(weight)
                    .append("; ");
        }

        //check regions
        Integer regions = createOrderDto.getRegions();
        if (regions == null) {
            orderStringBuilder.append("The parameter 'regions' is missing; ");
        } else if (regions <= 0) {
            orderStringBuilder.append("Invalid region: ")
                    .append(regions)
                    .append("; ");
        }

        //check deliveryHours
        List<String> deliveryHours = TimeValidationUtils.validateTimeIntervals(createOrderDto.getDeliveryHours(),
                orderStringBuilder,
                "delivery_hours");

        //check cost
        Integer cost = createOrderDto.getCost();
        if (cost == null) {
            orderStringBuilder.append("The parameter 'cost' is missing; ");
        } else if (cost <= 0) {
            orderStringBuilder.append("Invalid cost: ")
                    .append(cost)
                    .append("; ");
        }

        if (!EntityValidationUtils.isEntityValid(sb, "Order", index, orderStringBuilder)) {
            return null;
        }

        return new OrderDto(weight, regions, deliveryHours, cost, null);
    }

    private AssignmentDto validateCreateAssignmentDto(CreateAssignmentDto createAssignmentDto,
                                                      StringBuilder sb,
                                                      int index) {
        StringBuilder createAssignmentStringBuilder = new StringBuilder();

        //check courier_id
        Long courierId = createAssignmentDto.getCourierId();
        if (courierId == null) {
            createAssignmentStringBuilder.append("The parameter 'courier_id' is missing; ");
        }

        //check order_id
        Long orderId = createAssignmentDto.getOrderId();
        if (orderId == null) {
            createAssignmentStringBuilder.append("The parameter 'order_id' is missing; ");
        }

        //check creation_date
        LocalDate creationDate = LocalDate.now();
        String creationDateString = createAssignmentDto.getCreationDate();
        if (creationDateString == null || creationDateString.trim().isEmpty()) {
            createAssignmentStringBuilder.append("The parameter 'creation_date' is missing; ");
        } else {
            try {
                creationDate = TimeValidationUtils.parseDate(creationDateString.trim());
            } catch (DateTimeParseException ex) {
                createAssignmentStringBuilder.append("Invalid parameter 'creation_date': ")
                        .append(createAssignmentDto.getCreationDate())
                        .append("; ");
            }
        }

        if (!EntityValidationUtils.isEntityValid(sb, "Assignment", index, createAssignmentStringBuilder)) {
            return null;
        }

        //check courier existence
        CourierDto courierDto = courierRepository.findById(courierId);
        if (courierDto == null) {
            createAssignmentStringBuilder.append("The courier with id = ")
                    .append(courierId)
                    .append(" does not exist; ");
        }

        //check order existence
        OrderDto orderDto = orderRepository.findById(orderId);
        if (orderDto == null) {
            createAssignmentStringBuilder.append("The order with id = ")
                    .append(orderId)
                    .append(" does not exist; ");
        }

        if (!EntityValidationUtils.isEntityValid(sb, "Assignment", index, createAssignmentStringBuilder)) {
            return null;
        }

        //check order assignment existence
        List<AssignmentDto> assignmentDtos = assignmentRepository.findAssignmentByOrder(orderId);
        if (!assignmentDtos.isEmpty()) {
            createAssignmentStringBuilder.append("The order is already assigned; ");
        }

        //check if order is completed
        if (orderDto.getCompletedTime() != null) {
            createAssignmentStringBuilder.append("The order is already completed; ");
        }

        if (!EntityValidationUtils.isEntityValid(sb, "Assignment", index, createAssignmentStringBuilder)) {
            return null;
        }

        return new AssignmentDto(courierDto, orderDto, creationDate);
    }
}
