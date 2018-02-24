package tigers.cave.webm.invoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tigers.cave.webm.invoice.dao.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	@Query("SELECT c FROM Client c WHERE c.delFlg = :notDel AND c.clientNo = :clientNo ")
	Client findByClientNo(@Param("clientNo") int clientNo, @Param("notDel") String delFlg);

}
