package br.com.starterpack.service;

import br.com.starterpack.core.repository.BaseRepository;
import br.com.starterpack.core.service.AbstractCrudService;
import br.com.starterpack.entity.Project;
import br.com.starterpack.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService extends AbstractCrudService<Project, String> {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskService taskService;

    @Override
    public BaseRepository<Project, String> getRepository() {
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
