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
public class ChatHistoryRequest implements Serializable {
    private static final long serialVersionUID = 104L;

    private String friend;

    private String user;
}
