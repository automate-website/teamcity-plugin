package website.automate.teamcity.agent.support;

import jetbrains.buildServer.RunBuildException;

public class ExecutionInterruptionException extends RunBuildException {

    private static final long serialVersionUID = -6963320300323826533L;

    public ExecutionInterruptionException(String msg, Throwable e){
        super(msg, e);
    }
}
