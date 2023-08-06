package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 2L;

    private String userId;

    private String userName;

    private String password;

    private String role;

    private String token;

    private Long createdAt;

    private Long updatedAt;
}
