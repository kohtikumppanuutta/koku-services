package fi.arcusys.koku.tiva.soa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 17, 2011
 */
@Stateless
@WebService(serviceName = "KokuTietopyyntoProcessingService", portName = "KokuTietopyyntoProcessingServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuTietopyyntoProcessingService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public class KokuTietopyyntoProcessingServiceImpl implements KokuTietopyyntoProcessingService {

    private final static Logger logger = LoggerFactory.getLogger(KokuTietopyyntoProcessingServiceImpl.class);
    
    /**
     * @param request
     * @return
     */
    @Override
    public Long createInformationRequest(InformationRequestTO request) {
        // TODO Auto-generated method stub
        logger.info("createInformationRequest: " + request);
        return 123L;
    }

    /**
     * @param requestId
     * @param categoryId
     * @param informationDetails
     */
    @Override
    public void approveRequest(InformationRequestReplyTO reply) {
        // TODO Auto-generated method stub
        logger.info("approveRequest: " + reply.getRequestId() + ", " + reply.getCategoryIds() + ", " + reply.getInformationDetails());
    }

    /**
     * @param requestId
     */
    @Override
    public void declineRequest(Long requestId, final String explanation) {
        // TODO Auto-generated method stub
        logger.info("declineRequest: " + requestId + ", explanation: " + explanation);
    }

    /**
     * @return
     */
    @Override
    public InformationCategoryTO getCategories() {
        // TODO Auto-generated method stub
        final InformationCategoryTO rootCategory = new InformationCategoryTO();
        rootCategory.setCategoryId(1L);
        rootCategory.setName("Root");
        rootCategory.setDescription("Root category for information classification");
        final List<InformationCategoryTO> rootSubcategories = new ArrayList<InformationCategoryTO>();
        final InformationCategoryTO childOne = new InformationCategoryTO();
        childOne.setCategoryId(2L);
        childOne.setName("childOne");
        childOne.setDescription("childOne description");
        rootSubcategories.add(childOne);
        final InformationCategoryTO childTwo = new InformationCategoryTO();
        childTwo.setCategoryId(3L);
        childTwo.setName("childTwo");
        childTwo.setDescription("childTwo description");
        final InformationCategoryTO childTwoOne = new InformationCategoryTO();
        childTwoOne.setCategoryId(4L);
        childTwoOne.setName("childTwoOne");
        childTwoOne.setDescription("childTwoOne description");
        final List<InformationCategoryTO> childTwoSubcategories = new ArrayList<InformationCategoryTO>();
        childTwoSubcategories.add(childTwoOne);
        childTwo.setSubcategories(childTwoSubcategories);
        rootSubcategories.add(childTwo);
        rootCategory.setSubcategories(rootSubcategories);
        return rootCategory;
    }

}
