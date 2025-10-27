package com.mpt.journal.service;

import com.mpt.journal.model.RoleUsersModel;
import com.mpt.journal.repository.RoleUsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class RoleUsersServiceImpl implements RoleUsersService {
    private final RoleUsersRepository repository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public RoleUsersServiceImpl(RoleUsersRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleUsersModel> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleUsersModel> search(Integer id, String name, int page, int size) {
        return repository.search(id, name, PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional
    public RoleUsersModel save(RoleUsersModel role) {
        return repository.save(role);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleUsersModel> findById(Integer id) {
        return repository.findById(id);
    }
}