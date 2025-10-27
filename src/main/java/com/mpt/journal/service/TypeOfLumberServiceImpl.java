package com.mpt.journal.service;

import com.mpt.journal.model.TypeOfLumberModel;
import com.mpt.journal.repository.TypeOfLumberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class TypeOfLumberServiceImpl implements TypeOfLumberService {
    private final TypeOfLumberRepository repository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public TypeOfLumberServiceImpl(TypeOfLumberRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfLumberModel> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfLumberModel> search(Long id, String name, String area, int page, int size) {
        return repository.search(id,
                name != null && !name.isEmpty() ? name : null,
                area != null && !area.isEmpty() ? area : null,
                PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional
    public TypeOfLumberModel save(TypeOfLumberModel typeOfLumber) {
        return repository.save(typeOfLumber);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeOfLumberModel> findById(Long id) {
        return repository.findById(id);
    }
}