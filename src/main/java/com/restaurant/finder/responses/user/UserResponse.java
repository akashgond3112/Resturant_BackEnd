package com.restaurant.finder.responses.user;

import com.restaurant.finder.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 26042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String userName;
    private String email;
    private String mobileNumber;
    private Gender gender;
}
