package fi.arcusys.koku.tiva.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.tiva.service.KksCollectionsDAO;
import fi.koku.services.entity.kks.v1.GroupsHelper;
import fi.koku.services.entity.kks.v1.InfoGroup;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 14, 2011
 */
@Stateless
public class KksCollectionsDAOImpl implements KksCollectionsDAO {
    
    private final static Logger logger = LoggerFactory.getLogger(KksCollectionsDAOImpl.class);

    @EJB
    private UsersAndGroupsService usersService;
    
    @EJB
    private UserDAO userDao;
    
    private String uid = "marko";
    private String pwd = "marko";
    private String component = "tiva";
    private String urlBase = "http://localhost:8180";
    
    private GroupsHelper helper;
    
    @PostConstruct
    public void init() {
        try {
            helper = new GroupsHelper(uid, pwd, component, urlBase);
        } catch(RuntimeException re) {
            logger.error(null, re);
        } 
    }
    
    /**
     * @param employeeUid
     * @return
     */
    @Override
    public List<InfoGroup> getInfoGroups(String employeeUid) {
        if (helper == null) {
            throw new IllegalStateException("Failed to initialize communication with KKS.");
        }
        
        final String ssnByLdapName = usersService.getSsnByLdapName(userDao.getUserByUid(employeeUid).getEmployeePortalName());
        try {
            return helper.getInfoGroups(ssnByLdapName); 
        } catch(RuntimeException re) {
            logger.error("Failed to get InfoGroups by employeeUid " + employeeUid + " and ssn " + ssnByLdapName, re);
            throw re;
        }
    }

}
