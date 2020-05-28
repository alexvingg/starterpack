package br.com.starterpack.entity;

import br.com.starterpack.core.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Document("project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project extends AbstractEntity {

    @NotEmpty
    private String name;

    @DecimalMin(value = "0.00")
    @NotNull
    private BigDecimal cost;

    @Transient
    private List<Task> tasks;

}
