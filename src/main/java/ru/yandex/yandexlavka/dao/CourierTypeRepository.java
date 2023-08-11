package ru.yandex.yandexlavka.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.pojo.dto.entity.CourierTypeDto;

import java.util.List;

@Repository
public class CourierTypeRepository {

    private EntityManager entityManager;

    @Autowired
    public CourierTypeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<CourierTypeDto> findAll() {
        return entityManager.createQuery("from CourierTypeDto").getResultList();
    }

    public CourierTypeDto findByName(String courierType) {
        return entityManager.createQuery("from CourierTypeDto where name = :courierType",
                        CourierTypeDto.class)
                .setParameter("courierType", courierType)
                .getSingleResult();
    }
}
