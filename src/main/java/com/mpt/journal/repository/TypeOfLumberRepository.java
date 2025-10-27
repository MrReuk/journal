package com.mpt.journal.repository;

import com.mpt.journal.model.TypeOfLumberModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfLumberRepository extends JpaRepository<TypeOfLumberModel, Long> {

    @Query("SELECT t FROM TypeOfLumberModel t WHERE " +
            "(:id IS NULL OR t.id = :id) AND " +
            "(:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:area IS NULL OR LOWER(t.areaOfSpeciesion) LIKE LOWER(CONCAT('%', :area, '%')))")
    Page<TypeOfLumberModel> search(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("area") String area,
            Pageable pageable);
}