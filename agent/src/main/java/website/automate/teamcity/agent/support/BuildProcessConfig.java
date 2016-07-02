package website.automate.teamcity.agent.support;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

import website.automate.teamcity.common.Constants;

public class BuildProcessConfig {

    private static final long DEFAULT_EXECUTION_TIMEOUT_IN_SEC = 300;

    private static final long DEFAULT_JOB_STATUS_CHECK_INTERVAL_IN_SEC = 30;
    
    private long executionTimeoutSec;
    
    private long jobStatusCheckIntervalSec;
    
    private String username;
    
    private String password;
    
    private List<String> scenarioIds;
    
    private Map<String, String> context;
    
    public static BuildProcessConfig of(Map<String, String> runnerParameters,
            Map<String, String> configParameters){
        String username = runnerParameters.get(Constants.PROPERTY_NAME_ACCOUNT_USERNAME);
        String password = runnerParameters.get(Constants.PROPERTY_NAME_ACCOUNT_PASSWORD);
        String scenarioIdsStr = runnerParameters.get(Constants.PROPERTY_NAME_SCENARIO_IDS);
        long executionTimeoutSec = getParamValueAsLong(configParameters, Constants.PROPERTY_NAME_EXECUTION_TIMEOUT_SEC, DEFAULT_EXECUTION_TIMEOUT_IN_SEC);
        long jobStatusCheckIntervalSec = getParamValueAsLong(configParameters, Constants.PROPERTY_NAME_JOB_STATUS_CHECK_INTERVAL_SEC, DEFAULT_JOB_STATUS_CHECK_INTERVAL_IN_SEC);
        Map<String, String> context = ContextParameterResolver.getInstance().resolve(configParameters);
        
        List<String> scenarioIds = asList(scenarioIdsStr.split(","));
        
        return new BuildProcessConfig(username, password, 
                scenarioIds, context,
                executionTimeoutSec, jobStatusCheckIntervalSec);
    }
    
    private static long getParamValueAsLong(Map<String, String> configParameters, String paramName, long defaultValue){
        return Long.parseLong(getOrDefault(configParameters, paramName, Long.toString(defaultValue)));
    }
    
    private static String getOrDefault(Map<String, String> map, String key, String defaultValue){
        String value = map.get(key);
        if(value == null){
            value = defaultValue;
        }
        return value;
    }
    
    private BuildProcessConfig(String username, String password,
            List<String> scenarioIds, Map<String, String> context,
            long executionTimeoutSec, long jobStatusCheckIntervalSec) {
        super();
        this.username = username;
        this.password = password;
        this.scenarioIds = scenarioIds;
        this.context = context;
        this.executionTimeoutSec = executionTimeoutSec;
        this.jobStatusCheckIntervalSec = jobStatusCheckIntervalSec;
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

    public Map<String, String> getContext() {
        return context;
    }
    public long getExecutionTimeoutSec() {
        return executionTimeoutSec;
    }
    
    public long getJobStatusCheckIntervalSec() {
        return jobStatusCheckIntervalSec;
    }
}
