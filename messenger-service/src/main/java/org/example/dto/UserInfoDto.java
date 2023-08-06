package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {

    private String userId;

    private String userName;

    private String password;

    private String role;

    private String token;

    private Long createdAt;

    private Long updatedAt;
}
