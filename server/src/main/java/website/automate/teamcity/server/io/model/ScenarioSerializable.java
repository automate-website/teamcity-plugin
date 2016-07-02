package website.automate.teamcity.server.io.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("scenario")
public class ScenarioSerializable extends AbstractSerializable {

    private static final long serialVersionUID = 1488221815086152013L;
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
