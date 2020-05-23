package br.com.starterpack.validation;

import br.com.starterpack.model.AbstractModel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
@NoArgsConstructor
public class ValidationService {

    @Autowired
    private Validator validator;

    public void validate(AbstractModel input) {
        Set<ConstraintViolation<AbstractModel>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public Set<ConstraintViolation<AbstractModel>> getViolations(AbstractModel input) {
        return validator.validate(input);
    }
}

