package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 3, 2011
 */
@Stateless
@WebService(serviceName = "KokuTivaToKksService", portName = "KokuTivaToKksServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuTivaToKksService",
        targetNamespace = "http://services.koku.fi/entity/tiva/v1")
public class KokuTivaToKksServiceImpl implements KokuTivaToKksService {

    /**
     * @param prefix
     * @param limit
     * @return
     */
    @Override
    public List<ConsentTemplateShort> queryConsentTemplates(String prefix,
            Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param consent
     */
    @Override
    public void createConsent(ConsentExternal consent) {
        // TODO Auto-generated method stub

    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<ConsentExternal> queryConsents(ConsentSearchCriteria query) {
        // TODO Auto-generated method stub
        return null;
    }

}
