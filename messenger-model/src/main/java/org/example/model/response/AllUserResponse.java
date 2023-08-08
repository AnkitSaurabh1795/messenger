package org.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllUserResponse implements Serializable {
    private static final long serialVersionUID = 156L;

    private String status;
    private List<String> data;
}
