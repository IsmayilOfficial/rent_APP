package ee.ut.esi.group4.rentit.sales.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class POExtensionDTO {
    Long entityId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate oldEndDate;
    LocalDate endDate;
    String client;

//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    po.createdby  = authentication.getName();
}
