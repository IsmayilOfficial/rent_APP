package ee.ut.esi.group4.rentit.sales.application.dto;

import ee.ut.esi.group4.rentit.common.application.dto.BusinessPeriodDTO;
import ee.ut.esi.group4.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import ee.ut.esi.group4.rentit.sales.domain.POStatus;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
public class PurchaseOrderDTO extends RepresentationModel<PurchaseOrderDTO> {
    Long _id;
    BusinessPeriodDTO rentalPeriod;
    PlantInventoryEntryDTO plant;

    POStatus status;
    BigDecimal Total;

    String createdby;

    List<PlantInventoryItemDTO> reservations = new ArrayList<PlantInventoryItemDTO>();


}
