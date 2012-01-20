package fi.arcusys.koku.kv.soa;

import fi.arcusys.koku.common.service.datamodel.Visibility;

/**
 * RequestTemplate visibility type. 
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 5, 2011
 */
public enum RequestTemplateVisibility {
    Creator, Organization, All;

    public static RequestTemplateVisibility valueOf(final Visibility dmType) {
        if (dmType == null) {
            return null;
        }
        final RequestTemplateVisibility typeByName = valueOf(dmType.name());
        if (typeByName != null) {
            return typeByName;
        } else {
            throw new IllegalArgumentException("RequestTemplateVisibility not found for " + dmType);
        }
    }
    
    public static Visibility toDmType(final RequestTemplateVisibility soaType) {
        if (soaType == null) {
            return null;
        }
        final Visibility typeByName = Visibility.valueOf(soaType.name());
        if (typeByName != null) {
            return typeByName;
        } else {
            throw new IllegalArgumentException("Visibility not found for " + soaType);
        }
    }
}
