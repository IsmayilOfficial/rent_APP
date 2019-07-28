package ee.ut.esi.group4.rentit.inventory.domain.model;

import ee.ut.esi.group4.rentit.common.domain.BusinessPeriod;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PlantReservation {
    @Id
    @GeneratedValue
    Long id;

    @Embedded
    BusinessPeriod schedule;

    @ManyToOne
    PlantInventoryItem plant;
}
