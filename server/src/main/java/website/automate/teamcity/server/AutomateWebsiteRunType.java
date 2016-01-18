package website.automate.teamcity.server;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import website.automate.teamcity.common.Constants;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;

public class AutomateWebsiteRunType extends RunType {

    private final PluginDescriptor descriptor;
    
    private final AutomateWebsitePropertiesProcessor propetriesProcessor;
    
    public AutomateWebsiteRunType(@NotNull final RunTypeRegistry registry,
            @NotNull final PluginDescriptor descriptor, 
            @NotNull AutomateWebsitePropertiesProcessor propetriesProcessor) {
        this.descriptor = descriptor;
        this.propetriesProcessor = propetriesProcessor;
        registry.registerRunType(this);
    }
    
    @Override
    public String getDescription() {
        return Constants.DESCRIPTION;
    }

    @Override
    public String getDisplayName() {
        return Constants.DISPLAY_NAME;
    }

    @Override
    public String getType() {
        return Constants.RUN_TYPE;
    }

    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        return new HashMap<String, String>();
    }

    @Override
    public String getEditRunnerParamsJspFilePath() {
        return descriptor.getPluginResourcesPath() + "editParams.jsp";
    }

    @Override
    public String getViewRunnerParamsJspFilePath() {
        return descriptor.getPluginResourcesPath() + "viewParams.jsp";
    }
    
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
        return propetriesProcessor;
    }
}
