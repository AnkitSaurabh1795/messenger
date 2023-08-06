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
public class UnreadMessageResponse implements Serializable {

    private static final long serialVersionUID = 151L;

    private String status;
    private String message;

    private List<Chat> chats;
}
