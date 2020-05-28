package br.com.starterpack.service;

import br.com.starterpack.core.service.ICrudService;
import br.com.starterpack.entity.Project;
import br.com.starterpack.core.repository.IRepository;
import br.com.starterpack.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements ICrudService<Project, String> {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskService taskService;

    @Override
    public IRepository<Project, String> getRepository() {
        return this.projectRepository;
    }

    @Override
    public Project mergeToUpdate(Project objectUpdated, Project object) {

        objectUpdated.setCost(object.getCost());
        objectUpdated.setName(object.getName());

        return objectUpdated;
    }

    @Override
    public void afterGetAll(List<Project> all) {
        all.forEach(project -> project.setTasks(this.taskService.findByProjectId(project.getId())));
    }
}
