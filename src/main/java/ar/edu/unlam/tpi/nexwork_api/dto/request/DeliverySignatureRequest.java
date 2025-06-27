package ar.edu.unlam.tpi.nexwork_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliverySignatureRequest {
    @NotNull(message = "Signature cannot be null")
    @NotBlank(message = "Signature cannot be blank")
    private String signature;

    @NotNull(message = "Clarification cannot be null")
    @NotBlank(message = "Clarification cannot be blank")
    private String clarification;
}
