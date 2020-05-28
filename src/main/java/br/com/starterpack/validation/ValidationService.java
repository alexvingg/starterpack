package br.com.starterpack.validation;

import br.com.starterpack.entity.AbstractEntity;
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

    public void validate(AbstractEntity input) {
        Set<ConstraintViolation<AbstractEntity>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public Set<ConstraintViolation<AbstractEntity>> getViolations(AbstractEntity input) {
        return validator.validate(input);
    }
}

