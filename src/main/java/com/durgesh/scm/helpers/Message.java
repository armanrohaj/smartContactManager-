package com.durgesh.scm.helpers;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    private String content;
    @Builder.Default
    private MessageType type= MessageType.blue;

}
