package com.mpt.journal.service;

import com.mpt.journal.model.SalesModel;
import com.mpt.journal.repository.SalesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService {
    private final SalesRepository repository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public SalesServiceImpl(SalesRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesModel> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesModel> search(Integer id, Long employeeId, Long buyerId,
                                   Integer payId, Integer lumberId,
                                   int page, int size) {
        return repository.search(
                id != null ? id : null,
                employeeId != null ? employeeId : null,
                buyerId != null ? buyerId : null,
                payId != null ? payId : null,
                lumberId != null ? lumberId : null,
                PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE)
        );
    }

    @Override
    @Transactional
    public SalesModel save(SalesModel sales) {
        return repository.save(sales);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalesModel> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalesModel> findByBuyerId(Long buyerId) {
        return repository.findByBuyerId(buyerId);
    }
}