package website.automate.plugins.teamcity.server.global;

import java.util.Collection;

import website.automate.plugins.teamcity.server.model.AccountSerializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerConfigModel {

    private ServerConfigPersistenceManager configPersistenceManager;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    public ServerConfigModel(){
        this.configPersistenceManager = ServerConfigPersistenceManager.getInstance();
    }
    
    public String getAccountsAsJson() throws JsonProcessingException{
        Collection<AccountSerializable> accounts = configPersistenceManager.getAccounts();
        return mapper.writeValueAsString(accounts.toArray(new AccountSerializable[accounts.size()]));
    }
}
