package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.TargetPerson;

/**
 * DAO interface for CRUD operations with 'TargetPerson' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 22, 2011
 */
public interface TargetPersonDAO extends AbstractEntityDAO<TargetPerson> {

//    TargetPerson getTargetPersonByUid(final String uid);

    /**
     * @param targetPerson
     * @param receipients
     * @return
     */
    TargetPerson getOrCreateTargetPerson(final String targetPerson, final List<String> receipients);
    
}
