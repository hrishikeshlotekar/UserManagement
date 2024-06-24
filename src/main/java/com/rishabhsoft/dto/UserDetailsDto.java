package com.rishabhsoft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * It is responsible for storing the user details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto  {

    private Long id;
    private String name;
    private String email;
    private String password;
}
