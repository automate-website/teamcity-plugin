package website.automate.teamcity.agent.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContextParameterResolver {

    private static final String CONTEXT_PARAMETER_PREFIX = "website.automate.context.";

    private static final ContextParameterResolver INSTANCE = new ContextParameterResolver();
    
    public static ContextParameterResolver getInstance(){
        return INSTANCE;
    }
    
    public Map<String, String> resolve(Map<String, String> runnerParameters){
        Map<String, String> contextParameters = new HashMap<String, String>();
        Set<String> parameterNames = runnerParameters.keySet();
        for(String parameterName : parameterNames){
            if(parameterName.startsWith(CONTEXT_PARAMETER_PREFIX)){
                contextParameters.put(parameterName.substring(CONTEXT_PARAMETER_PREFIX.length()), runnerParameters.get(parameterName));
            }
        }
        return contextParameters;
    }
}
