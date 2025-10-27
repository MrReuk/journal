package com.mpt.journal.service;

import com.mpt.journal.model.SalesModel;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface SalesService {
    Page<SalesModel> findAll(int page, int size);
    Page<SalesModel> search(Integer id, Long employeeId, Long buyerId,
                            Integer payId, Integer lumberId,
                            int page, int size);
    SalesModel save(SalesModel sales);
    void deleteById(Integer id);
    Optional<SalesModel> findById(Integer id);

    // Новый метод для поиска заказов по покупателю
    List<SalesModel> findByBuyerId(Long buyerId);
}