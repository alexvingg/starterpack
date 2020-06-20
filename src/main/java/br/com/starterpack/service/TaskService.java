package br.com.starterpack.service;

import br.com.starterpack.core.repository.BaseRepository;
import br.com.starterpack.core.service.AbstractCrudService;
import br.com.starterpack.entity.Task;
import br.com.starterpack.exception.ModelNotFoundException;
import br.com.starterpack.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskService extends AbstractCrudService<Task, String> {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public BaseRepository<Task, String> getRepository() {
        return this.taskRepository;
    }

    @Override
    public List<CriteriaDefinition> applyFilters(Map<String, String> filters) {
        List<CriteriaDefinition> criterias = new ArrayList<>();
        if(filters.containsKey("dateStart") ){
            LocalDate start = LocalDate.parse(filters.get("dateStart"));
            criterias.add(new Criteria("scheduledTo").gte(start));
            filters.remove("dateStart");
        }

        if(filters.containsKey("dateEnd")){
            LocalDate end = LocalDate.parse(filters.get("dateEnd"));
            criterias.add(new Criteria("scheduledTo").lte(end));
            filters.remove("dateEnd");
        }
        return criterias;
    }

    @Override
    public Task mergeToUpdate(Task objectUpdated, Task object) {

        objectUpdated.setDescription(object.getDescription());
        objectUpdated.setPriority(object.getPriority());
        objectUpdated.setScheduledTo(object.getScheduledTo());

        return objectUpdated;
    }

    public void toogleDone(Task task){
        Task taskUpdated = this.taskRepository.findById(task.getId()).orElseThrow(
                () -> new ModelNotFoundException());
        taskUpdated.setDone(task.isDone());
        this.taskRepository.save(taskUpdated);
    }

    public List<Task> findByProjectId(String projectId) {
        return this.taskRepository.findByProjectId(projectId);
    }
}
