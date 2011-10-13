package fi.arcusys.koku.common.service.impl;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.DaycareRequestDAO;
import fi.arcusys.koku.common.service.datamodel.DaycareRequest;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 13, 2011
 */
@Stateless
public class DaycareRequestDAOImpl extends AbstractEntityDAOImpl<DaycareRequest> implements DaycareRequestDAO {
    
    public DaycareRequestDAOImpl() {
        super(DaycareRequest.class);
    }
}
