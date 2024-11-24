package com.fiap.burguer.infraestructure.repository;

import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.core.application.enums.StatusOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository  extends CrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findByStatus(StatusOrder status);
    OrderEntity save(OrderEntity orderEntity);
    OrderEntity findById(int id);
    void deleteById(int id);
    @Query(value = "SELECT * FROM \"order\" WHERE status = 'RECEIVED' OR status = 'PREPARATION' OR status = 'READY' ORDER BY CASE WHEN status = 'RECEIVED' THEN 1 WHEN status = 'PREPARATION' THEN 2 WHEN status = 'READY' THEN 3 END, date_created;",nativeQuery = true)
    List<OrderEntity> findAllOrders();
}