package com.rishabhsoft.service;

import com.rishabhsoft.dto.UserDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * It is responsible for handling the user service.
 */
public interface UserService {

    UserDetailsDto findByEmail(String email);
    void saveUser(UserDetailsDto user);
    void deleteUser(Long id);
    Page<UserDetailsDto> findAllUsers(Pageable pageable);
}
