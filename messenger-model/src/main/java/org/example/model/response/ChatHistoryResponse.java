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
@SuperBuilder
public class ChatHistoryResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 153L;

    List<Text> texts;
}
