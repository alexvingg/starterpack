package br.com.starterpack.repository;

import br.com.starterpack.entity.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends IRepository<Task, String> {
}
