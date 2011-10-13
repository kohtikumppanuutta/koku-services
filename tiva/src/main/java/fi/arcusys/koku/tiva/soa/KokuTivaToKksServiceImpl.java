package fi.arcusys.koku.tiva.soa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.tiva.service.ConsentServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 3, 2011
 */
@Stateless
@WebService(serviceName = "KokuTivaToKksService", portName = "KokuTivaToKksServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuTivaToKksService",
        targetNamespace = "http://services.koku.fi/entity/tiva/v1")
public class KokuTivaToKksServiceImpl implements KokuTivaToKksService {

    @EJB
    private ConsentServiceFacade consentServiceFacade;
    
    @EJB
    private UsersAndGroupsService usersService;
    
    /**
     * @param prefix
     * @param limit
     * @return
     */
    @Override
    public List<ConsentTemplateShort> queryConsentTemplates(String prefix, Integer limit) {
        final List<ConsentTemplateShort> result = new ArrayList<ConsentTemplateShort>();
        for (final ConsentTemplateTO templateTO : consentServiceFacade.getConsentTemplates(prefix, limit)) {
            final ConsentTemplateShort templateShort = new ConsentTemplateShort();
            templateShort.setConsentTemplateId(templateTO.getConsentTemplateId());
            templateShort.setTemplateName(templateTO.getTitle());
            result.add(templateShort);
        }
        return result;
    }

    /**
     * @param consent
     */
    @Override
    public void createConsent(ConsentExternal consent) {
        final List<String> receipients = new ArrayList<String>();
        for (final String hetu : consent.getConsentProviders()) {
            receipients.add(usersService.getUserUidByKunpoSsn(hetu));
        }
        
        final ConsentKksExtraInfo extraInfo = new ConsentKksExtraInfo();
        extraInfo.setInformationTargetId(consent.getInformationTargetId());
        extraInfo.setGivenTo(consent.getGivenTo());
        extraInfo.setMetaInfo(consent.getMetaInfo());
        
        consentServiceFacade.requestForConsent(
                consent.getTemplate().getConsentTemplateId(), 
                usersService.getUserUidByEmployeeSsn(consent.getConsentRequestor()), 
                usersService.getUserUidByKunpoSsn(consent.getTargetPerson()),
                receipients, ConsentReceipientsType.BothParents, 
                null, consent.getValidTill(), Boolean.TRUE, extraInfo);
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<ConsentExternal> queryConsents(ConsentSearchCriteria query) {
        final List<ConsentExternal> result = new ArrayList<ConsentExternal>();
        final List<ConsentTO> consents = consentServiceFacade.searchConsents(query);
        for (final ConsentTO consent : consents) {
            final ConsentExternal consentExternal = new ConsentExternal();
            consentExternal.setConsentId(consent.getConsentId());
            final List<String> consentProviders = new ArrayList<String>();
            for (final String userUid : consent.getReceipients()) {
                consentProviders.add(getSsnByKunpoUid(userUid));
            }
            consentExternal.setConsentProviders(consentProviders);
            consentExternal.setConsentRequestor(getSsnByLooraUid(consent.getRequestor()));
            consentExternal.setDescription(consent.getTemplateDescription());
            consentExternal.setGivenAt(consent.getGivenAt());
            consentExternal.setGivenTo(consent.getGivenToParties());
            consentExternal.setInformationTargetId(consent.getInformationTargetId());
            consentExternal.setMetaInfo(consent.getMetaInfo());
            consentExternal.setStatus(consent.getStatus());
            consentExternal.setTargetPerson(getSsnByKunpoUid(consent.getTargetPersonUid()));
            final ConsentTemplateShort template = new ConsentTemplateShort();
            template.setConsentTemplateId(consent.getTemplateId());
            template.setTemplateName(consent.getTemplateName());
            consentExternal.setTemplate(template);
            consentExternal.setValidTill(consent.getValidTill());
            result.add(consentExternal);
        }
        return result;
    }

    /**
     * @param requestor
     * @return
     */
    private String getSsnByLooraUid(String userUid) {
        return usersService.getSsnByLdapName(usersService.getLooraNameByUserUid(userUid));
    }

    protected String getSsnByKunpoUid(final String userUid) {
        return usersService.getSsnByLdapName(usersService.getKunpoNameByUserUid(userUid));
    }

}
