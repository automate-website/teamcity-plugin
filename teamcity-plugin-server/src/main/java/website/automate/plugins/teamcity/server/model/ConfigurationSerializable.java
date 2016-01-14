package website.automate.plugins.teamcity.server.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("configuration")
public class ConfigurationSerializable {

    @XStreamImplicit(itemFieldName="account")
    private List<AccountSerializable> accounts = new ArrayList<AccountSerializable>();
    
    public void addAccount(AccountSerializable account){
        this.accounts.add(account);
    }

    public List<AccountSerializable> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountSerializable> accounts) {
        this.accounts = new ArrayList<AccountSerializable>(accounts);
    }
}
