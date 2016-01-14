package website.automate.plugins.teamcity.server.global;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Element;

import jetbrains.buildServer.serverSide.crypt.RSACipher;

public class GlobalConfigRequest { 

    private static final String 
        PARAM_ID = "id",
        PARAM_USERNAME = "username",
        PARAM_ENCRYPTED_PASSWORD = "encryptedPassword",
        PARAM_MODE_VALUE_DELETE = "delete",
        PARAM_MODE = "editMode",
        PARAM_MODE_VALUE_EDIT = "edit",
        PARAM_MODE_VALUE_ADD = "add",
        PARAM_MODE_VALUE_SYNC = "sync";
    
    public enum GlobalConfigRequestType {
        ADD,
        EDIT,
        DELETE,
        SYNC,
        UNKNOWN
    }
    
    private GlobalConfigRequestType type;
    
    private String username;
    
    private String password;
    
    private String id;
    
    public GlobalConfigRequestType getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
    
    public GlobalConfigRequest(final HttpServletRequest request){
        this.type = getType(request);
        this.id = request.getParameter(PARAM_ID);
        this.username = request.getParameter(PARAM_USERNAME);
        this.password = getPassword(request);
    }
    
    private String getPassword(final HttpServletRequest request){
        String encryptedPassword = request.getParameter(PARAM_ENCRYPTED_PASSWORD);
        if(encryptedPassword == null){
            return encryptedPassword;
        }
        return RSACipher.decryptWebRequestData(encryptedPassword);
    }
    
    private GlobalConfigRequestType getType(final HttpServletRequest request){
        if(isDeleteMode(request)){
            return GlobalConfigRequestType.DELETE;
        } else if(isAddMode(request)){
            return GlobalConfigRequestType.ADD;
        } else if(isEditMode(request)){
            return GlobalConfigRequestType.EDIT;
        } else if(isSyncRequest(request)){
            return GlobalConfigRequestType.SYNC;
        }
        return GlobalConfigRequestType.UNKNOWN;
    }
    
    private boolean isDeleteMode(final HttpServletRequest request) {
        return PARAM_MODE_VALUE_DELETE.equals(request.getParameter(PARAM_MODE));
    }

    private boolean isEditMode(final HttpServletRequest request) {
        return PARAM_MODE_VALUE_EDIT.equals(request.getParameter(PARAM_MODE));
    }

    private boolean isAddMode(final HttpServletRequest request) {
        return PARAM_MODE_VALUE_ADD.equals(request.getParameter(PARAM_MODE));
    }

    private boolean isSyncRequest(final HttpServletRequest request) {
        return PARAM_MODE_VALUE_SYNC.equals(request.getParameter(PARAM_MODE));
    }
}
