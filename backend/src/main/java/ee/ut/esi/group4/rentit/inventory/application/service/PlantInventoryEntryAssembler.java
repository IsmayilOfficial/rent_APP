package ee.ut.esi.group4.rentit.inventory.application.service;

import ee.ut.esi.group4.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import ee.ut.esi.group4.rentit.inventory.domain.model.PlantInventoryEntry;
import ee.ut.esi.group4.rentit.inventory.rest.InventoryRestController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PlantInventoryEntryAssembler
        extends RepresentationModelAssemblerSupport<PlantInventoryEntry, PlantInventoryEntryDTO> {

    public PlantInventoryEntryAssembler() {
        super(InventoryRestController.class, PlantInventoryEntryDTO.class);
    }

    @Override
    public PlantInventoryEntryDTO toModel(PlantInventoryEntry plant) {
        if (plant == null) return null;

        PlantInventoryEntryDTO dto = super.createModelWithId(plant.getId(), plant);
        dto.set_id(plant.getId());
        dto.setName(plant.getName());
        dto.setDescription(plant.getDescription());
        dto.setPrice(plant.getPrice());


        return dto;
    }

    public List<PlantInventoryEntryDTO> toModels(List<PlantInventoryEntry> plantInventoryEntries ) {

        return plantInventoryEntries.stream().map(item -> toModel(item)).collect(Collectors.toList());
    }
}
