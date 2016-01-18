package website.automate.plugins.teamcity.server.global;

import java.util.Collection;
import java.util.List;

import website.automate.plugins.teamcity.server.io.model.AccountSerializable;
import website.automate.plugins.teamcity.server.web.mapper.AccountResponseMapper;
import website.automate.plugins.teamcity.server.web.model.AccountResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerConfigModel {

    private final ServerConfigPersistenceManager configPersistenceManager = ServerConfigPersistenceManager.getInstance();
    
    private final AccountResponseMapper accountMapper = AccountResponseMapper.getInstance();
    
    private ObjectMapper mapper = new ObjectMapper();
    
    private List<AccountResponse> getAccounts(){
        Collection<AccountSerializable> accounts = configPersistenceManager.getAccounts();
        return accountMapper.safeMapCollection(accounts);
    }
    
    public String getAccountsAsJson() throws JsonProcessingException{
        Collection<AccountResponse> accounts = getAccounts();
        return mapper.writeValueAsString(accounts.toArray(new AccountResponse[accounts.size()]));
    }
}
