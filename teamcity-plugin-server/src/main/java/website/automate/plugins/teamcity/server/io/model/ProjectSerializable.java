package website.automate.plugins.teamcity.server.io.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("project")
public class ProjectSerializable extends AbstractSerializable {

    private static final long serialVersionUID = 2332154637587414919L;

    private String title;

    @XStreamImplicit(itemFieldName="scenario")
    private List<ScenarioSerializable> scenarios = new ArrayList<ScenarioSerializable>();
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ScenarioSerializable> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioSerializable> scenarios) {
        this.scenarios = scenarios;
    }
    
    public void addScenario(ScenarioSerializable scenario){
        this.scenarios.add(scenario);
    }
}
