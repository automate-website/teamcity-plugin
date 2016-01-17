package website.automate.plugins.teamcity.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.util.StringUtils;

import website.automate.plugins.teamcity.common.Constants;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;

public class AutomateWebsitePropertiesProcessor implements PropertiesProcessor {

    @Override
    public Collection<InvalidProperty> process(Map<String, String> properties) {
        Collection<InvalidProperty> invalidProperties = new ArrayList<InvalidProperty>();
        validateProperty(Constants.PROPERTY_NAME_ACCOUNT, "Account must be set.", properties, invalidProperties);
        validateProperty(Constants.PROPERTY_NAME_PROJECT, "Project must be set.", properties, invalidProperties);
        validateProperty(Constants.PROJECT_NAME_SCENARIOS, "Scenarios must be set.", properties, invalidProperties);
        return invalidProperties;
    }

    private void validateProperty(String key, String reason, Map<String, String> properties, Collection<InvalidProperty> invalidProperties){
        String value = properties.get(key);
        if(StringUtils.isEmpty(value)){
            invalidProperties.add(new InvalidProperty(key, reason));
        }
    }
}
