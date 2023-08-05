package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
