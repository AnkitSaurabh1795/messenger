package org.example.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UnreadMessageResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 151L;

    private List<Chat> chats;
    @Builder(builderMethodName = "childBuilder")
    public UnreadMessageResponse(String status, String message, List<Chat> chats) {
        super(status, message);
        this.chats = chats;
    }
}
