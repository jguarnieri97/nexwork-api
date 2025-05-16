package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.exceptions.ValidatorException;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class Validator {

    public static void validateBudgetsUserIdNotNull(Long applicantId, Long supplierId) {
        if (Objects.nonNull(applicantId) && Objects.nonNull(supplierId))
            throw new ValidatorException("applicantId and supplierId cannot be set together");
        if (Objects.isNull(applicantId) && Objects.isNull(supplierId))
            throw new ValidatorException("applicantId and supplierId cannot be null");
    }

}
