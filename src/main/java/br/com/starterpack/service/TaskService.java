package br.com.starterpack.service;

import br.com.starterpack.core.service.AbstractCrudService;
import br.com.starterpack.entity.QTask;
import br.com.starterpack.entity.Task;
import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.core.repository.IRepository;
import br.com.starterpack.repository.TaskRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TaskService extends AbstractCrudService<Task, String> {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public IRepository<Task, String> getRepository() {
        return this.taskRepository;
    }

    @Override
    public void applyFilters(Map<String, String> filters, BooleanBuilder predicate) {

        if(filters.containsKey("dateStart") ){
            LocalDate start = LocalDate.parse(filters.get("dateStart"));
            predicate.and(QTask.task.scheduledTo.goe(start));
            filters.remove("dateStart");
        }

        if(filters.containsKey("dateEnd")){
            LocalDate end = LocalDate.parse(filters.get("dateEnd"));
            predicate.and(QTask.task.scheduledTo.loe(end));
            filters.remove("dateEnd");
        }
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
                () -> new  BusinessException("Task não existe"));
        taskUpdated.setDone(task.isDone());
        this.taskRepository.save(taskUpdated);
    }

    public List<Task> findByProjectId(String projectId) {
        return this.taskRepository.findByProjectId(projectId);
    }
}
