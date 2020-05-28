package br.com.starterpack.entity;

import br.com.starterpack.core.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Document("task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task extends AbstractEntity {

    @NotEmpty
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduledTo;

    private Integer priority;

    private boolean done;

    @DBRef
    private Project project;
}
