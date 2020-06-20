package br.com.starterpack.repository;

import br.com.starterpack.core.repository.BaseRepository;
import br.com.starterpack.entity.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends BaseRepository<Project, String> {
}
