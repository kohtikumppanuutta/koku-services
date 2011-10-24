package fi.arcusys.koku.palvelukanava.palvelut.facade;

import static fi.arcusys.koku.palvelukanava.palvelut.facade.FacadeUtils.convert;
import static fi.arcusys.koku.palvelukanava.palvelut.facade.FacadeUtils.convertCategories;
import static fi.arcusys.koku.palvelukanava.palvelut.facade.FacadeUtils.convertFormHolders;
import static fi.arcusys.koku.palvelukanava.palvelut.facade.FacadeUtils.convertForms;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;

import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.FormHolderDTO;
import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraCategoryDTO;
import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraFormDTO;
import fi.arcusys.koku.palvelukanava.palvelut.service.VeeraCategoryService;
import fi.arcusys.koku.palvelukanava.palvelut.service.VeeraFormService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Apr 18, 2011
 */
@Stateless
@WebService(serviceName = "VeeraServicesFacade", portName = "VeeraServicesFacadePort", 
		endpointInterface = "fi.arcusys.koku.palvelukanava.palvelut.facade.VeeraServicesFacadeWS")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VeeraServicesSessionBean implements VeeraServicesFacadeWS {
	@EJB
	private VeeraCategoryService categoryService;

	@EJB
	private VeeraFormService formService;

	/**
	 * @param newCategory
	 */
	@Override
	public void createCategory(VeeraCategoryDTO newCategory) {
		categoryService.create(convert(newCategory));
	}

	/**
	 * @param newForm
	 */
	
	@Override
	public void createForm(VeeraFormDTO newForm) {
		formService.create(convert(newForm));
	}

	/**
	 * @param companyId
	 * @return
	 */
	@Override
	public List<VeeraCategoryDTO> findAllCategoriesByCompanyId(long companyId) {
		return convertCategories(categoryService.findAllByCompanyId(companyId));
	}

	/**
	 * @param entryId
	 * @param companyId
	 * @return
	 */
	@Override
	public VeeraCategoryDTO findCategoryByEntryAndCompanyId(Integer entryId,
			long companyId) {
		return convert(categoryService.findByEntryAndCompanyId(entryId, companyId));
	}

	/**
	 * @param parent
	 * @param companyId
	 * @return
	 */
	@Override
	public List<VeeraCategoryDTO> findChildCategories(Integer parent, long companyId) {
		return convertCategories(categoryService.findChildCategories(parent, companyId));
	}

	/**
	 * @param folderId
	 * @return
	 */
	@Override
	public List<VeeraFormDTO> findChildForms(Integer folderId) {
		return convertForms(formService.findChildForms(folderId));
	}

	/**
	 * @param parent
	 * @param companyId
	 * @param holders
	 * @return
	 */
	@Override
	public List<VeeraFormDTO> findChildFormsByFormHolders(Integer parent, long companyId, List<FormHolderDTO> holders) {
		return convertForms(formService.findChildFormsByFormHolders(parent, companyId, convertFormHolders(holders)));
	}

	/**
	 * @param categoryId
	 * @param companyId
	 * @return
	 */
	@Override
	public List<?> findChildFormsCount(Integer categoryId, long companyId) {
		return formService.findChildFormsCount(categoryId, companyId);
	}

	/**
	 * @param entryId
	 * @return
	 */
	@Override
	public VeeraFormDTO findFormByEntryId(Integer entryId) {
		return convert(formService.findByEntryId(entryId));
	}

	/**
	 * @param description
	 * @param maxResults
	 * @return
	 */
	@Override
	public List<VeeraFormDTO> findFormsByDescription(String description, int maxResults) {
		return convertForms(formService.findByDescription(description, maxResults));
	}

	/**
	 * @param entryId
	 * @param companyId
	 * @return
	 */
	@Override
	public int removeCategoryByEntryAndCompanyId(Integer entryId, long companyId) {
		return categoryService.removeByEntryAndCompanyId(entryId, companyId);
	}

	/**
	 * @param entryId
	 * @return
	 */
	@Override
	public int removeFormByEntryId(Integer entryId) {
		return formService.removeByEntryId(entryId);
	}

	/**
	 * @param category
	 */
	@Override
	public void updateCategory(VeeraCategoryDTO category) {
		categoryService.update(convert(category));
	}

	/**
	 * @param form
	 */
	@Override
	public void updateForm(VeeraFormDTO form) {
		formService.update(convert(form));
	}
}
