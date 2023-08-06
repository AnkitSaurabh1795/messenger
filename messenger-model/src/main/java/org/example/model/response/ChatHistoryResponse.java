package org.example.model.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChatHistoryResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 153L;

    private List<Text> texts;

    @Builder(builderMethodName = "childBuilder")
    public ChatHistoryResponse(String status, String message, List<Text> texts) {
        super(status, message);
        this.texts = texts;
    }
}
