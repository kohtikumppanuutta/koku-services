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
import javax.persistence.Version;

import org.bouncycastle.util.encoders.Base64;

import fi.arcusys.koku.palvelukanava.palvelut.facade.dto.VeeraForm;

@Entity(name = "VeeraForm")
public class VeeraFormEntity implements Serializable, VeeraForm {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer entryId;
	@Basic
	private Integer folderId;
	@Basic
	private Integer type;
	@Basic
	private String identity;
	@Basic
	private String identity2;
	@Basic
	private String description;
	@Basic
	private String helpContent;
	@Basic
	private Long companyId;
	@Column(nullable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar modifiedAt;
	@Version
	private int optlockver;
	
	public VeeraFormEntity() {
		
	}

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getIdentity2() {
		return identity2;
	}

	public void setIdentity2(String identity2) {
		this.identity2 = identity2;
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
	
	public String getIdentity_64() {
		return new String(Base64.decode(this.identity.getBytes()));
	}

	public String getIdentity2_64() {
		return new String(Base64.decode(this.identity2.getBytes()));
	}

	public static String identityTo64(String id) {
		return new String(Base64.encode(id.getBytes()));
	}
	
	@PrePersist
	void prePersist() {
		setCreatedAt(Calendar.getInstance());
	}
	
	@PreUpdate
	void preUpdate() {
		setModifiedAt(Calendar.getInstance());
	}
	
	@Override
	public String toString() {
		return "VeeraForm [companyId=" + companyId + ",\\n description="
		+ description + ",\\n entryId=" + entryId + ",\\n folderId="
		+ folderId + ",\\n helpContent=" + helpContent
		+ ",\\n identity=" + identity + ",\\n identity2=" + identity2
		+ ",\\n type=" + type + "]";
	}
	
	/*private Integer entryId;
	private Integer folderId;
	private Integer type;
	private String identity;
	private String identity2;
	private String description;
	private String helpContent;
	private Long companyId;

	public Integer getEntryId() {
		return this.entryId;
	}

	public void setEntryId(Integer entryid) {
		this.entryId = entryid;
	}

	public Integer getFolderId() {
		return this.folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public String getIdentity() {
		return this.identity;
	}

	public String getIdentity_64() {
		return new String(Base64.decode(this.identity.getBytes()));
	}

	public String getIdentity2_64() {
		return new String(Base64.decode(this.identity2.getBytes()));
	}

	public static String identityTo64(String id) {
		return new String(Base64.encode(id.getBytes()));
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getIdentity2() {
		return this.identity2;
	}

	public void setIdentity2(String identity2) {
		this.identity2 = identity2;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public static List<VeeraForm> getFormsLike(String keyword, Long companyId) {
		Transaction tx = null;
		List<VeeraForm> forms = null;

		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(VeeraForm.class);
			criteria
					.add(Restrictions.ilike("description", "%" + keyword + "%"));
			forms = criteria.list();
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
		return forms;
	}

	@SuppressWarnings("unchecked")
	public static List<VeeraForm> getFormsLike(String keyword, Long companyId, int maxResults) {
		Transaction tx = null;
		List<VeeraForm> forms = null;

		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(VeeraForm.class);
			criteria.setMaxResults(maxResults);
			criteria
					.add(Restrictions.ilike("description", "%" + keyword + "%"));
					
			forms = criteria.list();
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
		return forms;
	}
	
	public static void createForm(VeeraForm form) {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(form);
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
	public static VeeraForm getForm(Integer entryId) {
		Transaction tx = null;
		List results = null;

		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			Query q = session
					.createQuery("select c from VeeraForm as c where c.entryId = :entryId");

			q.setInteger("entryId", entryId.intValue());
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
			return (VeeraForm) results.get(0);
		}
		return null;
	}

	public static int deleteForm(Integer entryId) {
		Transaction tx = null;
		int results = 0;

		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			Query q = session
					.createQuery("delete from VeeraForm where entryId = :entryId");

			q.setInteger("entryId", entryId.intValue());
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
	}

	public static void updateForm(VeeraForm form) {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(form);
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

	@Override
	public String toString() {
		return "VeeraForm [companyId=" + companyId + ",\\n description="
				+ description + ",\\n entryId=" + entryId + ",\\n folderId="
				+ folderId + ",\\n helpContent=" + helpContent
				+ ",\\n identity=" + identity + ",\\n identity2=" + identity2
				+ ",\\n type=" + type + "]";
	}*/

}