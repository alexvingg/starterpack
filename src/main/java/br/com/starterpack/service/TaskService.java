package br.com.starterpack.service;

import br.com.starterpack.entity.QTask;
import br.com.starterpack.entity.Task;
import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.repository.IRepository;
import br.com.starterpack.repository.TaskRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class TaskService implements ICrudService<Task, String>{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public IRepository<Task, String> getRepository() {
        return this.taskRepository;
    }

    @Override
    public void beforeGetAll(Map<String, String> filters, BooleanBuilder predicate, PageRequest pageRequest) {

        if(filters.containsKey("projectId")){
            predicate.and(QTask.task.project.id.eq(filters.get("projectId")));
            filters.remove("projectId");
        }

        if(filters.containsKey("dateStart") && filters.containsKey("dateEnd")){
            LocalDate start = LocalDate.parse(filters.get("dateStart"));
            LocalDate end = LocalDate.parse(filters.get("dateEnd"));
            predicate.and(QTask.task.scheduledTo.between(start, end));
            filters.remove("dateStart");
            filters.remove("dateEnd");
        }

    }

    @Override
    public Task mergeToUpdate(String id, Task objectUpdated, Task object) {

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
}
