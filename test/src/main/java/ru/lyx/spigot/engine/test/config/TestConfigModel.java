package ru.lyx.spigot.engine.test.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigMetadataModel;

@Getter
@Setter(AccessLevel.PRIVATE)
public class TestConfigModel extends SpigotConfigMetadataModel {

    public static final String STRING_PARAM_PATH = "ParamsToPrint.stringParam";
    public static final String INT_PARAM_PATH = "ParamsToPrint.intParam";
    public static final String BOOL_PARAM_PATH = "ParamsToPrint.boolParam";
    public static final String LONG_PARAM_PATH = "ParamsToPrint.longParam";

    @Override
    protected AttachmentContainer<String> ofPaths() {
        return AttachmentContainer.of(STRING_PARAM_PATH, INT_PARAM_PATH,
                BOOL_PARAM_PATH, LONG_PARAM_PATH);
    }
}
