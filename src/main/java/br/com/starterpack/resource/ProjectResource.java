package br.com.starterpack.resource;


import br.com.starterpack.entity.Project;
import br.com.starterpack.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/projects")
public class ProjectResource implements ICrudResource<Project, ProjectService, String> {

    @Autowired
    private ProjectService projectService;

    @Override
    public ProjectService getService() {
        return projectService;
    }
}
