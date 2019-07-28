package ee.ut.esi.group4.rentit.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class ClientAccount {
    @Id
    @GeneratedValue
    Long id;
    String name;
    @Column(unique = true)
    String ourSystemAccount;
    @Column(unique = true)
    String clientSystemAccount;
    String password;
    String phrHref;

    //Todo
    String invoicingUrl;

//    public static ClientAccount of(ClientAccount plantInventoryEntry) {
//        ClientAccount client = new ClientAccount();
//        client.name = plantInventoryEntry.name;
//        client.username = plantInventoryEntry.username;
//        client.password = plantInventoryEntry.password;
//
//        return client;
//    }
}