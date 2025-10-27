package com.mpt.journal.service;

import com.mpt.journal.model.PayModel;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.util.Optional;

public interface PayService {
    Page<PayModel> findAll(int page, int size);
    Page<PayModel> search(Integer id, Integer typePayId, Long buyerId,
                          BigDecimal priceMin, BigDecimal priceMax,
                          int page, int size);
    PayModel save(PayModel pay);
    void deleteById(Integer id);
    Optional<PayModel> findById(Integer id);
}