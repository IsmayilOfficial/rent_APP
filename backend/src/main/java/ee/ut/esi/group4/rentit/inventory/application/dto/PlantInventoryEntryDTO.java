package ee.ut.esi.group4.rentit.inventory.application.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
public class PlantInventoryEntryDTO extends RepresentationModel<PlantInventoryEntryDTO> {
    Long _id;
    String name;
    String description;
    BigDecimal price;
}