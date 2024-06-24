package com.rishabhsoft.service;

import com.rishabhsoft.dto.UserDetailsDto;
import com.rishabhsoft.model.User;
import com.rishabhsoft.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * It is responsible for handling the user service.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // For password encoding

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    /**
     * It is responsible for saving the user.
     * @param userDto The user details.
     */
    @Override
    public void saveUser(UserDetailsDto userDto) {
        log.debug("Saving user: {}", userDto);
        User user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password before saving
        userRepository.save(user);
    }

    /**
     * It is responsible for deleting the user.
     * @param userId The user ID.
     */
    @Override
    public void deleteUser(Long userId) {
        log.debug("Deleting user with ID: {}", userId);
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        userRepository.deleteById(existingUser.getId());
    }

    /**
     * It is responsible for finding the user by email.
     * @param email The email.
     * @return The user details.
     */
    @Override
    public UserDetailsDto findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        User user = userRepository.findByEmail(email);
        if (Objects.nonNull(user)) {
            return convertToDto(user);
        }
        return null;
    }

    /**
     * It is responsible for finding all the users.
     * @param pageable The pageable.
     * @return The users.
     */
    @Override
    public Page<UserDetailsDto> findAllUsers(Pageable pageable) {
        log.debug("Finding all users");
        pageable = validatePageable(pageable);
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::convertToDto);
    }

    /**
     * It is responsible for validating the pageable.
     * @param pageable The pageable.
     * @return The validated pageable.
     */
    private static Pageable validatePageable(Pageable pageable) {
        log.debug("Validating pageable: {}", pageable);
        if (Objects.isNull(pageable)) {
            pageable = PageRequest.of(0, 10); // Default to page 0 with 10 items per page
        }
        if (pageable.getPageNumber() < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        return pageable;
    }

    /**
     * It is responsible for converting the user to DTO.
     * @param user The user.
     * @return The user DTO.
     */
    private UserDetailsDto convertToDto(User user) {
        return UserDetailsDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    /**
     * It is responsible for converting the DTO to user.
     * @param userDto The user DTO.
     * @return The user.
     */
    private User convertToEntity(UserDetailsDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

}
