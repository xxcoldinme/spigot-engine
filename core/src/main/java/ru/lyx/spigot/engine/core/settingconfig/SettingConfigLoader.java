package ru.lyx.spigot.engine.core.settingconfig;

import org.ini4j.Ini;
import org.ini4j.Profile;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.exception.SpigotEngineException;
import ru.lyx.spigot.engine.core.settingconfig.model.SettingGroup;
import ru.lyx.spigot.engine.core.settingconfig.model.SettingProperty;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SettingConfigLoader {

    public SettingConfig loadAbsolute(@NotNull File file) {
        try {
            return load(new FileInputStream(file));
        }
        catch (FileNotFoundException exception) {
            throw new SpigotEngineException(exception);
        }
    }

    public SettingConfig load(@NotNull InputStream inputStream) {
        Set<SettingGroup> properties = loadGroups(inputStream);
        return new SettingConfig(properties);
    }

    private Set<SettingGroup> loadGroups(InputStream inputStream) {
        try {
            final Ini ini = new Ini(inputStream);
            return ini.keySet()
                    .stream()
                    .map(groupName -> new SettingGroup(groupName, loadProperties(ini, groupName)))
                    .collect(Collectors.toSet());
        }
        catch (IOException exception) {
            throw new SpigotEngineException(exception);
        }
    }

    private SettingProperty[] loadProperties(Ini ini, String groupName) {
        List<Profile.Section> sectionList = ini.getAll(groupName);
        return sectionList.stream()
                .flatMap(section -> Stream.of(section.childrenNames()))
                .map(propertyName -> new SettingProperty(propertyName, ini.get(groupName, propertyName)))
                .toArray(SettingProperty[]::new);
    }
}
