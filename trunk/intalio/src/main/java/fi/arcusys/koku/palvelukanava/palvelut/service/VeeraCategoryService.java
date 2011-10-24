package fi.arcusys.koku.palvelukanava.palvelut.service;

import java.util.List;

import fi.arcusys.koku.palvelukanava.palvelut.model.VeeraCategoryEntity;

public interface VeeraCategoryService extends Service<VeeraCategoryEntity> {
	public VeeraCategoryEntity findByEntryAndCompanyId(Integer entryId, long companyId);
	public List<VeeraCategoryEntity> findChildCategories(Integer parent, long companyId);
	public int removeByEntryAndCompanyId(Integer entryId, long companyId);
	public List<VeeraCategoryEntity> findAllByCompanyId(long companyId);
	
}
