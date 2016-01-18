package website.automate.teamcity.server.web.model;

import java.util.List;

public class ProjectResponse extends AbstractResponse {

    private String title;
    
    private List<ScenarioResponse> scenarios;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ScenarioResponse> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioResponse> scenarios) {
        this.scenarios = scenarios;
    }
}
