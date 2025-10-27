package com.mpt.journal.service;

import com.mpt.journal.model.LumberModel;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.util.Optional;

public interface LumberService {
    Page<LumberModel> findAll(int page, int size);
    Page<LumberModel> search(Integer id, Long typeId, Integer speciesId,
                             BigDecimal volumeMin, BigDecimal volumeMax,
                             int page, int size);
    LumberModel save(LumberModel lumber);
    void deleteById(Integer id);
    Optional<LumberModel> findById(Integer id);
}