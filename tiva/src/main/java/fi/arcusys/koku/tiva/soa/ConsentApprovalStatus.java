package fi.arcusys.koku.tiva.soa;

import java.util.HashMap;
import java.util.Map;

import fi.arcusys.koku.common.service.datamodel.ConsentReplyStatus;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 16, 2011
 */
public enum ConsentApprovalStatus {
    Approved(ConsentReplyStatus.Given), Declined(ConsentReplyStatus.Declined), Revoked(ConsentReplyStatus.Revoked);

    private final static Map<ConsentReplyStatus, ConsentApprovalStatus> dmToSoaMapping = new HashMap<ConsentReplyStatus, ConsentApprovalStatus>();
    
    private final ConsentReplyStatus dmStatus;
    
    private ConsentApprovalStatus(final ConsentReplyStatus datamodelStatus) {
        dmStatus = datamodelStatus;
    }
    
    public static ConsentApprovalStatus valueOf(final ConsentReplyStatus datamodelStatus) {
        if (!dmToSoaMapping.containsKey(datamodelStatus) ) {
            for (final ConsentApprovalStatus soaStatus : values()) {
                if (soaStatus.dmStatus == datamodelStatus) {
                    dmToSoaMapping.put(datamodelStatus, soaStatus);
                }
            }

            if (!dmToSoaMapping.containsKey(datamodelStatus) ) {
                throw new IllegalArgumentException("Mapping is missing for ConsentReplyStatus: " + datamodelStatus);
            }
        }
        return dmToSoaMapping.get(datamodelStatus);
    }
}
