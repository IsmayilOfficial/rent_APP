package ee.ut.esi.group4.rentit.inventory.domain.repository;

import ee.ut.esi.group4.rentit.inventory.domain.model.PlantInventoryEntry;
import ee.ut.esi.group4.rentit.inventory.domain.model.PlantInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class InventoryRepositoryImpl implements CustomInventoryRepository {
    @Autowired
    EntityManager em;

    public List<PlantInventoryEntry> findAvailablePlants(String name, LocalDate startDate, LocalDate endDate) {
        return em.createQuery("select p.plantInfo from PlantInventoryItem p where LOWER(p.plantInfo.name) like concat('%', ?1, '%') and p not in " +
                                "(select r.plant from PlantReservation r where ?2 < r.schedule.endDate and ?3 > r.schedule.startDate)",
                PlantInventoryEntry.class)
                .setParameter(1, name)
                .setParameter(2, startDate)
                .setParameter(3, endDate)
                .getResultList();
    }

    public List<PlantInventoryItem> confirmItemAvailableAtPeriod(Long id, LocalDate startDate, LocalDate endDate) {
        return em.createQuery("select p from PlantInventoryItem p where p.plantInfo.id=?1 and p not in " +
                        "(select r.plant from PlantReservation r where ?2 < r.schedule.endDate and ?3 > r.schedule.startDate)",
                PlantInventoryItem.class)
                .setParameter(1, id)
                .setParameter(2, startDate)
                .setParameter(3, endDate)
                .getResultList();
    }

}
