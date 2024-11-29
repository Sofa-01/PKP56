package com.pizzaro.repo;

import com.pizzaro.model.Orderings;
import com.pizzaro.model.enums.OrderingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingsRepo extends JpaRepository<Orderings, Long> {
    List<Orderings> findAllByOwner_IdAndStatus(Long id, OrderingStatus status);

    List<Orderings> findAllByStatus(OrderingStatus status);

    List<Orderings> findAllByManager_IdAndStatus(Long id, OrderingStatus status);

    List<Orderings> findAllByManager_Id(Long id);
}
