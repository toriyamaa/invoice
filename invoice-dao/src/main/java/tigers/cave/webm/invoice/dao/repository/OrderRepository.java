package tigers.cave.webm.invoice.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tigers.cave.webm.invoice.dao.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	@Query("SELECT o FROM Order o WHERE o.delFlg = '0' AND o.clientTbl.clientNo = :clientNo AND o.createDatetime BETWEEN :startDate AND :endDate")
	List<Order> findByClientNoAndInvoiceTerm(@Param("clientNo") int clientNo, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
}
