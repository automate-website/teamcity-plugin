package website.automate.plugins.teamcity.server.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("project")
public class Project extends Base {

    private static final long serialVersionUID = 2332154637587414919L;

    private String title;

    public Project(){
        super();
    }
    
    public Project(String id, String title) {
        super(id);
        this.title = title;
    }

    private List<Scenario> scenarios = new ArrayList<Scenario>();
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }
    
    public void addScenario(Scenario scenario){
        this.scenarios.add(scenario);
    }
}
