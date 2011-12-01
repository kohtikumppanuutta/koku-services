package fi.arcusys.koku.common.service.dto;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 1, 2011
 */
public class AppointmentDTOCriteria {
    private String targetPersonUid;

    /**
     * @return the targetPersonUid
     */
    public String getTargetPersonUid() {
        return targetPersonUid;
    }

    /**
     * @param targetPersonUid the targetPersonUid to set
     */
    public void setTargetPersonUid(String targetPersonUid) {
        this.targetPersonUid = targetPersonUid;
    }
}
