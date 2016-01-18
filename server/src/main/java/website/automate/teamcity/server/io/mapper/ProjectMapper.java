package website.automate.teamcity.server.io.mapper;

import website.automate.manager.api.client.model.Project;
import website.automate.teamcity.server.io.model.ProjectSerializable;
import website.automate.teamcity.server.support.Mapper;

public class ProjectMapper extends Mapper<Project, ProjectSerializable> {

    private static final ProjectMapper INSTANCE = new ProjectMapper();
    
    private final ScenarioMapper scenarioMapper = ScenarioMapper.getInstance();
    
    public static ProjectMapper getInstance() {
        return INSTANCE;
    }
    
    @Override
    public ProjectSerializable map(Project source) {
        ProjectSerializable target = new ProjectSerializable();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setScenarios(scenarioMapper.safeMapCollection(source.getScenarios()));
        return target;
    }

}
