package com.mpt.journal.service;

import com.mpt.journal.model.TreeSpeciesModel;
import com.mpt.journal.repository.TreeSpeciesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class TreeSpeciesServiceImpl implements TreeSpeciesService {
    private final TreeSpeciesRepository repository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public TreeSpeciesServiceImpl(TreeSpeciesRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TreeSpeciesModel> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TreeSpeciesModel> search(Integer id, String name, int page, int size) {
        return repository.search(id,
                name != null && !name.isEmpty() ? name : null,
                PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional
    public TreeSpeciesModel save(TreeSpeciesModel treeSpecies) {
        return repository.save(treeSpecies);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TreeSpeciesModel> findById(Integer id) {
        return repository.findById(id);
    }
}