package tigers.cave.webm.invoice.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.repository.custom.InvoiceRepositoryCustom;

@Repository
public interface InvoiceRepository
    extends JpaRepository<Invoice, Integer>, InvoiceRepositoryCustom {

  @Query("SELECT i FROM Invoice i WHERE i.delFlg = :notDel AND i.invoiceNo = :invoiceNo ")
  Invoice findByInvoiceNo(@Param("invoiceNo") int invoiceNo, @Param("notDel") String delFlg);

  @Query("SELECT i "
      + "FROM Invoice i "
      + "WHERE i.delFlg = :notDel "
      + "AND i.clientTbl.clientNo = :clientNo "
      + "AND i.invoiceStartDate <= :endDate "
      + "AND i.invoiceEndDate >= :startDate ")
  List<Invoice> findByClientNoAndInvoiceTerm(
      @Param("clientNo") int clientNo,
      @Param("startDate") Date startDate,
      @Param("endDate") Date endDate,
      @Param("notDel") String delFlg);

}
