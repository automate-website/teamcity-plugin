package website.automate.plugins.teamcity.server.web.mapper;

import website.automate.plugins.teamcity.server.io.model.ScenarioSerializable;
import website.automate.plugins.teamcity.server.support.Mapper;
import website.automate.plugins.teamcity.server.web.model.ScenarioResponse;

public class ScenarioResponseMapper extends Mapper<ScenarioSerializable, ScenarioResponse> {

    private static final ScenarioResponseMapper INSTANCE = new ScenarioResponseMapper();
    
    public static ScenarioResponseMapper getInstance() {
        return INSTANCE;
    }
    
    @Override
    public ScenarioResponse map(ScenarioSerializable source) {
        ScenarioResponse target = new ScenarioResponse();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        return target;
    }

}
