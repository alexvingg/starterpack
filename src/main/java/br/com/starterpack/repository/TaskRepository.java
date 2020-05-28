package br.com.starterpack.repository;

import br.com.starterpack.core.repository.IRepository;
import br.com.starterpack.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends IRepository<Task, String> {

    List<Task> findByProjectId(String projectId);
}
