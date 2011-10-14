package fi.arcusys.koku.tiva.service;

import java.util.List;

import fi.koku.services.entity.kks.v1.InfoGroup;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 14, 2011
 */
public interface KksCollectionsDAO {
    
    List<InfoGroup> getInfoGroups(final String employeeUid); 
}
