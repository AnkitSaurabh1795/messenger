package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String messageId;
    private String fromUserId;

    private String toUserId;

    private String text;

    private boolean isRead;

    private Long createdAt;

    private Long updatedAt;


}
