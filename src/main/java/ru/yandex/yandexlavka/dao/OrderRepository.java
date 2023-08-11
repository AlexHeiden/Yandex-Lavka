package ru.yandex.yandexlavka.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.pojo.dto.entity.OrderDto;

import java.util.List;

@Repository
public class OrderRepository {

    private final EntityManager entityManager;

    @Autowired
    public OrderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persist(OrderDto orderDto) {
        entityManager.persist(orderDto);
    }

    public OrderDto findById(long orderId) {
        return entityManager.find(OrderDto.class, orderId);
    }

    public List<OrderDto> find(int limit, int offset) {
        return entityManager.createQuery("from OrderDto order by orderId")
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }
}
