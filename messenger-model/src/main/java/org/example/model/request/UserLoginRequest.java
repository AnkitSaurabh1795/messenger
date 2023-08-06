package org.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 101L;

    private String userName;

    private String passCode;
}
