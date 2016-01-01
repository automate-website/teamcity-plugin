
package website.automate.plugins.teamcity.server.global;

import jetbrains.buildServer.serverSide.auth.AuthUtil;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.serverSide.auth.SecurityContext;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import jetbrains.buildServer.web.openapi.SimpleCustomTab;
import jetbrains.buildServer.web.openapi.WebControllerManager;

import org.jetbrains.annotations.NotNull;


import javax.servlet.http.HttpServletRequest;

import java.util.Map;

public class GlobalServerConfigTab extends SimpleCustomTab {

    private static final String TAB_ID = "automate-website";
    
    private ServerConfigPersistenceManager configPersistenceManager;
    private SecurityContext securityContext;

    public GlobalServerConfigTab(final @NotNull WebControllerManager controllerManager,
                                            final @NotNull SecurityContext securityContext,
                                            final @NotNull ServerListener serverListener) {
        super(controllerManager, PlaceId.ADMIN_SERVER_CONFIGURATION_TAB, TAB_ID,
                "globalServerConfigTab.jsp",
                "Automate Website");
        this.securityContext = securityContext;
        this.configPersistenceManager = serverListener.getConfigModel();

        setPosition(PositionConstraint.after("serverConfigGeneral"));
        register();

        controllerManager.registerController("/admin/automate-website/globalServerConfigTab.html",
                new GlobalServerConfigController(configPersistenceManager));
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
        super.fillModel(model, request);
        model.put("serverConfigPersistenceManager", configPersistenceManager);
    }

    @Override
    public boolean isVisible() {
        return super.isVisible() && userHasPermission();
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        return super.isAvailable(request) && userHasPermission();
    }

    private boolean userHasPermission() {
        return AuthUtil.hasGlobalPermission(securityContext.getAuthorityHolder(), Permission.CHANGE_SERVER_SETTINGS);
    }
}