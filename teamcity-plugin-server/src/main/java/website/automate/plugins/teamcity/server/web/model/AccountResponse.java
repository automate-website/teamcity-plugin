package website.automate.plugins.teamcity.server.web.model;

import java.util.List;

public class AccountResponse extends AbstractResponse {

    private String username;
    
    private List<ProjectResponse> projects;

    public List<ProjectResponse> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectResponse> projects) {
        this.projects = projects;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
