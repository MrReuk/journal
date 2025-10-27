package com.mpt.journal.repository;

import com.mpt.journal.model.LumberModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface LumberRepository extends JpaRepository<LumberModel, Integer> {

    @Query("SELECT l FROM LumberModel l LEFT JOIN FETCH l.typeOfLumber LEFT JOIN FETCH l.treeSpecies WHERE " +
            "(:id IS NULL OR l.id = :id) AND " +
            "(:typeId IS NULL OR l.typeOfLumber.id = :typeId) AND " +
            "(:speciesId IS NULL OR l.treeSpecies.id = :speciesId) AND " +
            "(:volumeMin IS NULL OR l.volume >= :volumeMin) AND " +
            "(:volumeMax IS NULL OR l.volume <= :volumeMax)")
    Page<LumberModel> search(
            @Param("id") Integer id,
            @Param("typeId") Long typeId,
            @Param("speciesId") Integer speciesId,
            @Param("volumeMin") BigDecimal volumeMin,
            @Param("volumeMax") BigDecimal volumeMax,
            Pageable pageable);
}