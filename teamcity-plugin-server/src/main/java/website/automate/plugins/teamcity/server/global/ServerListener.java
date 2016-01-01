package website.automate.plugins.teamcity.server.global;

import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.util.EventDispatcher;

import org.jetbrains.annotations.NotNull;

import website.automate.plugins.teamcity.common.Constants;

public class ServerListener extends BuildServerAdapter {

    private SBuildServer server;
    private ServerConfigPersistenceManager configPersistenceManager;

    public ServerListener(@NotNull final EventDispatcher<BuildServerListener> dispatcher,
            @NotNull final SBuildServer server, @NotNull ServerPaths serverPaths) {
        this.server = server;

        dispatcher.addListener(this);

        configPersistenceManager = new ServerConfigPersistenceManager(serverPaths);
    }

    @Override
    public void serverStartup() {
        Loggers.SERVER.info("Plugin '" + Constants.DISPLAY_NAME + "' is running on server version " +
                server.getFullServerVersion()
                + ".");
    }

    public ServerConfigPersistenceManager getConfigModel() {
        return configPersistenceManager;
    }
}