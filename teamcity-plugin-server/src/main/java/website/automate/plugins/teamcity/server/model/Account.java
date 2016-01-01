package website.automate.plugins.teamcity.server.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("account")
public class Account extends Base {

    private static final long serialVersionUID = -1340244196417263060L;

    private String username;
    
    private String password;

    private List<Project> projects = new ArrayList<Project>();
    
    public Account(){
        super();
    }
    
    public Account(String id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
    public void addProject(Project project){
        this.projects.add(project);
    }

}
