package com.mpt.journal.repository;

import com.mpt.journal.model.PayModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface PayRepository extends JpaRepository<PayModel, Integer> {

    @Query("SELECT p FROM PayModel p LEFT JOIN FETCH p.typePay LEFT JOIN FETCH p.buyer WHERE " +
            "(:id IS NULL OR p.id = :id) AND " +
            "(:typePayId IS NULL OR p.typePay.id = :typePayId) AND " +
            "(:buyerId IS NULL OR p.buyer.id = :buyerId) AND " +
            "(:priceMin IS NULL OR p.price >= :priceMin) AND " +
            "(:priceMax IS NULL OR p.price <= :priceMax)")
    Page<PayModel> search(
            @Param("id") Integer id,
            @Param("typePayId") Integer typePayId,
            @Param("buyerId") Long buyerId,
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            Pageable pageable);
}