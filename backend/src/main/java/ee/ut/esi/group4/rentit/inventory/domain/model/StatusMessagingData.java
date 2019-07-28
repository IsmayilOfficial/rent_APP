package ee.ut.esi.group4.rentit.inventory.domain.model;

import ee.ut.esi.group4.rentit.sales.domain.POStatus;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(force=true,access= AccessLevel.PROTECTED)
@AllArgsConstructor(staticName="of")
public class StatusMessagingData {
    @Id
    @GeneratedValue
    Long entityId;
    @Enumerated(EnumType.STRING)
    POStatus state;
    String client;
    String comment;


    public static StatusMessagingData of(Long entityId, POStatus state, String comment){
        StatusMessagingData po = new StatusMessagingData();

        po.entityId = entityId;
        po.state = state;
        po.comment = comment;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        po.client  = authentication.getName();

        return po;
    }

}