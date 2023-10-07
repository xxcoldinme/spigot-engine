package ru.lyx.spigot.engine.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.net.InetSocketAddress;

@Getter
@ToString
@RequiredArgsConstructor
public class SpigotServerInfo {

    private final String name;
    private final String motd;
    private final String bukkitVersion;

    private final InetSocketAddress address;

    private final int maxOnlineSize;

    private final long timestamp;
}
