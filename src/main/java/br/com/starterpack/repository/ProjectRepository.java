package br.com.starterpack.repository;

import br.com.starterpack.core.repository.IRepository;
import br.com.starterpack.entity.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends IRepository<Project, String> {
}
