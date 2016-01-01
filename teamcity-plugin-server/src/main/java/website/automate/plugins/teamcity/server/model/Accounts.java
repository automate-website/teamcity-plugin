package website.automate.plugins.teamcity.server.model;

import java.util.ArrayList;
import java.util.Collection;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("accounts")
public class Accounts {

    private Collection<Account> accounts = new ArrayList<Account>();
    
    public void addAccount(Account account){
        this.accounts.add(account);
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = new ArrayList<Account>(accounts);
    }
}
