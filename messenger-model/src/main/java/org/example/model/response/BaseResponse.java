package org.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 154L;

    private String status;

    private String message;

    @SuperBuilder
    public static class BaseResponseBuilder {
    }
}
