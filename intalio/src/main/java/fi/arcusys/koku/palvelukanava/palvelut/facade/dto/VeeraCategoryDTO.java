package fi.arcusys.koku.palvelukanava.palvelut.facade.dto;

import java.io.Serializable;

public class VeeraCategoryDTO implements Serializable, VeeraCategory {
	
	private static final long serialVersionUID = 1L;
	private Integer entryId;
	private Integer parent;
	private String name;
	private String description;
	private String helpContent;
	private Long companyId;
	private int formCount;
	
	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHelpContent() {
		return helpContent;
	}

	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public int getFormCount() {
		return formCount;
	}

	public void setFormCount(int formCount) {
		this.formCount = formCount;
	}
}