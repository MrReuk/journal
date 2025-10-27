package com.mpt.journal.repository;

import com.mpt.journal.model.RoleUsersModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUsersRepository extends JpaRepository<RoleUsersModel, Integer> {

    @Query("SELECT r FROM RoleUsersModel r WHERE " +
            "(:id IS NULL OR r.id = :id) AND " +
            "(:name IS NULL OR r.name LIKE %:name%)")
    Page<RoleUsersModel> search(
            @Param("id") Integer id,
            @Param("name") String name,
            Pageable pageable);
}