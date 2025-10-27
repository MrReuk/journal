package com.mpt.journal.repository;

import com.mpt.journal.model.TreeSpeciesModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TreeSpeciesRepository extends JpaRepository<TreeSpeciesModel, Integer> {

    @Query("SELECT t FROM TreeSpeciesModel t WHERE " +
            "(:id IS NULL OR t.id = :id) AND " +
            "(:name IS NULL OR t.name LIKE %:name%)")
    Page<TreeSpeciesModel> search(
            @Param("id") Integer id,
            @Param("name") String name,
            Pageable pageable);
}