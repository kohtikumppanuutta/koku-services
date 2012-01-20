package fi.arcusys.koku.common.service.datamodel;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Entity for representing RequestTemplate in KV-Requests functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findRequestTemplatesByPrefix", query = "SELECT tmp FROM RequestTemplate tmp WHERE tmp.subject LIKE :prefix  ORDER BY tmp.id DESC"),
    @NamedQuery(name = "findRequestTemplatesByPrefixAndUser", query = "SELECT tmp FROM RequestTemplate tmp WHERE tmp.subject LIKE :prefix AND " +
    		" (tmp.visibility = :visibility_all OR tmp.visibility = :visibility_organization OR tmp.visibility = :visibility_creator AND tmp.creator = :user)" +
    		" ORDER BY tmp.id DESC")
})
public class RequestTemplate extends AbstractEntity {
    private String subject;

    @ManyToOne
    private User creator;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Question> questions;
    
    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    /**
     * @return the visibility
     */
    public Visibility getVisibility() {
        return visibility;
    }

    /**
     * @param visibility the visibility to set
     */
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the creator
     */
    public User getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * @return the questions
     */
    public Set<Question> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
    
}
