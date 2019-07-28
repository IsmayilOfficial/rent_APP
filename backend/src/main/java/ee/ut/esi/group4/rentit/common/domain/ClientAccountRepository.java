package ee.ut.esi.group4.rentit.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long> {
    @Query("select p from ClientAccount p where LOWER(p.ourSystemAccount) = ?1")
    Optional<ClientAccount> findByUserName(String username);
}
