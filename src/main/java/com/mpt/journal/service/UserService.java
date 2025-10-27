package com.mpt.journal.service;

import com.mpt.journal.model.UserModel;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface UserService {
    Page<UserModel> findAll(int page, int size);
    Page<UserModel> search(Long id, String surname, Integer roleId, int page, int size);
    UserModel save(UserModel user);
    void deleteById(Long id);
    Optional<UserModel> findById(Long id);
    Optional<UserModel> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean validatePassword(String rawPassword, String encodedPassword);
}