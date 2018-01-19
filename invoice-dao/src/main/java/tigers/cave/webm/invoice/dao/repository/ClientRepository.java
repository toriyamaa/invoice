package tigers.cave.webm.invoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tigers.cave.webm.invoice.dao.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
