package com.mpt.journal.service;

import com.mpt.journal.model.PayModel;
import com.mpt.journal.repository.PayRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PayServiceImpl implements PayService {
    private final PayRepository repository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public PayServiceImpl(PayRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PayModel> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PayModel> search(Integer id, Integer typePayId, Long buyerId,
                                 BigDecimal priceMin, BigDecimal priceMax,
                                 int page, int size) {
        return repository.search(
                id != null ? id : null,
                typePayId != null ? typePayId : null,
                buyerId != null ? buyerId : null,
                priceMin != null ? priceMin : null,
                priceMax != null ? priceMax : null,
                PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE)
        );
    }

    @Override
    @Transactional
    public PayModel save(PayModel pay) {
        return repository.save(pay);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PayModel> findById(Integer id) {
        return repository.findById(id);
    }
}