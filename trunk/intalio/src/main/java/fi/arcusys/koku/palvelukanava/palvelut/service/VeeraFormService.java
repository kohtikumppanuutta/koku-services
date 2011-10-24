package fi.arcusys.koku.palvelukanava.palvelut.service;

import java.util.List;

import fi.arcusys.koku.palvelukanava.palvelut.model.FormHolder;
import fi.arcusys.koku.palvelukanava.palvelut.model.VeeraFormEntity;

public interface VeeraFormService extends Service<VeeraFormEntity>{
	public VeeraFormEntity findByEntryId(Integer entryId);
	public List<VeeraFormEntity> findChildForms(Integer folderId);
	public int removeByEntryId(Integer entryId);
	public List<VeeraFormEntity> findByDescription(String description, int maxResults);
	public List<VeeraFormEntity> findChildFormsByFormHolders(Integer parent, long companyId, List<FormHolder> holders);
	public List<?> findChildFormsCount(Integer categoryId, long companyId);
	
}
