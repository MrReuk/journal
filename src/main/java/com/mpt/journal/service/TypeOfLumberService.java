package com.mpt.journal.service;

import com.mpt.journal.model.TypeOfLumberModel;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface TypeOfLumberService {
    Page<TypeOfLumberModel> findAll(int page, int size);
    Page<TypeOfLumberModel> search(Long id, String name, String area, int page, int size);
    TypeOfLumberModel save(TypeOfLumberModel typeOfLumber);
    void deleteById(Long id);
    Optional<TypeOfLumberModel> findById(Long id);
}