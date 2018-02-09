package tigers.cave.webm.invoice.dao.repository.custom.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.util.StringUtils;

import tigers.cave.webm.invoice.dao.common.constant.DelFlg;
import tigers.cave.webm.invoice.dao.model.Invoice;
import tigers.cave.webm.invoice.dao.repository.criteria.InvoiceCriteria;
import tigers.cave.webm.invoice.dao.repository.custom.InvoiceRepositoryCustom;

/**
 * The Class InvoiceRepositoryImpl.
 */
public class InvoiceRepositoryImpl implements InvoiceRepositoryCustom {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/* (非 Javadoc)
	 * @see tigers.cave.webm.invoice.dao.repository.custom.InvoiceRepositoryCustom#findByCriteria(tigers.cave.webm.invoice.dao.repository.criteria.InvoiceCriteria)
	 */
	@Override
	public List<Invoice> findByCriteria(InvoiceCriteria invoiceCriteria) {

		StringBuilder jpql = new StringBuilder(
				"SELECT i FROM Invoice i WHERE i.delFlg = :notDel ");

		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, Integer> paramMapInteger = new HashMap<String, Integer>();
		Map<String, Date> paramMapDate = new HashMap<String, Date>();

		if (invoiceCriteria.getClientNo() != null) {
			jpql.append("AND i.clientTbl.clientNo = :clientNo ");
			paramMapInteger.put("clientNo", invoiceCriteria.getClientNo());
		}

		if (!StringUtils.isEmpty(invoiceCriteria.getInvoiceStatus())) {
			jpql.append("AND i.invoiceStatus = :invoiceStatus ");
			paramMap.put("invoiceStatus", invoiceCriteria.getInvoiceStatus());
		}

		if (invoiceCriteria.getInvoiceDateMin() != null) {
			jpql.append("AND i.invoiceEndDate >= :invoiceDateMin ");
			paramMapDate.put("invoiceDateMin", invoiceCriteria.getInvoiceDateMin());
		}

		if (invoiceCriteria.getInvoiceDateMax() != null) {
			jpql.append("AND i.invoiceStartDate <= :invoiceDateMax ");
			paramMapDate.put("invoiceDateMax", invoiceCriteria.getInvoiceDateMax());
		}

		jpql.append("ORDER BY i.invoiceNo ASC ");

		TypedQuery<Invoice> query = entityManager.createQuery(jpql.toString(), Invoice.class);

		query.setParameter("notDel", DelFlg.NOT_DELETE.getValue());

		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, Integer> entry : paramMapInteger.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, Date> entry : paramMapDate.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		if (invoiceCriteria.getStart() != null) {
			query.setFirstResult(invoiceCriteria.getStart());
		}

		if (invoiceCriteria.getMaxCount() != null) {
			query.setMaxResults(invoiceCriteria.getMaxCount());
		}

		return query.getResultList();
	}

	/* (非 Javadoc)
	 * @see tigers.cave.webm.invoice.dao.repository.custom.InvoiceRepositoryCustom#countAllByCriteria(tigers.cave.webm.invoice.dao.repository.criteria.InvoiceCriteria)
	 */
	@Override
	public long countAllByCriteria(InvoiceCriteria invoiceCriteria) {

		StringBuilder jpql = new StringBuilder(
				"SELECT COUNT(i) FROM Invoice i WHERE i.delFlg = :notDel ");

		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, Integer> paramMapInteger = new HashMap<String, Integer>();
		Map<String, Date> paramMapDate = new HashMap<String, Date>();

		if (invoiceCriteria.getClientNo() != null) {
			jpql.append("AND i.clientTbl.clientNo = :clientNo ");
			paramMapInteger.put("clientNo", invoiceCriteria.getClientNo());
		}

		if (!StringUtils.isEmpty(invoiceCriteria.getInvoiceStatus())) {
			jpql.append("AND i.invoiceStatus = :invoiceStatus ");
			paramMap.put("invoiceStatus", invoiceCriteria.getInvoiceStatus());
		}

		if (invoiceCriteria.getInvoiceDateMin() != null) {
			jpql.append("AND i.invoiceEndDate >= :invoiceDateMin ");
			paramMapDate.put("invoiceDateMin", invoiceCriteria.getInvoiceDateMin());
		}

		if (invoiceCriteria.getInvoiceDateMax() != null) {
			jpql.append("AND i.invoiceStartDate <= :invoiceDateMax ");
			paramMapDate.put("invoiceDateMax", invoiceCriteria.getInvoiceDateMax());
		}

		TypedQuery<Long> query = entityManager.createQuery(jpql.toString(), Long.class);

		query.setParameter("notDel", DelFlg.NOT_DELETE.getValue());

		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, Integer> entry : paramMapInteger.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, Date> entry : paramMapDate.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.getSingleResult().longValue();

	}

}
