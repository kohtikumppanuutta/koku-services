package fi.arcusys.koku.tiva.soa;

import fi.arcusys.koku.common.service.datamodel.ReceipientsType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 5, 2011
 */
public enum ConsentReceipientsType {
    Child, AnyParent, BothParents;
    
    public static ConsentReceipientsType valueOf(final ReceipientsType dmType) {
        if (dmType == null) {
            return null;
        }
        final ConsentReceipientsType typeByName = valueOf(dmType.name());
        if (typeByName != null) {
            return typeByName;
        } else {
            throw new IllegalArgumentException("ConsentReceipientsType not found for " + dmType);
        }
    }
    
    public static ReceipientsType toDmType(final ConsentReceipientsType soaType) {
        if (soaType == null) {
            return null;
        }
        final ReceipientsType typeByName = ReceipientsType.valueOf(soaType.name());
        if (typeByName != null) {
            return typeByName;
        } else {
            throw new IllegalArgumentException("ReceipientsType not found for " + soaType);
        }
    }
}
