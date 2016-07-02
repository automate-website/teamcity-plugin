package website.automate.teamcity.server.global;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import jetbrains.buildServer.serverSide.ServerPaths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.automate.teamcity.server.global.ServerConfigPersistenceManager;
import website.automate.teamcity.server.io.model.AccountSerializable;
import website.automate.teamcity.server.io.model.ProjectSerializable;
import website.automate.teamcity.server.io.model.ScenarioSerializable;

@RunWith(MockitoJUnitRunner.class)
public class ServerConfigPersistenceManagerTest {

    private static final String 
        USERNAME = "random bill",
        PASSWORD = "secr3t",
        PROJECT_ID = "a6757131-bafb-4642-afe7-6d41e5266f7f",
        PROJECT_TITLE = "Awesome project",
        SCENARIO_ID = "f6757131-bafb-4642-afe7-6d41e5266f7f",
        SCENARIO_TITLE = "Awesome scenario";
    
    @Mock
    private ServerPaths serverPaths;
    
    private ServerConfigPersistenceManager manager;
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    @Before
    public void init(){
        when(serverPaths.getConfigDir()).thenReturn(folder.getRoot().getAbsolutePath());
        manager = new ServerConfigPersistenceManager(serverPaths);
    }
    
    @Test
    public void createdAccountShouldBeSavedAndLoaded() throws IOException{
        AccountSerializable expectedAccount = manager.createAccount(USERNAME, PASSWORD);
        expectedAccount.addProject(createProject());
        
        manager.saveConfiguration();
        manager.loadConfiguration();
        
        AccountSerializable actualAccount = manager.getAccount(expectedAccount.getId());
        assertEquals(expectedAccount, actualAccount);
    }
    
    private ProjectSerializable createProject(){
        ProjectSerializable project = new ProjectSerializable();
        project.setTitle(PROJECT_TITLE);
        project.setId(PROJECT_ID);
        project.setScenarios(asList(createScenario()));
        return project;
    }
    
    private ScenarioSerializable createScenario(){
        ScenarioSerializable scenario = new ScenarioSerializable();
        scenario.setName(SCENARIO_TITLE);
        scenario.setId(SCENARIO_ID);
        return scenario;
    }
}
