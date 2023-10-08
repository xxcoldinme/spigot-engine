package ru.lyx.spigot.engine.core.settingconfig.model;

/**
 * The implementations of this interface are configuration
 * data models and their container within a single module.
 */
public interface SpigotConfigModel {

    /**
     * Initialization configuration data-model.
     *
     * @param context Configuration model context.
     */
    void initModel(SettingConfigModelContext context);
}
