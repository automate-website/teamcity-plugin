/*
 * Copyright (C) 2010 JFrog Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package website.automate.plugins.teamcity.server.global;

import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.util.EventDispatcher;

import org.jetbrains.annotations.NotNull;

import website.automate.plugins.teamcity.common.Constants;

public class AutomateWebsiteServerListener extends BuildServerAdapter {

    private SBuildServer server;
    private ServerConfigPersistenceManager configPersistenceManager;

    public AutomateWebsiteServerListener(@NotNull final EventDispatcher<BuildServerListener> dispatcher,
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