package ee.ut.esi.group4.rentit.inventory.domain.model;

import ee.ut.esi.group4.rentit.sales.domain.PlantLocation;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(force=true,access= AccessLevel.PROTECTED)
public class PlantInventoryItem {

    @Id @GeneratedValue
    Long id;

    String serialNumber;

    @Enumerated(EnumType.STRING)
    EquipmentCondition equipmentCondition;

    @ManyToOne
    PlantInventoryEntry plantInfo;

    @Enumerated(EnumType.STRING)
    PlantLocation location;

    public void setLocation(PlantLocation location){
        this.location =  location;
    }
}
