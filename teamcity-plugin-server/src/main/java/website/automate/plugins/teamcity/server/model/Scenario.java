package website.automate.plugins.teamcity.server.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("scenario")
public class Scenario extends Base {

    private static final long serialVersionUID = 1488221815086152013L;
    
    private String title;

    public Scenario(){
        super();
    }
    
    public Scenario(String id, String title){
        super(id);
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
