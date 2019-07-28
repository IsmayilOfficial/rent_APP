package ee.ut.esi.group4.rentit.inventory.application.service;

import ee.ut.esi.group4.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import ee.ut.esi.group4.rentit.inventory.domain.model.PlantInventoryEntry;
import ee.ut.esi.group4.rentit.inventory.domain.repository.InventoryRepository;
import ee.ut.esi.group4.rentit.inventory.domain.repository.PlantReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    @Autowired
    PlantReservationRepository plantReservationRepository;

    public List<PlantInventoryEntryDTO> findAvailablePlants(String name, LocalDate startDate, LocalDate endDate) {
        List<PlantInventoryEntry> entries = inventoryRepository.findAvailablePlants(name, startDate, endDate);
        return plantInventoryEntryAssembler.toModels(entries);

    }

    public PlantInventoryEntryDTO findPlantById(long id) {
        return plantInventoryEntryAssembler.toModel(inventoryRepository.findById(id).orElse(null));
    }

}