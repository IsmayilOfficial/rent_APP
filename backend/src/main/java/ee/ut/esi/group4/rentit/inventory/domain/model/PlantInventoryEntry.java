package ee.ut.esi.group4.rentit.inventory.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Value
@NoArgsConstructor(force=true,access= AccessLevel.PROTECTED)
@AllArgsConstructor(staticName="of")
public class PlantInventoryEntry {
    @Id
    @GeneratedValue
    Long id;

    String name;
    String description;
    @Column(precision=8, scale=2)
    BigDecimal price;

}
