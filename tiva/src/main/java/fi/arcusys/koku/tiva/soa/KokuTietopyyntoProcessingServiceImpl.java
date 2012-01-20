package fi.arcusys.koku.tiva.soa;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.tiva.service.InformationRequestServiceFacade;

/**
 * Implementation of TIVA-Tietopyyntö-processing operations, called from the TIVA-Tietopyyntö Intalio process.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 17, 2011
 */
@Stateless
@WebService(serviceName = "KokuTietopyyntoProcessingService", portName = "KokuTietopyyntoProcessingServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuTietopyyntoProcessingService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
@Interceptors(KokuTietopyyntoInterceptor.class)
public class KokuTietopyyntoProcessingServiceImpl implements KokuTietopyyntoProcessingService {

    private final static Logger logger = LoggerFactory.getLogger(KokuTietopyyntoProcessingServiceImpl.class);
    
    @EJB
    private InformationRequestServiceFacade serviceFacade;
    
    /**
     * @param request
     * @return
     */
    @Override
    public Long createInformationRequest(InformationRequestTO request) {
        return serviceFacade.createInformationRequest(request);
    }

    /**
     * @param requestId
     * @param categoryId
     * @param informationDetails
     */
    @Override
    public void approveRequest(InformationRequestReplyTO reply) {
        serviceFacade.approveRequest(reply);
    }

    /**
     * @param requestId
     */
    @Override
    public void declineRequest(Long requestId, final String userUid, final String explanation) {
        serviceFacade.declineRequest(requestId, userUid, explanation);
    }

    /**
     * @return
     */
    @Override
    public InformationCategoryTO getCategories(final String employeeUid) {
        return serviceFacade.getCategories(employeeUid);
//        // TODO Auto-generated method stub
//        final InformationCategoryTO rootCategory = new InformationCategoryTO();
//        rootCategory.setCategoryId(1L);
//        rootCategory.setName("Root");
//        rootCategory.setDescription("Root category for information classification");
//        final List<InformationCategoryTO> rootSubcategories = new ArrayList<InformationCategoryTO>();
//        final InformationCategoryTO childOne = new InformationCategoryTO();
//        childOne.setCategoryId(2L);
//        childOne.setName("childOne");
//        childOne.setDescription("childOne description");
//        rootSubcategories.add(childOne);
//        final InformationCategoryTO childTwo = new InformationCategoryTO();
//        childTwo.setCategoryId(3L);
//        childTwo.setName("childTwo");
//        childTwo.setDescription("childTwo description");
//        final InformationCategoryTO childTwoOne = new InformationCategoryTO();
//        childTwoOne.setCategoryId(4L);
//        childTwoOne.setName("childTwoOne");
//        childTwoOne.setDescription("childTwoOne description");
//        final List<InformationCategoryTO> childTwoSubcategories = new ArrayList<InformationCategoryTO>();
//        childTwoSubcategories.add(childTwoOne);
//        childTwo.setSubcategories(childTwoSubcategories);
//        rootSubcategories.add(childTwo);
//        rootCategory.setSubcategories(rootSubcategories);
//        return rootCategory;
    }

}
