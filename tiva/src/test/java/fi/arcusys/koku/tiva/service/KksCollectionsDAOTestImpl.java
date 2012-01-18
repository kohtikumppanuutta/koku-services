package fi.arcusys.koku.tiva.service;

import java.util.Collections;
import java.util.List;

import fi.arcusys.koku.tiva.service.impl.KksCollectionsDAOImpl;
import fi.koku.services.entity.kks.v1.InfoGroup;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jan 18, 2012
 */
public class KksCollectionsDAOTestImpl extends KksCollectionsDAOImpl {
    /**
     * @param employeeUid
     * @return
     */
    @Override
    public List<InfoGroup> getInfoGroups(String employeeUid) {
        return Collections.singletonList(new InfoGroup("1", "test"));
    }
}
