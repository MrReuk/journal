package com.mpt.journal.service;

import com.mpt.journal.model.UserModel;
import com.mpt.journal.model.RoleUsersModel;
import com.mpt.journal.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleUsersService roleService;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, RoleUsersService roleService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserModel> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserModel> search(Long id, String surname, Integer roleId, int page, int size) {
        return repository.searchUsers(
                id != null && id > 0 ? id : null,
                surname != null && !surname.isEmpty() ? surname : null,
                roleId != null && roleId > 0 ? roleId : null,
                PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE)
        );
    }

    @Override
    @Transactional
    public UserModel save(UserModel user) {
        if (user.getRole() != null && user.getRole().getId() != null) {
            Optional<RoleUsersModel> role = roleService.findById(user.getRole().getId());
            role.ifPresent(user::setRole);
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            if (!user.getPassword().startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        return repository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserModel> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserModel> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLogin(String login) {
        return repository.findByLogin(login).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}