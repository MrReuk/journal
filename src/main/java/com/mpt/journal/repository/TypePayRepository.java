package com.mpt.journal.repository;

import com.mpt.journal.model.TypePayModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePayRepository extends JpaRepository<TypePayModel, Integer> {

    @Query("SELECT t FROM TypePayModel t WHERE " +
            "(:id IS NULL OR t.id = :id) AND " +
            "(:name IS NULL OR t.name LIKE %:name%)")
    Page<TypePayModel> search(
            @Param("id") Integer id,
            @Param("name") String name,
            Pageable pageable);
}