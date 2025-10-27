package com.mpt.journal.service;

import com.mpt.journal.model.TypePayModel;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface TypePayService {
    Page<TypePayModel> findAll(int page, int size);
    Page<TypePayModel> search(Integer id, String name, int page, int size);
    TypePayModel save(TypePayModel typePay);
    void deleteById(Integer id);
    Optional<TypePayModel> findById(Integer id);
}