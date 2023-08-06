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
public class SendMessageRequest implements Serializable {
    private static final long serialVersionUID = 103L;

    private String from;
    private String to;

    private String text;
}
