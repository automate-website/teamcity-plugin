package website.automate.plugins.teamcity.server.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.serverSide.crypt.RSACipher;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import com.thoughtworks.xstream.XStream;

import website.automate.plugins.teamcity.server.model.Account;
import website.automate.plugins.teamcity.server.model.Accounts;
import website.automate.plugins.teamcity.server.model.Project;
import website.automate.plugins.teamcity.server.model.Scenario;

public class ServerConfigPersistenceManager {

    private static final String CONFIG_FILE_NAME = "automate-website-config.xml";
    
    private File configFile;
    private final Map<String, Account> configuredAccounts = new ConcurrentHashMap<String, Account>();
    private XStream xStream;
    
    public ServerConfigPersistenceManager(@NotNull ServerPaths serverPaths){
        xStream = new XStream();
        xStream.setClassLoader(Accounts.class.getClassLoader());
        xStream.processAnnotations(new Class [] {
                Accounts.class,
                Account.class,
                Project.class,
                Scenario.class
        });
        
        configFile = new File(serverPaths.getConfigDir(), CONFIG_FILE_NAME);
        loadConfiguration();
    }
    
    private void loadConfiguration(){
        if(configFile.exists()){
            FileInputStream configFileStream = null;
            try {
                configFileStream = new FileInputStream(configFile);
                Accounts persistedAccountsWrapper = Accounts.class.cast(xStream.fromXML(configFile));
                Collection<Account> accounts = persistedAccountsWrapper.getAccounts();
                if(accounts != null){
                    for(Account account : accounts){
                        addAccount(account);
                    }
                }
            } catch (IOException e) {
                Loggers.SERVER.error("Failed to load automate.website config file: " + configFile, e);
            } finally {
                IOUtils.closeQuietly(configFileStream);
            }
        }
    }
    
    private void addAccount(Account account){
        this.configuredAccounts.put(account.getId(), account);
    }
    
    public void createAccount(String username, String password){
        Account account = new Account(generateId(), username, password);
        addAccount(account);
    }
    
    public void updateAccount(String id, String username, String password){
        Account account = configuredAccounts.get(id);
        account.setUsername(username);
        account.setPassword(password);
    }
    
    public void deleteAccount(String id){
        configuredAccounts.remove(id);
    }
    
    public Collection<Account> getAccounts(){
        return Collections.unmodifiableCollection(configuredAccounts.values());
    }
    
    private static String generateId(){
        return UUID.randomUUID().toString();
    }
    
    public String getHexEncodedPublicKey() {
        return RSACipher.getHexEncodedPublicKey();
    }
    
    public synchronized void saveConfiguration(){
        FileOutputStream configFileStream = null;
        try {
            configFileStream = new FileOutputStream(configFile);
            Accounts accountsToPersist = new Accounts();
            accountsToPersist.setAccounts(configuredAccounts.values());
            xStream.toXML(accountsToPersist, configFileStream);
        } catch (IOException e) {
            Loggers.SERVER.error("Failed to save Automate Website config file: " + configFile, e);
        } finally {
            IOUtils.closeQuietly(configFileStream);
        }
    }
}
