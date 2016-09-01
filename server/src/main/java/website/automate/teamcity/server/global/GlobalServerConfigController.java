package website.automate.teamcity.server.global;

import static java.text.MessageFormat.format;

import java.util.List;

import jetbrains.buildServer.controllers.ActionErrors;
import jetbrains.buildServer.controllers.BaseFormXmlController;
import jetbrains.buildServer.log.Loggers;

import org.jdom.Element;
import org.springframework.web.servlet.ModelAndView;

import website.automate.manager.api.client.ProjectRetrievalRemoteService;
import website.automate.manager.api.client.model.Project;
import website.automate.teamcity.server.global.GlobalConfigRequest.GlobalConfigRequestType;
import website.automate.teamcity.server.io.mapper.AccountMapper;
import website.automate.teamcity.server.io.mapper.ProjectMapper;
import website.automate.teamcity.server.io.model.AccountSerializable;
import website.automate.teamcity.server.io.model.ProjectSerializable;

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
                getOrCreateMessages(request).addMessage("objectUpdated", format("Account entry {0} was updated.", username));
                
            } else if(type == GlobalConfigRequestType.ADD){
                String username = globalConfigRequest.getUsername();
                String password = globalConfigRequest.getPassword();
                
                AccountSerializable accountSerializable = configPersistenceManager.createAccount(username, password);
                syncAccount(accountSerializable.getId());
                getOrCreateMessages(request).addMessage("objectCreated", format("Account entry {0} was created.", username));
                
            } else if(type == GlobalConfigRequestType.DELETE){
                
                String id = globalConfigRequest.getId();
                AccountSerializable accountSerializable = configPersistenceManager.getAccount(id);
                configPersistenceManager.deleteAccount(id);
                getOrCreateMessages(request).addMessage("objectDeleted", format("Account entry {0} was deleted", accountSerializable.getUsername()));
                
            } else if(type == GlobalConfigRequestType.SYNC){
                
                String id = globalConfigRequest.getId();
                AccountSerializable accountSerializable = configPersistenceManager.getAccount(id);
                syncAccount(id);
                getOrCreateMessages(request).addMessage("objectSynced", format("Account entry {0} was synced.", accountSerializable.getUsername()));
            }
            
            configPersistenceManager.saveConfiguration();
        } catch (Exception e){
            handleException(errors, e);
        }
    }
    
    private void syncAccount(String id){
        AccountSerializable accountSerializable = configPersistenceManager.getAccount(id);
        
        List<Project> projects = projectRetrievalService.getProjectsWithExecutableScenariosByPrincipal(accountMapper.map(accountSerializable));
        List<ProjectSerializable> projectsSerializable = projectMapper.safeMapCollection(projects);
        
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
        errors.addError("objectError", errorMessage);
        Loggers.SERVER.error("GlobalConfigRequest processing failed.", e);
    }
}