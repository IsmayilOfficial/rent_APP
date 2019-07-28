package ee.ut.esi.group4.rentit.sales.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionRepository extends JpaRepository<POExtension, Long> {

}
