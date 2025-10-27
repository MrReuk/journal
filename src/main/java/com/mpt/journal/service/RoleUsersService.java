package com.mpt.journal.service;

import com.mpt.journal.model.RoleUsersModel;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface RoleUsersService {
    Page<RoleUsersModel> findAll(int page, int size);
    Page<RoleUsersModel> search(Integer id, String name, int page, int size);
    RoleUsersModel save(RoleUsersModel role);
    void deleteById(Integer id);
    Optional<RoleUsersModel> findById(Integer id);
}