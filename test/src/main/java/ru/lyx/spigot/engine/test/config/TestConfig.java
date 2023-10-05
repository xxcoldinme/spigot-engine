package ru.lyx.spigot.engine.test.config;

import ru.lyx.spigot.engine.core.settingconfig.SettingConfig;
import ru.lyx.spigot.engine.core.settingconfig.SettingConfigLoader;

@SuppressWarnings("DataFlowIssue")
public class TestConfig {

    private static final TestConfigModel TEST_CONFIG_MODEL = new TestConfigModel();
    private static final SettingConfigLoader LOADER = new SettingConfigLoader();

    public static void main(String[] args) {
        SettingConfig config = LOADER.load(TestConfig.class.getResourceAsStream("/test_config.ini"));

        config.mergeModel(TEST_CONFIG_MODEL);

        System.out.println(TEST_CONFIG_MODEL.getProperty(TestConfigModel.STRING_PARAM_PATH).getValue());
        System.out.println(TEST_CONFIG_MODEL.getProperty(TestConfigModel.INT_PARAM_PATH).getValueAsInt());
        System.out.println(TEST_CONFIG_MODEL.getProperty(TestConfigModel.BOOL_PARAM_PATH).getValueAsBoolean());
        System.out.println(TEST_CONFIG_MODEL.getProperty(TestConfigModel.LONG_PARAM_PATH).getValueAsLong());
    }
}
