package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageInfoDto {
    private String messageId;
    private String fromUserId;

    private String toUserId;

    private String text;

    private boolean isRead;

    private Long createdAt;

    private Long updatedAt;
}
