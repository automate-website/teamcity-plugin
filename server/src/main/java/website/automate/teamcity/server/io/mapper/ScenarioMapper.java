package website.automate.teamcity.server.io.mapper;

import website.automate.manager.api.client.model.Scenario;
import website.automate.teamcity.server.io.model.ScenarioSerializable;
import website.automate.teamcity.server.support.Mapper;

public class ScenarioMapper extends Mapper<Scenario, ScenarioSerializable> {

    private static final ScenarioMapper INSTANCE = new ScenarioMapper();
    
    public static ScenarioMapper getInstance() {
        return INSTANCE;
    }
    
    @Override
    public ScenarioSerializable map(Scenario source) {
        ScenarioSerializable target = new ScenarioSerializable();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }

}
