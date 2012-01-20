package fi.arcusys.koku.tiva.soa;

import java.util.HashMap;
import java.util.Map;

import fi.arcusys.koku.common.service.datamodel.ConsentType;

/**
 * Type of consent creation.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public enum ConsentCreateType {
    Electronic(ConsentType.Electronic), EmailBased(ConsentType.EmailBased), PaperBased(ConsentType.PaperBased), Verbal(ConsentType.Verbal), Fax(ConsentType.Fax);

    private final static Map<ConsentType, ConsentCreateType> dmToSoaMapping = new HashMap<ConsentType, ConsentCreateType>();
    
    private final ConsentType dmStatus;
    
    private ConsentCreateType(final ConsentType datamodelStatus) {
        dmStatus = datamodelStatus;
    }
    
    public static ConsentCreateType valueOf(final ConsentType datamodelStatus) {
        if (!dmToSoaMapping.containsKey(datamodelStatus) ) {
            for (final ConsentCreateType soaStatus : values()) {
                if (soaStatus.dmStatus == datamodelStatus) {
                    dmToSoaMapping.put(datamodelStatus, soaStatus);
                }
            }

            if (!dmToSoaMapping.containsKey(datamodelStatus) ) {
                throw new IllegalArgumentException("Mapping is missing for ConsentType: " + datamodelStatus);
            }
        }
        return dmToSoaMapping.get(datamodelStatus);
    }
    
    public ConsentType getConsentType() {
        return dmStatus;
    }
}
