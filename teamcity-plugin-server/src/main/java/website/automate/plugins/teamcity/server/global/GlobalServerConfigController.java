package website.automate.plugins.teamcity.server.global;

import jetbrains.buildServer.controllers.ActionErrors;
import jetbrains.buildServer.controllers.BaseFormXmlController;
import jetbrains.buildServer.log.Loggers;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalServerConfigController extends BaseFormXmlController {

    private static final String 
        PARAM_ID = "id",
        PARAM_USERNAME = "username",
        PARAM_PASSWORD = "password",
        PARAM_DELETE_OBJECT = "deleteObject",
        PARAM_EDIT_MODE = "editMode",
        PARAM_VALUE_EDIT = "edit",
        PARAM_VALUE_ADD = "add",
        PARAM_VALUE_SYNC = "sync";
    
    private ServerConfigPersistenceManager configPersistenceManager;

    public GlobalServerConfigController(final ServerConfigPersistenceManager configPersistenceManager) {
        this.configPersistenceManager = configPersistenceManager;
    }

    @Override
    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, Element xmlResponse) {
        boolean isEditMode = isEditMode(request);
        boolean isAddMode = isAddMode(request);
        boolean isDeleteMode = isDeleteMode(request);

        if (isEditMode || isAddMode) {
            ActionErrors errors = validate(request);
            if (errors.hasErrors()) {
                errors.serialize(xmlResponse);
                return;
            }
        }

        if (isSyncRequest(request)) {
            ActionErrors errors = testConnection(request);
            if (errors.hasErrors()) {
                errors.serialize(xmlResponse);
            }
            return;
        }
        
        String id = isDeleteMode ? request.getParameter(PARAM_DELETE_OBJECT) : request.getParameter(PARAM_ID);
        String username = request.getParameter(PARAM_USERNAME);
        String password = request.getParameter(PARAM_PASSWORD);

        if (isEditMode) {
            configPersistenceManager.updateAccount(id, username, password);
            getOrCreateMessages(request).addMessage("objectUpdated", "Automate Website account configuration was updated.");
        } else if(isAddMode){
            configPersistenceManager.createAccount(username, password);
            getOrCreateMessages(request).addMessage("objectCreated", "Automate Website account configuration was created.");
        } else if(isDeleteMode){
            configPersistenceManager.deleteAccount(id);
            getOrCreateMessages(request).addMessage("objectDeleted", "Automate Website server configuration was deleted.");
        }
        
        configPersistenceManager.saveConfiguration();
    }

    private ActionErrors validate(final HttpServletRequest request) {
        String username = request.getParameter(PARAM_USERNAME);
        String password = request.getParameter(PARAM_PASSWORD);

        ActionErrors errors = new ActionErrors();
        if (StringUtils.isBlank(username)) {
            errors.addError("errorUsername", "Username can't be empty.");
        }

        if (StringUtils.isBlank(password)) {
            errors.addError("errorPassword", "Password can't be empty.");
        }
        
        return errors;
    }

    private ActionErrors testConnection(final HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        return errors;
    }

    private boolean isDeleteMode(final HttpServletRequest req) {
        return req.getParameter(PARAM_DELETE_OBJECT) != null;
    }

    private boolean isEditMode(final HttpServletRequest req) {
        return PARAM_VALUE_EDIT.equals(req.getParameter(PARAM_EDIT_MODE));
    }

    private boolean isAddMode(final HttpServletRequest req) {
        return PARAM_VALUE_ADD.equals(req.getParameter(PARAM_EDIT_MODE));
    }

    private boolean isSyncRequest(final HttpServletRequest req) {
        String sync = req.getParameter(PARAM_VALUE_SYNC);
        return StringUtils.isNotBlank(sync) && Boolean.valueOf(sync);
    }

    private void handleSyncException(ActionErrors errors, Exception e) {
        Throwable throwable = e.getCause();
        String errorMessage;
        if (throwable != null) {
            errorMessage = e.getMessage() + " (" + throwable.getClass().getCanonicalName() + ")";
        } else {
            errorMessage = e.getClass().getCanonicalName() + ": " + e.getMessage();
        }
        errors.addError("errorConnection", errorMessage);
        Loggers.SERVER.error("Error while performing the Automate Website account sync.", e);
    }
}