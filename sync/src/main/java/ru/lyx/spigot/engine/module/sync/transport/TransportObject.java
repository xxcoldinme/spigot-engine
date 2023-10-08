package ru.lyx.spigot.engine.module.sync.transport;

import lombok.*;

import java.io.Serializable;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransportObject implements Serializable {

    private String queue;
    private TransportMessage message;
}
