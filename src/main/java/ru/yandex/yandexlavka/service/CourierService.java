package ru.yandex.yandexlavka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.common_validation_utils.EntityValidationUtils;
import ru.yandex.yandexlavka.common_validation_utils.TimeValidationUtils;
import ru.yandex.yandexlavka.dao.AssignmentRepository;
import ru.yandex.yandexlavka.dao.CourierRepository;
import ru.yandex.yandexlavka.dao.CourierTypeRepository;
import ru.yandex.yandexlavka.pojo.dto.CouriersOrdersDto;
import ru.yandex.yandexlavka.pojo.dto.entity.AssignmentDto;
import ru.yandex.yandexlavka.pojo.dto.entity.CourierRawMetaInfoDto;
import ru.yandex.yandexlavka.pojo.response.GetCourierMetaInfoResponse;
import ru.yandex.yandexlavka.pojo.response.OrderAssignResponse;
import ru.yandex.yandexlavka.rest.exception_handling.exception.IllegalRequestArgumentException;
import ru.yandex.yandexlavka.rest.exception_handling.exception.ResourceNotFoundException;
import ru.yandex.yandexlavka.pojo.dto.CreateCourierDto;
import ru.yandex.yandexlavka.pojo.dto.entity.CourierDto;
import ru.yandex.yandexlavka.pojo.dto.entity.CourierTypeDto;
import ru.yandex.yandexlavka.pojo.request.CreateCourierRequest;
import ru.yandex.yandexlavka.pojo.response.CreateCourierResponse;
import ru.yandex.yandexlavka.pojo.response.GetCouriersResponse;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class CourierService {

    private final CourierRepository courierRepository;
    private final CourierTypeRepository courierTypeRepository;
    private final AssignmentRepository assignmentRepository;

    @Autowired
    public CourierService(CourierRepository courierRepository,
                          CourierTypeRepository courierTypeRepository,
                          AssignmentRepository assignmentRepository) {
        this.courierRepository = courierRepository;
        this.courierTypeRepository = courierTypeRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Transactional
    public CreateCourierResponse createCouriers(CreateCourierRequest createCourierRequest) {
        List<CourierDto> courierDtos = validateCreateCourierRequest(createCourierRequest);

        //save in db
        for (CourierDto courierDto : courierDtos) {
            courierRepository.persist(courierDto);
        }

        return new CreateCourierResponse(courierDtos);
    }

    public GetCouriersResponse findCouriers(int limit, int offset) {
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

        return new GetCouriersResponse(courierRepository.find(limit, offset), limit, offset);
    }

    public CourierDto findCourierById(long courierId) {
        CourierDto courierDto = courierRepository.findById(courierId);
        if (courierDto == null) {
            throw new ResourceNotFoundException("The courier with id = " + courierId + " does not exist");
        }

        return courierDto;
    }

    public OrderAssignResponse findAssignments(String dateString, Long courierId) {
        StringBuilder sb = new StringBuilder();

        //check if date is valid
        LocalDate date = LocalDate.now();
        if (dateString != null && !dateString.trim().isEmpty()) {
            try {
                date = TimeValidationUtils.parseDate(dateString.trim());
            } catch (DateTimeParseException ex) {
                sb.append("Invalid parameter 'date': ")
                        .append(dateString.trim())
                        .append("; ");
            }
        }

        //check if courier exists
        CourierDto courierDto = null;
        if (courierId != null) {
            courierDto = courierRepository.findById(courierId);

            if (courierDto == null) {
                sb.append("The courier with id = " + courierId + " does not exist; ");
            }
        }

        if (sb.length() != 0) {
            sb.delete(sb.length() - 2, sb.length());
            throw new IllegalRequestArgumentException(sb.toString());
        }

        //get assignments
        List<AssignmentDto> assignmentDtos;
        if (courierDto == null) {
            assignmentDtos = assignmentRepository.findAssignmentsByDate(date);
        } else {
            assignmentDtos = assignmentRepository.findAssignmentsByCourierAndDate(courierId, date);
        }

        //fill response
        HashMap<Long, CouriersOrdersDto> assignmentsMap = new HashMap<>();
        for (AssignmentDto assignmentDto : assignmentDtos) {
            Long assignmentCourierId = assignmentDto.getCourierDto().getCourierId();
            if (assignmentsMap.containsKey(assignmentCourierId)) {
                assignmentsMap.get(assignmentCourierId)
                        .getOrders()
                        .add(assignmentDto.getOrderDto());
            } else {
                CouriersOrdersDto couriersOrdersDto = new CouriersOrdersDto(assignmentCourierId,
                        new ArrayList<>());
                couriersOrdersDto.getOrders().add(assignmentDto.getOrderDto());
                assignmentsMap.put(assignmentCourierId, couriersOrdersDto);
            }
        }

        ArrayList<CouriersOrdersDto> couriersOrdersDtoArrayList = new ArrayList<>();
        for (Map.Entry<Long, CouriersOrdersDto> entry : assignmentsMap.entrySet()) {
            couriersOrdersDtoArrayList.add(entry.getValue());
        }

        return new OrderAssignResponse(date.toString(), couriersOrdersDtoArrayList);
    }

    public GetCourierMetaInfoResponse getCourierMetaInfo(long courierId, String startDateString, String endDateString) {
        StringBuilder sb = new StringBuilder();

        //check if courier exists
        CourierDto courierDto = courierRepository.findById(courierId);
        if (courierDto == null) {
            sb.append("The courier with id = " + courierId + " does not exist; ");
        }

        //check if startDate is valid
        LocalDate startDate = LocalDate.now();
        startDateString = startDateString.trim();
        if (startDateString.isEmpty()) {
            sb.append("The parameter 'startDate' is missing; ");
        } else {
            try {
                startDate = TimeValidationUtils.parseDate(startDateString);
            } catch (DateTimeParseException ex) {
                sb.append("Invalid parameter 'startDate': ")
                        .append(startDateString)
                        .append("; ");
            }
        }

        //check if endDate is valid
        LocalDate endDate = LocalDate.now();
        endDateString = endDateString.trim();
        if (endDateString.isEmpty()) {
            sb.append("The parameter 'endDate' is missing; ");
        } else {
            try {
                endDate = TimeValidationUtils.parseDate(endDateString);
            } catch (DateTimeParseException ex) {
                sb.append("Invalid parameter 'endDate': ")
                        .append(endDateString)
                        .append("; ");
            }
        }

        if (sb.length() != 0) {
            sb.delete(sb.length() - 2, sb.length());
            throw new IllegalRequestArgumentException(sb.toString());
        }

        //check if endDate is after startDate
        if (!endDate.isAfter(startDate)) {
            sb.append("Invalid parameters 'startDate' and 'endDate'. 'endDate' must go after 'startDate'; ");
        }

        if (sb.length() != 0) {
            sb.delete(sb.length() - 2, sb.length());
            throw new IllegalRequestArgumentException(sb.toString());
        }

        //get raw meta-info
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();
        CourierRawMetaInfoDto courierRawMetaInfoDto = assignmentRepository.getRawMetaInfo(courierId,
                startDateTime,
                endDateTime);

        CourierTypeDto courierTypeDto = courierTypeRepository.findByName(courierDto.getCourierType());

        //calculate rating and earnings
        int rating = calculateRating(courierRawMetaInfoDto.getCountOfCompleteOrders(),
                startDateTime,
                endDateTime,
                courierTypeDto.getRatingCoef());

        int earnings = calculateEarnings(courierRawMetaInfoDto.getEarningsWithoutStatCoef(),
                courierTypeDto.getStatSalaryCoef());

        return new GetCourierMetaInfoResponse(courierDto, rating, earnings);
    }

    private List<CourierDto> validateCreateCourierRequest(CreateCourierRequest createCourierRequest) {
        //check couriers for validity
        List<CreateCourierDto> createCourierDtos = createCourierRequest.getCouriers();
        if (createCourierDtos == null || createCourierDtos.isEmpty()) {
            throw new IllegalRequestArgumentException("The parameter 'couriers' is missing");
        }

        //fill CourierDto list
        List<CourierDto> courierDtos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < createCourierDtos.size(); i++) {
            CourierDto courierDto = validateCreateCourierDto(createCourierDtos.get(i),
                    sb,
                    i);
            if (courierDto != null) {
                courierDtos.add(courierDto);
            }
        }

        //check for validity
        if (sb.length() != 0) {
            sb.delete(sb.length() - 3, sb.length());
            throw new IllegalRequestArgumentException(sb.toString());
        }

        return courierDtos;
    }

    private CourierDto validateCreateCourierDto(CreateCourierDto createCourierDto,
                                                StringBuilder sb,
                                                int courierIndex) {
        StringBuilder courierStringBuilder = new StringBuilder();

        //get courier types
        List<CourierTypeDto> courierTypeDtos = courierTypeRepository.findAll();
        HashMap<String, CourierTypeDto> courierTypeDtoHashMap = new HashMap<>();
        for (CourierTypeDto courierTypeDto : courierTypeDtos) {
            courierTypeDtoHashMap.put(courierTypeDto.getName(), courierTypeDto);
        }

        String courierType = validateCourierType(createCourierDto.getCourierType(),
                courierTypeDtoHashMap,
                courierStringBuilder);
        List<Integer> regions = validateRegions(createCourierDto.getRegions(),
                courierType,
                courierTypeDtoHashMap,
                courierStringBuilder);
        List<String> workingHours = TimeValidationUtils.validateTimeIntervals(createCourierDto.getWorkingHours(),
                courierStringBuilder,
                "working_hours");

        //save data about invalid arguments
        if (!EntityValidationUtils.isEntityValid(sb, "Courier", courierIndex, courierStringBuilder)) {
            return null;
        }

        return new CourierDto(courierType, regions, workingHours);
    }

    //check courier_type
    private String validateCourierType(String courierType,
                                       HashMap<String, CourierTypeDto> courierTypeDtoHashMap,
                                       StringBuilder courierStringBuilder) {
        if (courierType == null || courierType.trim().isEmpty()) {
            courierStringBuilder.append("The parameter 'courier_type' is missing; ");
        } else if (!courierTypeDtoHashMap.containsKey(courierType.trim())) {
            courierStringBuilder.append("The entered 'courier_type' does not exist: ")
                    .append(courierType.trim())
                    .append("; ");
        }

        return courierType;
    }

    //check regions
    private List<Integer> validateRegions(List<Integer> regions,
                                          String courierType,
                                          HashMap<String, CourierTypeDto> courierTypeDtoHashMap,
                                          StringBuilder courierStringBuilder) {
        if (regions == null || regions.isEmpty()) {
            courierStringBuilder.append("The parameter 'regions' is missing; ");
        } else {
            //check if too many regions
            if (courierType != null
                    && courierTypeDtoHashMap.containsKey(courierType.trim())
                    && regions.size() > courierTypeDtoHashMap.get(courierType.trim()).getMaxRegionNumber()) {
                courierStringBuilder.append("Too many regions (")
                        .append(regions.size())
                        .append(") for a courier with type ")
                        .append(courierType.trim())
                        .append(". The maximum of regions for it: ")
                        .append(courierTypeDtoHashMap.get(courierType.trim()).getMaxRegionNumber())
                        .append("; ");
            }

            //check invalid regions
            String invalidRegions = regions.stream()
                    .filter(region -> region <= 0)
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));
            if (!invalidRegions.isEmpty()) {
                courierStringBuilder.append("Invalid regions: ")
                        .append(invalidRegions)
                        .append("; ");
            }

            //check repeating regions
            String repeatingRegions = regions.stream()
                    .collect(Collectors.groupingBy(Function.identity()))
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().size() > 1)
                    .map(Map.Entry::getKey)
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));
            if (!repeatingRegions.isEmpty()) {
                courierStringBuilder.append("Repeating region numbers: ")
                        .append(repeatingRegions)
                        .append("; ");
            }
        }

        return regions;
    }

    private int calculateRating(Long countOfCompleteOrders,
                                LocalDateTime startDateTime,
                                LocalDateTime endDateTime,
                                int ratingCoef) {
        return (int) (((double) countOfCompleteOrders
                        / Duration.between(startDateTime, endDateTime).toHours())
                * ratingCoef);
    }

    private int calculateEarnings(Long earningsWithoutStatCoef, Integer statSalaryCoef) {
        return (int) (earningsWithoutStatCoef * statSalaryCoef);
    }
}
