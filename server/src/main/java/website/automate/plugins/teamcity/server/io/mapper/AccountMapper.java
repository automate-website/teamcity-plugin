package website.automate.plugins.teamcity.server.io.mapper;

import website.automate.manager.api.client.model.Authentication;
import website.automate.plugins.teamcity.server.io.model.AccountSerializable;
import website.automate.plugins.teamcity.server.support.Mapper;

public class AccountMapper extends Mapper<AccountSerializable, Authentication> {

    private static final AccountMapper INSTANCE = new AccountMapper();
    
    public static AccountMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Authentication map(AccountSerializable source) {
        return Authentication.of(source.getUsername(), source.getPassword());
    }
    
}
