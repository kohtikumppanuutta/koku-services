package fi.arcusys.koku.tiva.soa;

import java.util.HashMap;
import java.util.Map;

import fi.arcusys.koku.common.service.datamodel.ConsentType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public enum ConsentCreateType {
    Electronic(ConsentType.Electronic), EmailBased(ConsentType.EmailBased), PaperBased(ConsentType.PaperBased);

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
}
