package com.mpt.journal.service;

import com.mpt.journal.model.LumberModel;
import com.mpt.journal.repository.LumberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class LumberServiceImpl implements LumberService {
    private final LumberRepository repository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public LumberServiceImpl(LumberRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LumberModel> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LumberModel> search(Integer id, Long typeId, Integer speciesId,
                                    BigDecimal volumeMin, BigDecimal volumeMax,
                                    int page, int size) {
        return repository.search(id, typeId, speciesId,
                volumeMin, volumeMax,
                PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional
    public LumberModel save(LumberModel lumber) {
        return repository.save(lumber);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LumberModel> findById(Integer id) {
        return repository.findById(id);
    }
}