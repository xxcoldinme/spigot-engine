package ru.lyx.spigot.engine.module.sync.transport.message;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TextMessage implements TransportMessage {

    private String message;
}
