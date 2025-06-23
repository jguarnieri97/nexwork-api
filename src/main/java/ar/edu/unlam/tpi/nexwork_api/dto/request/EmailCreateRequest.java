package ar.edu.unlam.tpi.nexwork_api.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EmailCreateRequest {
    private String type;
    private String subject;
    private Map<String, String> templateVariables;
}