package ar.edu.unlam.tpi.nexwork_api.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContractsFinalizeRequest {
            String state;
            String detail;
            List<String> files;
}
