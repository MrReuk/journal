package com.mpt.journal.service;

import com.mpt.journal.model.TreeSpeciesModel;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface TreeSpeciesService {
    Page<TreeSpeciesModel> findAll(int page, int size);
    Page<TreeSpeciesModel> search(Integer id, String name, int page, int size);
    TreeSpeciesModel save(TreeSpeciesModel treeSpecies);
    void deleteById(Integer id);
    Optional<TreeSpeciesModel> findById(Integer id);
}