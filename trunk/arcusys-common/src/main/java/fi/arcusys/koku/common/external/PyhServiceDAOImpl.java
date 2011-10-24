package fi.arcusys.koku.common.external;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.soa.Child;
import fi.arcusys.koku.common.soa.ChildWithHetu;
import fi.arcusys.koku.common.soa.User;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.koku.services.entity.customer.v1.AuditInfoType;
import fi.koku.services.entity.customer.v1.CustomerServiceFactory;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.ElectronicContactInfoType;
import fi.koku.services.entity.customer.v1.ElectronicContactInfosType;
import fi.koku.services.entity.customer.v1.PhoneNumberType;
import fi.koku.services.entity.customer.v1.PhoneNumbersType;
import fi.koku.services.entity.customer.v1.ServiceFault;
import fi.koku.services.entity.family.FamilyConstants;
import fi.koku.services.entity.family.v1.FamilyService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 14, 2011
 */
@Stateless
@WebService(serviceName = "PyhServiceDAO", portName = "PyhServiceDAOPort", 
      endpointInterface = "fi.arcusys.koku.common.external.PyhServiceDAO",
      targetNamespace = "http://soa.common.koku.arcusys.fi/")
public class PyhServiceDAOImpl implements PyhServiceDAO {

    private final static Logger logger = LoggerFactory.getLogger(PyhServiceDAOImpl.class);
    
    @EJB
    private UserDAO userDao;
    
    @EJB
    private CustomerServiceDAO customerDao;
    
    private FamilyService familyService;
    
    private String customerServiceUserUid;
    private String customerServiceUserPwd;
    private String communityServiceUserUid;
    private String communityServiceUserPwd;

    @PostConstruct
    public void init() {
        try {
            familyService = new FamilyService(customerServiceUserUid, customerServiceUserPwd, communityServiceUserUid, communityServiceUserPwd);
        } catch(Exception re) {
            logger.error(null, re);
        } 
    }
    
    /**
     * @param userUid
     * @return
     */
    @Override
    public List<ChildWithHetu> getUsersChildren(String userUid) {
        if (familyService == null) {
            throw new IllegalStateException("Failed to initialize communication with PYH.");
        }

        // userDao.getUserByUid().getKunpoName(); ??? getHetuByKunpoName ? KOKU-500 getUsersChildren(); userDao.getOrCreateByKunpoName
        final String ssnByLdapName = customerDao.getSsnByKunpoName(userDao.getOrCreateUser(userUid).getCitizenPortalName());
        
        try {
            final List<ChildWithHetu> result = new ArrayList<ChildWithHetu>();
            for (final CustomerType customer : familyService.getPersonsChildren(ssnByLdapName, ssnByLdapName, "TIVA")) {
                final Child child = convertCustomerToChild(ssnByLdapName, customer);
                final ChildWithHetu childWithHetu = new ChildWithHetu(child);
                childWithHetu.setHetu(customer.getHenkiloTunnus());
                for (final Iterator<User> iter = childWithHetu.getParents().iterator(); iter.hasNext();) {
                    if (userUid.equals(iter.next().getUid())) {
                        iter.remove();
                    }
                }
                result.add(childWithHetu);
            }
            return result;
        } catch(Exception e) {
            logger.error("Failed to get UsersChildren by kunpoUid " + userUid + " and ssn " + ssnByLdapName, e);
            throw new RuntimeException(e);
        }
    }

    private Child convertCustomerToChild(final String ssnByLdapName, final CustomerType customer) throws Exception {
        final String childSsn = customer.getHenkiloTunnus();
        final User user = ExternalDAOsUtil.convertCustomerToUser(customer, customerDao.getKunpoUserInfoBySsn(customer.getHenkiloTunnus()).getUid());
        return getChildByUserAndSsn(ssnByLdapName, childSsn, user);
    }

    private Child getChildByUserAndSsn(final String ssnForAudit, final String childSsn, final User user) throws Exception {
        final Child child = new Child(user);
        final List<User> parents = new ArrayList<User>();
        for (final CustomerType parentExt : familyService.getPersonsParents(childSsn, ssnForAudit, "TIVA")) {
            parents.add(ExternalDAOsUtil.convertCustomerToUser(parentExt, customerDao.getKunpoUserInfoBySsn(parentExt.getHenkiloTunnus()).getUid()));
        }
        child.setParents(parents);
        return child;
    }


    /**
     * @param childUid
     * @return
     */
    @Override
    public Child getChildInfo(String childUid) {
        final String ssnByLdapName = customerDao.getSsnByKunpoName(userDao.getOrCreateUser(childUid).getCitizenPortalName());

        try {
            final AuditInfoType auditHeader = new AuditInfoType();
            auditHeader.setComponent("tiva");
            auditHeader.setUserId(ssnByLdapName);
            return getChildByUserAndSsn(ssnByLdapName, ssnByLdapName, customerDao.getUserInfo(childUid));
        } catch (Exception e) {
            logger.error("Failed to get child info by kunpoUid " + childUid + " and ssn " + ssnByLdapName, e);
            throw new RuntimeException(e);
        }
    }

}
