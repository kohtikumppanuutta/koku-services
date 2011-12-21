package fi.arcusys.koku.common.soa;

import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 21, 2011
 */
@WebService(targetNamespace = "http://soa.common.koku.arcusys.fi/")
public interface KokuScheduledTasksService {
    void perform();
}
