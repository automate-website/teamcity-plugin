package website.automate.plugins.teamcity.server;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;

public class AutomateWebsitePropertiesProcessor implements PropertiesProcessor {

    @Override
    public Collection<InvalidProperty> process(Map<String, String> properties) {
        return Collections.emptyList();
    }

}
