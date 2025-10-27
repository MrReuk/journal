package com.mpt.journal.repository;

import com.mpt.journal.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT u FROM UserModel u LEFT JOIN FETCH u.role WHERE u.login = :login")
    Optional<UserModel> findByLogin(@Param("login") String login);

    @Query("SELECT u FROM UserModel u LEFT JOIN FETCH u.role WHERE " +
            "(:id IS NULL OR u.id = :id) AND " +
            "(:surname IS NULL OR u.surname LIKE %:surname%) AND " +
            "(:roleId IS NULL OR u.role.id = :roleId)")
    Page<UserModel> searchUsers(
            @Param("id") Long id,
            @Param("surname") String surname,
            @Param("roleId") Integer roleId,
            Pageable pageable);
}