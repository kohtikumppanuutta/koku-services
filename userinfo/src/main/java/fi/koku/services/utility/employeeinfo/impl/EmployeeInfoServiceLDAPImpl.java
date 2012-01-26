package fi.koku.services.utility.employeeinfo.impl;

import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextProcessor;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;

import fi.koku.services.utility.employee.v1.EmployeePicsType;
import fi.koku.services.utility.employee.v1.EmployeeType;
import fi.koku.services.utility.employee.v1.EmployeesType;
import fi.koku.services.utility.employee.v1.UserIdsType;

/**
 * KoKu employeeInfo service LDAP implementation class.
 * 
 * @author hanhian
 */
public class EmployeeInfoServiceLDAPImpl implements EmployeeInfoService{
  
  private Logger logger = LoggerFactory.getLogger(EmployeeInfoServiceLDAPImpl.class);
  private SimpleLdapTemplate ldapTemplate;
  
  @Override
  public EmployeesType getEmployeesByIds(UserIdsType idsType) {
    return getEmployees(getQuery(idsType.getId(), "cn"));
  }
  
  @Override
  public EmployeesType getEmployeesByPics(EmployeePicsType picsType) {
    return getEmployees(getQuery(picsType.getPic(), "uid"));
  }

  public void setLdapTemplate(SimpleLdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  private EmployeesType getEmployees(String query) {
    SearchControls ctrl = new SearchControls();
    ctrl.setReturningAttributes(new String[] { "cn", "givenName", "sn", "uid", "mail" });
    ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
    logger.debug("getEmployee: query: " + query.toString());

    List<EmployeeType> employees = ldapTemplate.search("", query, ctrl, new LdapPersonMapper(),
        new DirContextProcessorNoop());

    EmployeesType employeesType = new EmployeesType();
    if (employees != null) {
      employeesType.getEmployee().addAll(employees);
    }
    return employeesType;
  }
  
  private String getQuery(List<String> ids, String queryType) {
    StringBuilder q = new StringBuilder("(&(objectclass=inetorgperson)");
    boolean hasMultipleCriteria = ids.size() > 1;
    if(hasMultipleCriteria)
      q.append("(|");
    for(Iterator<String> i = ids.iterator(); i.hasNext(); ) {
      q.append("("+queryType+"="+i.next()+")");
    }
    if(hasMultipleCriteria)
      q.append(")");
    q.append(")");
    return q.toString();
  }

  private class LdapPersonMapper implements ParameterizedContextMapper<EmployeeType> {
    @Override
    public EmployeeType mapFromContext(Object ctx) {
      DirContextAdapter a = (DirContextAdapter) ctx;
      EmployeeType emp = new EmployeeType();
      emp.setUserId(a.getStringAttribute("cn"));
      emp.setFirstname(a.getStringAttribute("givenName"));
      emp.setLastname(a.getStringAttribute("sn"));
      emp.setPic(a.getStringAttribute("uid"));
      emp.setEmail(a.getStringAttribute("mail"));
      return emp;
    }
  }

  private static class DirContextProcessorNoop implements DirContextProcessor {
    @Override
    public void postProcess(DirContext ctx) throws NamingException {
    }

    @Override
    public void preProcess(DirContext ctx) throws NamingException {
    }
  }
}