package fi.arcusys.koku.av.soa;

import javax.xml.bind.annotation.XmlTransient;

import fi.arcusys.koku.common.service.dto.AppointmentDTOCriteria;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 1, 2011
 */
public class AppointmentCriteria {
    private String targetPersonHetu;
    private String targetPersonUid;
    
    /**
     * @return the targetPersonHetu
     */
    public String getTargetPersonHetu() {
        return targetPersonHetu;
    }

    /**
     * @param targetPersonHetu the targetPersonHetu to set
     */
    public void setTargetPersonHetu(String targetPersonHetu) {
        this.targetPersonHetu = targetPersonHetu;
    }
    
    /**
     * @return the targetPersonUid
     */
    @XmlTransient
    public String getTargetPersonUid() {
        return targetPersonUid;
    }

    /**
     * @param targetPersonUid the targetPersonUid to set
     */
    public void setTargetPersonUid(String targetPersonUid) {
        this.targetPersonUid = targetPersonUid;
    }

    public AppointmentDTOCriteria toDtoCriteria() {
        final AppointmentDTOCriteria dtoCriteria = new AppointmentDTOCriteria();
        dtoCriteria.setTargetPersonUid(getTargetPersonUid());
        
        return dtoCriteria;
    }
}
