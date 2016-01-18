package website.automate.teamcity.agent.support;

import website.automate.teamcity.common.Constants;
import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;

public class AutomateWebsiteAgentBuildRunnerInfo implements AgentBuildRunnerInfo {

    private static final AutomateWebsiteAgentBuildRunnerInfo INSTANCE = new AutomateWebsiteAgentBuildRunnerInfo();
    
    public static AutomateWebsiteAgentBuildRunnerInfo getInstance() {
        return INSTANCE;
    }
    
    @Override
    public String getType() {
        return Constants.RUN_TYPE;
    }

    @Override
    public boolean canRun(BuildAgentConfiguration agentConfiguration) {
        // No restrictions known yet, since a simple URL connection to external server is required.
        return true;
    }

}
