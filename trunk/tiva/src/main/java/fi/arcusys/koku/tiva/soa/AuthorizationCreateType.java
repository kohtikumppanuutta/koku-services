package fi.arcusys.koku.tiva.soa;

import fi.arcusys.koku.common.service.datamodel.AuthorizationType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 13, 2011
 */
public enum AuthorizationCreateType {
    Electronic, Non_Electronic;
    
    public static AuthorizationCreateType valueOf(final AuthorizationType dmType) {
        if (valueOf(dmType.name()) != null) {
            return valueOf(dmType.name());
        }
        throw new IllegalArgumentException("SOA: AuthorizationCreateType not found for " + dmType);
    }
}
