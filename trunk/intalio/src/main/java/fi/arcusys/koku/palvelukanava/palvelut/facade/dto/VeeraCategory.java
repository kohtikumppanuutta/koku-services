package fi.arcusys.koku.palvelukanava.palvelut.facade.dto;

public interface VeeraCategory {

	Integer getEntryId();

	void setEntryId(Integer entryId);

	Integer getParent();

	void setParent(Integer parent);

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	String getHelpContent();

	void setHelpContent(String helpContent);

	Long getCompanyId();

	void setCompanyId(Long companyId);

	int getFormCount();

	void setFormCount(int formCount);

}