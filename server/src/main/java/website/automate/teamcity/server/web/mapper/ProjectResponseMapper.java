package website.automate.teamcity.server.web.mapper;

import website.automate.teamcity.server.io.model.ProjectSerializable;
import website.automate.teamcity.server.support.Mapper;
import website.automate.teamcity.server.web.model.ProjectResponse;

public class ProjectResponseMapper extends Mapper<ProjectSerializable, ProjectResponse> {

    private static final ProjectResponseMapper INSTANCE = new ProjectResponseMapper();
    
    private final ScenarioResponseMapper scenarioMapper = ScenarioResponseMapper.getInstance();
    
    public static ProjectResponseMapper getInstance() {
        return INSTANCE;
    }
    
    @Override
    public ProjectResponse map(ProjectSerializable source) {
        ProjectResponse target = new ProjectResponse();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setScenarios(scenarioMapper.safeMapCollection(source.getScenarios()));
        return target;
    }

}
