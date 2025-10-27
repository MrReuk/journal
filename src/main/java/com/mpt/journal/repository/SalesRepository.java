package com.mpt.journal.repository;

import com.mpt.journal.model.SalesModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<SalesModel, Integer> {

    @Query("SELECT s FROM SalesModel s LEFT JOIN FETCH s.employee LEFT JOIN FETCH s.buyer LEFT JOIN FETCH s.pay LEFT JOIN FETCH s.lumber WHERE " +
            "(:id IS NULL OR s.id = :id) AND " +
            "(:employeeId IS NULL OR s.employee.id = :employeeId) AND " +
            "(:buyerId IS NULL OR s.buyer.id = :buyerId) AND " +
            "(:payId IS NULL OR s.pay.id = :payId) AND " +
            "(:lumberId IS NULL OR s.lumber.id = :lumberId)")
    Page<SalesModel> search(
            @Param("id") Integer id,
            @Param("employeeId") Long employeeId,
            @Param("buyerId") Long buyerId,
            @Param("payId") Integer payId,
            @Param("lumberId") Integer lumberId,
            Pageable pageable);

    // Новый метод для поиска заказов по ID покупателя
    @Query("SELECT s FROM SalesModel s LEFT JOIN FETCH s.employee LEFT JOIN FETCH s.pay LEFT JOIN FETCH s.lumber WHERE s.buyer.id = :buyerId ORDER BY s.id DESC")
    List<SalesModel> findByBuyerId(@Param("buyerId") Long buyerId);
}