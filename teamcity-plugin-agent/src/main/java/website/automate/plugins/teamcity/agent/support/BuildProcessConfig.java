package website.automate.plugins.teamcity.agent.support;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

import website.automate.plugins.teamcity.common.Constants;

public class BuildProcessConfig {

    private String username;
    
    private String password;
    
    private List<String> scenarioIds;
    
    public static BuildProcessConfig of(Map<String, String> runnerParameters){
        String username = runnerParameters.get(Constants.PROPERTY_NAME_ACCOUNT_USERNAME);
        String password = runnerParameters.get(Constants.PROPERTY_NAME_ACCOUNT_PASSWORD);
        String scenarioIdsStr = runnerParameters.get(Constants.PROPERTY_NAME_SCENARIO_IDS);
        
        List<String> scenarioIds = asList(scenarioIdsStr.split(","));
        
        return new BuildProcessConfig(username, password, scenarioIds);
    }
    
    private BuildProcessConfig(String username, String password,
            List<String> scenarioIds) {
        super();
        this.username = username;
        this.password = password;
        this.scenarioIds = scenarioIds;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getScenarioIds() {
        return scenarioIds;
    }
} 
