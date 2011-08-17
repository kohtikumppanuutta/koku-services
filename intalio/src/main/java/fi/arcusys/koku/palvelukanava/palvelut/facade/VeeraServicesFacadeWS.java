package fi.arcusys.koku.palvelukanava.palvelut.facade;

import java.util.List;

import javax.jws.WebService;

import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.FormHolderDTO;
import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraCategoryDTO;
import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraFormDTO;

@WebService
public interface VeeraServicesFacadeWS {
	
	/**
	 * Category service methods 
	 */
	
	VeeraCategoryDTO findCategoryByEntryAndCompanyId(final Integer entryId, final long companyId);
	
	List<VeeraCategoryDTO> findChildCategories(final Integer parent, final long companyId);
	
	int removeCategoryByEntryAndCompanyId(final Integer entryId, final long companyId);
	
	List<VeeraCategoryDTO> findAllCategoriesByCompanyId(final long companyId);

	void createCategory(final VeeraCategoryDTO newCategory);

	void updateCategory(final VeeraCategoryDTO category);

	/**
	 * Form service methods 
	 */
	VeeraFormDTO findFormByEntryId(final Integer entryId);
	
	List<VeeraFormDTO> findChildForms(final Integer folderId);
	
	int removeFormByEntryId(final Integer entryId);
	
	List<VeeraFormDTO> findFormsByDescription(final String description, final int maxResults);
	
	List<VeeraFormDTO> findChildFormsByFormHolders(final Integer parent, final long companyId, final List<FormHolderDTO> holders);
	
	List<?> findChildFormsCount(final Integer categoryId, final long companyId);

	void createForm(final VeeraFormDTO newForm);

	void updateForm(final VeeraFormDTO form);
}
