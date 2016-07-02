package website.automate.teamcity.server.web.mapper;

import website.automate.teamcity.server.io.model.ScenarioSerializable;
import website.automate.teamcity.server.support.Mapper;
import website.automate.teamcity.server.web.model.ScenarioResponse;

public class ScenarioResponseMapper extends Mapper<ScenarioSerializable, ScenarioResponse> {

    private static final ScenarioResponseMapper INSTANCE = new ScenarioResponseMapper();
    
    public static ScenarioResponseMapper getInstance() {
        return INSTANCE;
    }
    
    @Override
    public ScenarioResponse map(ScenarioSerializable source) {
        ScenarioResponse target = new ScenarioResponse();
        target.setId(source.getId());
        target.setTitle(source.getName());
        return target;
    }

}
