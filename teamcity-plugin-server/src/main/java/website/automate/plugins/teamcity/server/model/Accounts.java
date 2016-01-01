package website.automate.plugins.teamcity.server.model;

import java.util.ArrayList;
import java.util.Collection;

public class Accounts {

    private Collection<Account> accounts = new ArrayList<Account>();
    
    public void addAccount(Account account){
        this.accounts.add(account);
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }
}
