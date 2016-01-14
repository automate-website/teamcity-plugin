package website.automate.plugins.teamcity.server.global;

import java.util.List;

import jetbrains.buildServer.controllers.ActionErrors;
import jetbrains.buildServer.controllers.BaseFormXmlController;
import jetbrains.buildServer.log.Loggers;

import org.jdom.Element;
import org.springframework.web.servlet.ModelAndView;

import website.automate.manager.api.client.ProjectRetrievalRemoteService;
import website.automate.manager.api.client.model.Project;
import website.automate.plugins.teamcity.server.global.GlobalConfigRequest.GlobalConfigRequestType;
import website.automate.plugins.teamcity.server.mapper.AccountMapper;
import website.automate.plugins.teamcity.server.mapper.ProjectMapper;
import website.automate.plugins.teamcity.server.model.AccountSerializable;
import website.automate.plugins.teamcity.server.model.ProjectSerializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalServerConfigController extends BaseFormXmlController {
    
    private ProjectMapper projectMapper = ProjectMapper.getInstance();
    
    private AccountMapper accountMapper = AccountMapper.getInstance();
    
    private GlobalConfigRequestValidator requestValidator = GlobalConfigRequestValidator.getInstance();
    
    private ProjectRetrievalRemoteService projectRetrievalService = ProjectRetrievalRemoteService.getInstance();
    
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
        GlobalConfigRequest globalConfigRequest = new GlobalConfigRequest(request);
        
        ActionErrors errors = requestValidator.validate(globalConfigRequest);
        if (errors.hasErrors()) {
            errors.serialize(xmlResponse);
            return;
        }
        
        GlobalConfigRequestType type = globalConfigRequest.getType();
        
        try {
            if (type == GlobalConfigRequestType.EDIT) {
                String username = globalConfigRequest.getUsername();
                String password = globalConfigRequest.getPassword();
                String id = globalConfigRequest.getId();
                
                configPersistenceManager.updateAccount(id, username, password);
                syncAccount(id);
                getOrCreateMessages(request).addMessage("objectUpdated", "Automate Website account configuration was updated.");
                
            } else if(type == GlobalConfigRequestType.ADD){
                String username = globalConfigRequest.getUsername();
                String password = globalConfigRequest.getPassword();
                
                AccountSerializable accountSerializable = configPersistenceManager.createAccount(username, password);
                syncAccount(accountSerializable.getId());
                getOrCreateMessages(request).addMessage("objectCreated", "Automate Website account configuration was created.");
                
            } else if(type == GlobalConfigRequestType.DELETE){
                
                String id = globalConfigRequest.getId();
                configPersistenceManager.deleteAccount(id);
                getOrCreateMessages(request).addMessage("objectDeleted", "Automate Website account configuration was deleted.");
                
            } else if(type == GlobalConfigRequestType.SYNC){
                
                String id = globalConfigRequest.getId();
                syncAccount(id);
                getOrCreateMessages(request).addMessage("objectSynced", "Automate Website account was synced.");
            }
            
            configPersistenceManager.saveConfiguration();
        } catch (Exception e){
            handleException(errors, e);
        }
    }
    
    private void syncAccount(String id){
        AccountSerializable accountSerializable = configPersistenceManager.getAccount(id);
        
        List<Project> projects = projectRetrievalService.getProjectsWithScenariosByPrincipal(accountMapper.map(accountSerializable));
        List<ProjectSerializable> projectsSerializable = projectMapper.safeMapList(projects);
        
        accountSerializable.setProjects(projectsSerializable);
        
        configPersistenceManager.saveConfiguration();
    }
    
    private void handleException(ActionErrors errors, Exception e) {
        Throwable throwable = e.getCause();
        String errorMessage;
        if (throwable != null) {
            errorMessage = e.getMessage() + " (" + throwable.getClass().getCanonicalName() + ")";
        } else {
            errorMessage = e.getClass().getCanonicalName() + ": " + e.getMessage();
        }
        errors.addError("errorGlobal", errorMessage);
        Loggers.SERVER.error("GlobalConfigRequest processing failed.", e);
    }
}