package website.automate.plugins.teamcity.server.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("account")
public class AccountSerializable extends AbstractSerializable {

    private static final long serialVersionUID = -1340244196417263060L;

    private String username;
    
    private String password;

    @XStreamImplicit(itemFieldName="project")
    private List<ProjectSerializable> projects = new ArrayList<ProjectSerializable>();
    
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
    
    public List<ProjectSerializable> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectSerializable> projects) {
        this.projects = projects;
    }
    
    public void addProject(ProjectSerializable project){
        this.projects.add(project);
    }

}
