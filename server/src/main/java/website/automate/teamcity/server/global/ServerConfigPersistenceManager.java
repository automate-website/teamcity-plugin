package website.automate.teamcity.server.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

import website.automate.teamcity.server.io.model.AccountSerializable;
import website.automate.teamcity.server.io.model.ConfigurationSerializable;
import website.automate.teamcity.server.io.model.ProjectSerializable;
import website.automate.teamcity.server.io.model.ScenarioSerializable;

public class ServerConfigPersistenceManager {

    private static ServerConfigPersistenceManager INSTANCE;
    
    static final String CONFIG_FILE_NAME = "automate-website-config.xml";
    
    private File configFile;
    private final Map<String, AccountSerializable> configuredAccounts = new ConcurrentHashMap<String, AccountSerializable>();
    private XStream xStream;
    
    public ServerConfigPersistenceManager(@NotNull ServerPaths serverPaths){
        xStream = new XStream();
        xStream.setClassLoader(ConfigurationSerializable.class.getClassLoader());
        xStream.processAnnotations(new Class [] {
                ConfigurationSerializable.class,
                AccountSerializable.class,
                ProjectSerializable.class,
                ScenarioSerializable.class
        });
        
        configFile = new File(serverPaths.getConfigDir(), CONFIG_FILE_NAME);
        loadConfiguration();
        
        INSTANCE = this;
    }
    
    public static ServerConfigPersistenceManager getInstance(){
        return INSTANCE;
    }
    
    void loadConfiguration(){
        if(configFile.exists()){
            FileInputStream configFileStream = null;
            try {
                configFileStream = new FileInputStream(configFile);
                ConfigurationSerializable configurationSerializable = ConfigurationSerializable.class.cast(xStream.fromXML(configFile));
                Collection<AccountSerializable> accounts = configurationSerializable.getAccounts();
                if(accounts != null){
                    for(AccountSerializable account : accounts){
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
    
    private void addAccount(AccountSerializable account){
        this.configuredAccounts.put(account.getId(), account);
    }
    
    public AccountSerializable createAccount(String username, String password){
        AccountSerializable account = new AccountSerializable();
        account.setId(generateId());
        account.setUsername(username);
        account.setPassword(password);
        addAccount(account);
        return account;
    }
    
    public AccountSerializable updateAccount(String id, String username, String password){
        AccountSerializable account = configuredAccounts.get(id);
        account.setUsername(username);
        account.setPassword(password);
        return account;
    }
    
    public void deleteAccount(String id){
        configuredAccounts.remove(id);
    }
    
    public AccountSerializable getAccount(String id){
        return configuredAccounts.get(id);
    }
    
    public Collection<AccountSerializable> getAccounts(){
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
            ConfigurationSerializable configurationSerializable = new ConfigurationSerializable();
            configurationSerializable.setAccounts(new ArrayList<AccountSerializable>(configuredAccounts.values()));
            xStream.toXML(configurationSerializable, configFileStream);
        } catch (IOException e) {
            Loggers.SERVER.error("Failed to save Automate Website config file: " + configFile, e);
        } finally {
            IOUtils.closeQuietly(configFileStream);
        }
    }
}
