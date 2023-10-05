package ru.lyx.spigot.engine.core.settingconfig.model;

@FunctionalInterface
public interface SpigotConfigModel {

    void initModel(SettingConfigModelContext context);
}
