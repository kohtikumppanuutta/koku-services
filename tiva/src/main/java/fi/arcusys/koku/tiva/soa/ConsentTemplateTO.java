package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 9, 2011
 */
@XmlType (name = "suostumuspohja", namespace = "http://soa.tiva.koku.arcusys.fi/",
propOrder={"concentTemplateId", "title" , "description", "creatorUid", "actions"})
public class ConsentTemplateTO {
    private Long concentTemplateId;
    private String title;
    private String description;
    private String creatorUid;
    private List<ActionRequestTO> actions;
    
    /**
     * @return the actions
     */
    @XmlElement(name = "toimenpiteet")
    public List<ActionRequestTO> getActions() {
        return actions;
    }
    /**
     * @param actions the actions to set
     */
    public void setActions(List<ActionRequestTO> actions) {
        this.actions = actions;
    }
    /**
     * @return the concentTemplateId
     */
    @XmlElement(name = "suostumuspohjaId")
    public Long getConcentTemplateId() {
        return concentTemplateId;
    }
    /**
     * @param concentTemplateId the concentTemplateId to set
     */
    public void setConcentTemplateId(Long concentTemplateId) {
        this.concentTemplateId = concentTemplateId;
    }
    /**
     * @return the title
     */
    @XmlElement(name = "otsikko")
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the description
     */
    @XmlElement(name = "saateteksti")
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the creatorUid
     */
    @XmlElement(name = "laatija")
    public String getCreatorUid() {
        return creatorUid;
    }
    /**
     * @param creatorUid the creatorUid to set
     */
    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }
    
}
