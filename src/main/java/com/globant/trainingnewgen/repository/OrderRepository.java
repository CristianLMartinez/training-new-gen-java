package com.globant.trainingnewgen.repository;


import com.globant.trainingnewgen.model.dto.SalesData;
import com.globant.trainingnewgen.model.dto.SalesDataProjection;
import com.globant.trainingnewgen.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUuid(UUID uuid);


    @Query("""
            SELECT
            p.fantasyName AS fantasyName,
            SUM(o.quantity) AS totalQuantity,
            SUM(o.quantity * p.price) AS totalRevenue
            FROM Order o JOIN o.product p
            WHERE FUNCTION('date', o.creationDateTime) BETWEEN :startDate AND :endDate
            GROUP BY p.fantasyName HAVING SUM(o.quantity) > 0
            """)
    List<SalesDataProjection> findSalesDataBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


}