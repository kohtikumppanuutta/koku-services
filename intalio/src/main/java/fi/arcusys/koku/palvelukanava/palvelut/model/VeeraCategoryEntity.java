package fi.arcusys.koku.palvelukanava.palvelut.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraCategory;

@Entity(name = "VeeraCategory")
public class VeeraCategoryEntity implements Serializable, VeeraCategory {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer entryId;
	@Basic
	private Integer parent;
	@Basic
	private String name;
	@Basic
	private String description;
	@Basic
	private String helpContent;
	@Basic
	private Long companyId;
	@Version
	private int optlockver;
	@Column(nullable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar modifiedAt;
	@Transient
	private int formCount;
	
	public VeeraCategoryEntity() {
		
	}

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

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public Calendar getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Calendar modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public int getOptlockver() {
		return optlockver;
	}

	public void setOptlockver(int optlockver) {
		this.optlockver = optlockver;
	}
	
	@PrePersist
	void prePersist() {
		setCreatedAt(Calendar.getInstance());
	}
	
	@PreUpdate
	void preUpdate() {
		setModifiedAt(Calendar.getInstance());
	}

	public int getFormCount() {
		return formCount;
	}

	public void setFormCount(int formCount) {
		this.formCount = formCount;
	}
	
	/*private Integer entryId;
	private Integer parent;
	private String name;
	private String description;
	private String helpContent;
	private Long companyId;

	public Integer getEntryId() {
		return this.entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParent() {
		return this.parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHelpContent() {
		return this.helpContent;
	}

	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@SuppressWarnings("unchecked")
	public static List getChildCategories(Integer category, Long companyId) {
		Transaction tx = null;
		List childs = null;

		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			Query q = session
					.createQuery("select c from VeeraCategory as c where c.parent = :category and c.companyId = :companyId and c.entryId <> '1'");

			q.setInteger("category", category.intValue());
			q.setLong("companyId", companyId.longValue());
			childs = q.list();
			tx.commit();
		} catch (RuntimeException e) {
			if ((tx != null) && (tx.isActive())) {
				try {
					tx.rollback();
				} catch (HibernateException e1) {
				}
				throw e;
			}
		}
		return childs;
	}

	@SuppressWarnings("unchecked")
	public static List getChildForms(Integer category, Long companyId) {
		Transaction tx = null;
		List childs = null;

		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			Query q = session
					.createQuery("select f from VeeraForm as f where f.folderId = :category");

			q.setInteger("category", category.intValue());
			childs = q.list();
			tx.commit();
		} catch (RuntimeException e) {
			if ((tx != null) && (tx.isActive())) {
				try {
					tx.rollback();
				} catch (HibernateException e1) {
				}
				throw e;
			}
		}
		return childs;
	}

	@SuppressWarnings("unchecked")
	public static List<VeeraForm> getChildFormsFiltered(Integer category,
			Long companyId, List<FormHolder> holders) {
		List<VeeraForm> forms = (List<VeeraForm>) getChildForms(category,
				companyId);
		List<VeeraForm> res = new ArrayList<VeeraForm>();

		for (VeeraForm form : forms) {
			if (form.getType().intValue() == 2) {
				for (FormHolder holder : holders) {
					if (form.getIdentity().equals(
							VeeraForm.identityTo64(holder.getName())))
						res.add(form);
				}
			} else {
				res.add(form);
			}
		}
		return res;
	}

	public static void createCategory(VeeraCategory category) {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(category);
			tx.commit();
		} catch (RuntimeException e) {
			if ((tx == null) || (!tx.isActive()))
				return;
			try {
				tx.rollback();
			} catch (HibernateException e1) {
			}
			throw e;
		}
	}

	public static void updateCategory(VeeraCategory category) {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(category);
			tx.commit();
		} catch (RuntimeException e) {
			if ((tx == null) || (!tx.isActive()))
				return;
			try {
				tx.rollback();
			} catch (HibernateException e1) {
			}
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static VeeraCategory getCategory(Integer entryId, Long companyId) {
		Transaction tx = null;
		List results = null;

		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			Query q = session
					.createQuery("select c from VeeraCategory as c where c.entryId = :entryId and c.companyId = :companyId ");

			q.setInteger("entryId", entryId.intValue());
			q.setLong("companyId", companyId.longValue());
			results = q.list();
			tx.commit();
		} catch (RuntimeException e) {
			if ((tx != null) && (tx.isActive())) {
				try {
					tx.rollback();
				} catch (HibernateException e1) {
				}
				throw e;
			}
		}
		if ((results != null) && (results.size() > 0)) {
			return (VeeraCategory) results.get(0);
		}
		return null;
	}

	public static int deleteCategory(Integer entryId, Long companyId) {
		Transaction tx = null;
		int results = 0;

		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			Query q = session
					.createQuery("delete from VeeraCategory where entryId = :entryId and companyId = :companyId");

			q.setInteger("entryId", entryId.intValue());
			q.setLong("companyId", companyId.longValue());
			results = q.executeUpdate();
			tx.commit();
		} catch (RuntimeException e) {
			if ((tx != null) && (tx.isActive())) {
				try {
					tx.rollback();
				} catch (HibernateException e1) {
				}
				throw e;
			}
		}
		return results;
	}*/
}