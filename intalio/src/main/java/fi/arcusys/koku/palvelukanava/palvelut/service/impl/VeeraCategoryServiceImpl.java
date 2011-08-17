package fi.arcusys.koku.palvelukanava.palvelut.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;

import fi.arcusys.koku.palvelukanava.palvelut.model.VeeraCategoryEntity;
import fi.arcusys.koku.palvelukanava.palvelut.service.VeeraCategoryService;

@Stateless
@Local({VeeraCategoryService.class})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VeeraCategoryServiceImpl extends AbstractService<VeeraCategoryEntity> implements VeeraCategoryService {

	public VeeraCategoryServiceImpl() {
		super(VeeraCategoryEntity.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VeeraCategoryEntity> findAllByCompanyId(long companyId) {
		return entityManager.createQuery("select c from VeeraCategory as c where c.companyId = :companyId")
			.setParameter("companyId", companyId)
			.getResultList();
	}

	@Override
	public VeeraCategoryEntity findByEntryAndCompanyId(Integer entryId, long companyId) {
		VeeraCategoryEntity category = null;
		try {
			category = (VeeraCategoryEntity)entityManager.createQuery(
			"select c from VeeraCategory as c where c.entryId = :entryId and c.companyId = :companyId ")
			.setParameter("entryId", entryId)
			.setParameter("companyId", companyId)
			.getSingleResult();
		} catch (NoResultException nre) {/*ignore*/}
		return category;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VeeraCategoryEntity> findChildCategories(Integer parent,
			long companyId) {
		return (List<VeeraCategoryEntity>)entityManager.createQuery(
				"select c from VeeraCategory as c where c.parent = :category and c.companyId = :companyId and c.entryId <> '1'")
				.setParameter("category", parent)
				.setParameter("companyId", companyId)
				.getResultList();
	}

	@Override
	public int removeByEntryAndCompanyId(Integer entryId, long companyId) {
		return entityManager.createQuery(
				"delete from VeeraCategory where entryId = :entryId and companyId = :companyId")
				.setParameter("entryId", entryId)
				.setParameter("companyId", companyId)
				.executeUpdate();
	}

	
	

}
