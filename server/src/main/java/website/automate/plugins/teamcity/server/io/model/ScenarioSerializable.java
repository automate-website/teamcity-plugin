package website.automate.plugins.teamcity.server.io.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("scenario")
public class ScenarioSerializable extends AbstractSerializable {

    private static final long serialVersionUID = 1488221815086152013L;
    
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
