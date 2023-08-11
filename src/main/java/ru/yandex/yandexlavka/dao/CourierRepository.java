package ru.yandex.yandexlavka.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.pojo.dto.entity.CourierDto;

import java.util.List;

@Repository
public class CourierRepository {

    private final EntityManager entityManager;

    @Autowired
    public CourierRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persist(CourierDto courierDto) {
        entityManager.persist(courierDto);
    }

    public CourierDto findById(long id) {
        return entityManager.find(CourierDto.class, id);
    }

    public List<CourierDto> find(int limit, int offset) {
        return entityManager.createQuery("from CourierDto order by courierId")
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }
}
