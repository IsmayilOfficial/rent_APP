package ee.ut.esi.group4.rentit.sales.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class Invoice  {
    @Id
    @GeneratedValue
    Long id;
//    List<LineItems> name;

    Long poId;
    BigDecimal total;

    
}