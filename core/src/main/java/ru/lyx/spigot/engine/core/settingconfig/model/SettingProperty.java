package ru.lyx.spigot.engine.core.settingconfig.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class SettingProperty {

    private final String key;
    private final String value;

    public boolean getValueAsBoolean() {
        return Boolean.parseBoolean(value);
    }

    public int getValueAsInt() {
        return Integer.parseInt(value);
    }

    public long getValueAsLong() {
        return Long.parseLong(value);
    }
}
