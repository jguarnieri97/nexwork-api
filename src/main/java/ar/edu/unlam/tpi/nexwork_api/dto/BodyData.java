package ar.edu.unlam.tpi.nexwork_api.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BodyData {
    private String noteNumber;
    private List<DescriptionObject> descriptionData;
}
