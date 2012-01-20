package fi.arcusys.koku.common.external;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import fi.arcusys.koku.common.soa.Child;
import fi.arcusys.koku.common.soa.ChildWithHetu;

/**
 * DAO interface for PYH component: getting family relations information.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 14, 2011
 */
@WebService(targetNamespace = "http://soa.common.koku.arcusys.fi/")
public interface PyhServiceDAO {
    public List<ChildWithHetu> getUsersChildren(
            @WebParam(name = "userUid") String userUid);

    public Child getChildInfo(
            @WebParam(name = "childUid") String childUid);
}
