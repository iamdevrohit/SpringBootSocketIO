package com.abloclone.abloclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {

    private MessageType type;
    private String message;
    private String room;

    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }
}

