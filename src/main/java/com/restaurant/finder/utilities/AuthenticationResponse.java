package com.restaurant.finder.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 22042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
