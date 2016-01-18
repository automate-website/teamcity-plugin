package website.automate.plugins.teamcity.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import website.automate.plugins.teamcity.common.Constants;
import website.automate.plugins.teamcity.server.global.ServerConfigPersistenceManager;
import website.automate.plugins.teamcity.server.global.ServerListener;
import website.automate.plugins.teamcity.server.io.model.AccountSerializable;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;

public class AutomateWebsitePropertiesProcessor implements PropertiesProcessor {

    private ServerConfigPersistenceManager configPersistenceManager;
    
    public AutomateWebsitePropertiesProcessor(@NotNull ServerListener serverListener){
        this.configPersistenceManager = serverListener.getConfigModel();
    }
    
    @Override
    public Collection<InvalidProperty> process(Map<String, String> properties) {
        
        Collection<InvalidProperty> invalidProperties = new ArrayList<InvalidProperty>();
        validateProperty(Constants.PROPERTY_NAME_ACCOUNT_ID, "Account id must be set.", properties, invalidProperties);
        validateProperty(Constants.PROPERTY_NAME_PROJECT_ID, "Project id must be set.", properties, invalidProperties);
        validateProperty(Constants.PROPERTY_NAME_SCENARIO_IDS, "Scenario id's must be set.", properties, invalidProperties);
        
        if(invalidProperties.isEmpty()){
            String accountId = properties.get(Constants.PROPERTY_NAME_ACCOUNT_ID);
            AccountSerializable account = configPersistenceManager.getAccount(accountId);
            properties.put(Constants.PROPERTY_NAME_ACCOUNT_USERNAME, account.getUsername());
            properties.put(Constants.PROPERTY_NAME_ACCOUNT_PASSWORD, account.getPassword());
        }
        
        return invalidProperties;
    }

    private void validateProperty(String key, String reason, Map<String, String> properties, Collection<InvalidProperty> invalidProperties){
        String value = properties.get(key);
        if(StringUtils.isEmpty(value)){
            invalidProperties.add(new InvalidProperty(key, reason));
        }
    }
}
