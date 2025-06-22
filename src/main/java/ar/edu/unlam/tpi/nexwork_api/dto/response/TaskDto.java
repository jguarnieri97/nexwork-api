package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDto {
    private Long id;
    private String description;
}
