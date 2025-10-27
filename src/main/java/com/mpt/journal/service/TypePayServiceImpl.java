package com.mpt.journal.service;

import com.mpt.journal.model.TypePayModel;
import com.mpt.journal.repository.TypePayRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class TypePayServiceImpl implements TypePayService {
    private final TypePayRepository repository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public TypePayServiceImpl(TypePayRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypePayModel> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypePayModel> search(Integer id, String name, int page, int size) {
        return repository.search(
                id != null && id > 0 ? id : null,
                name != null && !name.isEmpty() ? name : null,
                PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE)
        );
    }

    @Override
    @Transactional
    public TypePayModel save(TypePayModel typePay) {
        return repository.save(typePay);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypePayModel> findById(Integer id) {
        return repository.findById(id);
    }
}