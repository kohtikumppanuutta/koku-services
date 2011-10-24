package fi.arcusys.koku.palvelukanava.palvelut.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;


import fi.arcusys.koku.palvelukanava.palvelut.model.FormHolder;
import fi.arcusys.koku.palvelukanava.palvelut.model.VeeraFormEntity;
import fi.arcusys.koku.palvelukanava.palvelut.service.VeeraFormService;

@Stateless
@Local({VeeraFormService.class})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VeeraFormServiceImpl extends AbstractService<VeeraFormEntity> implements VeeraFormService {

	public VeeraFormServiceImpl() {
		super(VeeraFormEntity.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VeeraFormEntity> findByDescription(String description, int maxResults) {
		String lower = description != null && description.length() > 0 ? "%" + description + "%": "%";
		return entityManager.createQuery("select v from VeeraForm v where lower(description) like :description")
			.setParameter("description", lower)
			.setMaxResults(maxResults)
			.getResultList();
	}

	@Override
	public VeeraFormEntity findByEntryId(Integer entryId) {
		return (VeeraFormEntity)entityManager.createQuery(
			"select c from VeeraForm as c where c.entryId = :entryId")
			.setParameter("entryId", entryId)
			.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VeeraFormEntity> findChildForms(Integer folderId) {
		return entityManager.createQuery(
			"select f from VeeraForm as f where f.folderId = :category")
			.setParameter("category", folderId)
			.getResultList();
	}

	@Override
	public int removeByEntryId(Integer entryId) {
		return entityManager.createQuery("delete from VeeraForm where entryId = :entryId")
			.setParameter("entryId", entryId)
			.executeUpdate();
	}

	@Override
	public List<VeeraFormEntity> findChildFormsByFormHolders(Integer parent,
			long companyId, List<FormHolder> holders) {
		List<VeeraFormEntity> forms = findChildForms(parent);
		List<VeeraFormEntity> res = new ArrayList<VeeraFormEntity>();

		for (VeeraFormEntity form : forms) {
			if (form.getType().intValue() == 2) {
				for (FormHolder holder : holders) {
					if (form.getIdentity().equals(
							VeeraFormEntity.identityTo64(holder.getName())))
						res.add(form);
				}
			} else {
				res.add(form);
			}
		}
		return res;
	}

	@Override
	public List<?> findChildFormsCount(Integer categoryId, long companyId) {
		return entityManager.createQuery(
				"SELECT vc.entryId as categoryId, COUNT(vf.entryId) as formsCount FROM VeeraForm vf, VeeraCategory vc WHERE " +
				"vc.companyId = :companyId AND vc.parent = :rootCategory AND vf.folderId = vc.entryId GROUP BY vc.entryId")
				.setParameter("rootCategory", categoryId)
				.setParameter("companyId", companyId)
				.getResultList();
	}
}
