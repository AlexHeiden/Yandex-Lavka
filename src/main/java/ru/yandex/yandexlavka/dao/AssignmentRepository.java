package ru.yandex.yandexlavka.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.pojo.dto.entity.AssignmentDto;
import ru.yandex.yandexlavka.pojo.dto.entity.CourierRawMetaInfoDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AssignmentRepository {

    private final EntityManager entityManager;

    @Autowired
    public AssignmentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persist(AssignmentDto assignmentDto) {
        entityManager.persist(assignmentDto);
    }

    public List<AssignmentDto> findAssignmentByOrder(Long orderId) {
        return entityManager.createQuery("from AssignmentDto where orderDto.orderId = :orderId")
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<AssignmentDto> findAssignmentsByCourierAndDate(Long courierId, LocalDate date) {
        return entityManager.createQuery("from AssignmentDto where courierDto.courierId = :courierId " +
                        "and creationDate = :date")
                .setParameter("courierId", courierId)
                .setParameter("date", date)
                .getResultList();
    }

    public List<AssignmentDto> findAssignmentsByDate(LocalDate date) {
        return entityManager.createQuery("from AssignmentDto where creationDate = :date")
                .setParameter("date", date)
                .getResultList();
    }

    public CourierRawMetaInfoDto getRawMetaInfo(long courierId,
                                                LocalDateTime startDateTime,
                                                LocalDateTime endDateTime) {
        return entityManager.createQuery("select new "
                + "ru.yandex.yandexlavka.pojo.dto.entity.CourierRawMetaInfoDto("
                        + "coalesce(sum(orderDto.cost), 0), count(*)) "
                + "from AssignmentDto "
                + "where courierDto.courierId = :courierId "
                + "and orderDto.completedTime >= :startDateTime "
                + "and orderDto.completedTime < :endDateTime", CourierRawMetaInfoDto.class)
                .setParameter("courierId", courierId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getSingleResult();
    }
}
