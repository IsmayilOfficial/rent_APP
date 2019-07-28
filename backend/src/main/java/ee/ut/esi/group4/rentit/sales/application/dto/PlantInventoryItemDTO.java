package ee.ut.esi.group4.rentit.sales.application.dto;

import ee.ut.esi.group4.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import ee.ut.esi.group4.rentit.inventory.domain.model.EquipmentCondition;
import ee.ut.esi.group4.rentit.sales.domain.PlantLocation;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;


@Data

public class PlantInventoryItemDTO extends RepresentationModel<PlantInventoryItemDTO> {

    Long id;

    String serialNumber;

    EquipmentCondition equipmentCondition;

    PlantInventoryEntryDTO plantInfo;

    PlantLocation location;

}
