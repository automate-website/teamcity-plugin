package website.automate.plugins.teamcity.agent;

import website.automate.plugins.teamcity.agent.support.AutomateWebsiteAgentBuildRunnerInfo;
import website.automate.plugins.teamcity.agent.support.BuildProcessConfig;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentBuildRunner;
import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildProcess;
import jetbrains.buildServer.agent.BuildRunnerContext;

public class AutomateWebsiteAgentBuildRunner implements AgentBuildRunner {

    @Override
    public BuildProcess createBuildProcess(AgentRunningBuild agentRunningBuild,
            BuildRunnerContext buildRunnerContext) throws RunBuildException {
        return new AutomateWebsiteBuildProcess(BuildProcessConfig.of(buildRunnerContext.getRunnerParameters()),
                agentRunningBuild.getBuildLogger());
    }

    @Override
    public AgentBuildRunnerInfo getRunnerInfo() {
        return AutomateWebsiteAgentBuildRunnerInfo.getInstance();
    }

}
