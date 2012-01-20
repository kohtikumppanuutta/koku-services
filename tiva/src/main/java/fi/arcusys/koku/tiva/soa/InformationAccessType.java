package fi.arcusys.koku.tiva.soa;

import fi.arcusys.koku.common.service.datamodel.InformationReplyAccessType;

/**
 * Type of access to information, requested/provided in TIVA-Tietopyyntö processing
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 15, 2011
 */
public enum InformationAccessType {
    Manual, Portal;
    
    public static InformationAccessType valueOf(final InformationReplyAccessType dmType) {
        if (dmType == null) {
            return null;
        }
        return valueOf(dmType.name());
    }
    
    public InformationReplyAccessType toDmType() {
        return InformationReplyAccessType.valueOf(name());
    }
}
