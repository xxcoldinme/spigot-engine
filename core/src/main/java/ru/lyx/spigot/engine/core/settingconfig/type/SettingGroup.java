package ru.lyx.spigot.engine.core.settingconfig.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class SettingGroup {

    private final String key;
    private final SettingProperty[] properties;
}
