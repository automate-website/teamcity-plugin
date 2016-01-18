package website.automate.teamcity.server.global;

import static website.automate.teamcity.server.global.GlobalConfigRequest.GlobalConfigRequestType.ADD;
import static website.automate.teamcity.server.global.GlobalConfigRequest.GlobalConfigRequestType.DELETE;
import static website.automate.teamcity.server.global.GlobalConfigRequest.GlobalConfigRequestType.EDIT;
import static website.automate.teamcity.server.global.GlobalConfigRequest.GlobalConfigRequestType.SYNC;

import java.util.EnumSet;

import jetbrains.buildServer.controllers.ActionErrors;

import org.apache.commons.lang.StringUtils;

import website.automate.teamcity.server.global.GlobalConfigRequest.GlobalConfigRequestType;

public class GlobalConfigRequestValidator {

    private static final GlobalConfigRequestValidator INSTANCE = new GlobalConfigRequestValidator();
    
    private static final EnumSet<GlobalConfigRequestType> 
        IDENTIFYABLE_TYPES = EnumSet.of(EDIT, DELETE, SYNC),
        BUSINESS_DATA_TYPES = EnumSet.of(EDIT, ADD);
    
    public static GlobalConfigRequestValidator getInstance(){
        return INSTANCE;
    }
    
    public ActionErrors validate(final GlobalConfigRequest request) {
        GlobalConfigRequestType type = request.getType();
        ActionErrors errors = new ActionErrors();
        
        if(BUSINESS_DATA_TYPES.contains(type)){
            String username = request.getUsername();
            String password = request.getPassword();
            
            if (StringUtils.isBlank(username)) {
                errors.addError("errorUsername", "Username can't be empty.");
            }
    
            if (StringUtils.isBlank(password)) {
                errors.addError("errorPassword", "Password can't be empty.");
            }
        }
        
        if(IDENTIFYABLE_TYPES.contains(type)) {
            String id = request.getId();
            if (StringUtils.isBlank(id)) {
                errors.addError("errorId", "Identifier can't be empty.");
            }
        }
        
        return errors;
    }
}
