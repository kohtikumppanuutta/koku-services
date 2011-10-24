package fi.arcusys.koku.palvelukanava.palvelut.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.FormHolderDTO;
import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraCategory;
import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraCategoryDTO;
import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraForm;
import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraFormDTO;
import fi.arcusys.koku.palvelukanava.palvelut.model.FormHolder;
import fi.arcusys.koku.palvelukanava.palvelut.model.VeeraCategoryEntity;
import fi.arcusys.koku.palvelukanava.palvelut.model.VeeraFormEntity;

class FacadeUtils {

	static List<VeeraCategoryDTO> convertCategories(final List<VeeraCategoryEntity> categories) {
		if (categories == null || categories.isEmpty()) {
			return Collections.emptyList();
		}
		final List<VeeraCategoryDTO> result = new ArrayList<VeeraCategoryDTO>(categories.size());
		for (final VeeraCategoryEntity category : categories) {
			result.add(convert(category));
		}
		return result;
	}

	static List<VeeraFormDTO> convertForms(final List<VeeraFormEntity> forms) {
		if (forms == null || forms.isEmpty()) {
			return Collections.emptyList();
		}
		final List<VeeraFormDTO> result = new ArrayList<VeeraFormDTO>(forms.size());
		for (final VeeraFormEntity form : forms) {
			result.add(convert(form));
		}
		return result;
	}

	static List<FormHolder> convertFormHolders(final List<FormHolderDTO> formHolders) {
		if (formHolders == null || formHolders.isEmpty()) {
			return Collections.emptyList();
		}
		final List<FormHolder> result = new ArrayList<FormHolder>(formHolders.size());
		for (final FormHolderDTO formHolder : formHolders) {
			result.add(new FormHolder(formHolder.getName(), formHolder.getUrl()));
		}
		return result;
	}

	static VeeraCategoryDTO convert(final VeeraCategoryEntity category) {
		return copy(category, new VeeraCategoryDTO());
	}

	static VeeraCategoryEntity convert(final VeeraCategoryDTO categoryDTO) {
		return copy(categoryDTO, new VeeraCategoryEntity());
	}

	static VeeraFormDTO convert(final VeeraFormEntity form) {
		return copy(form, new VeeraFormDTO());
	}

	static VeeraFormEntity convert(final VeeraFormDTO form) {
		return copy(form, new VeeraFormEntity());
	}

	private static <T extends VeeraForm> T copy(final VeeraForm source, final T dest) {
		if (dest == null) {
			return null;
		}
		dest.setCompanyId(source.getCompanyId());
		dest.setDescription(source.getDescription());
		dest.setEntryId(source.getEntryId());
		dest.setFolderId(source.getFolderId());
		dest.setHelpContent(source.getHelpContent());
		dest.setIdentity(source.getIdentity());
		dest.setIdentity2(source.getIdentity2());
		dest.setType(source.getType());
		return dest;
	}

	private static <T extends VeeraCategory> T copy(final VeeraCategory source, final T dest) {
		if (dest == null) {
			return null;
		}
		dest.setCompanyId(source.getCompanyId());
		dest.setDescription(source.getDescription());
		dest.setEntryId(source.getEntryId());
		dest.setFormCount(source.getFormCount());
		dest.setHelpContent(source.getHelpContent());
		dest.setName(source.getName());
		dest.setParent(source.getParent());
		return dest;
	}
}
