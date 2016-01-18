package website.automate.teamcity.server.web.mapper;

import website.automate.teamcity.server.io.model.AccountSerializable;
import website.automate.teamcity.server.support.Mapper;
import website.automate.teamcity.server.web.model.AccountResponse;

public class AccountResponseMapper extends Mapper<AccountSerializable, AccountResponse> {

    private static final AccountResponseMapper INSTANCE = new AccountResponseMapper();

    private final ProjectResponseMapper projectMapper = ProjectResponseMapper.getInstance();
    
    public static AccountResponseMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public AccountResponse map(AccountSerializable source) {
        AccountResponse target = new AccountResponse();
        target.setId(source.getId());
        target.setUsername(source.getUsername());
        target.setProjects(projectMapper.safeMapCollection(source.getProjects()));
        return target;
    }
    
}
