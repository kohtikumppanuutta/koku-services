package fi.koku.services.utility.authorization.impl;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.koku.services.utility.authorization.v1.GroupQueryCriteriaType;
import fi.koku.services.utility.authorization.v1.GroupType;
import fi.koku.services.utility.authorization.v1.GroupsType;
import fi.koku.services.utility.authorization.v1.MemberPicsType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/test-application-context.xml"})
public class GroupServiceLDAPImplTest {
  @Autowired
  private GroupServiceLDAPImpl cut;
  
  @BeforeClass
  public static void init() throws Exception {
  }
  
  @Test
  public void testGetGroups() throws Exception {
      GroupQueryCriteriaType c = new GroupQueryCriteriaType();
      c.setDomain("kks");
      c.setGroupClass("registry");
      c.setMemberPics(new MemberPicsType());
      c.getMemberPics().getMemberPic().add("292929-2929");
      c.getMemberPics().getMemberPic().add("202020-2001");
      c.getMemberPics().getMemberPic().add("202020-2002");
      GroupsType groups = cut.getGroups(c);
      Map<String, GroupType> grps = new HashMap<String, GroupType>();
      for(GroupType g : groups.getGroup()) {
        grps.put(g.getId(), g);
      }
      assertEquals(grps.keySet().containsAll(Arrays.asList("daycareregistry", "healthcareregistry")), true);
      assertEquals(grps.get("daycareregistry").getMemberPics().getMemberPic().size(), 2);
      assertEquals(grps.get("healthcareregistry").getMemberPics().getMemberPic().size(), 1);
  }
  
  @Test
  public void testGetGroupsQuery() {
    List<GroupServiceLDAPImpl.LdapPerson> p1 = Arrays.asList(
        new GroupServiceLDAPImpl.LdapPerson("cn=kirsi.kuntalainen,ou=People,o=koku,dc=example,dc=org", "222222-2222")
        );
    List<GroupServiceLDAPImpl.LdapPerson> p2 = Arrays.asList(
        new GroupServiceLDAPImpl.LdapPerson("cn=kirsi.kuntalainen,ou=People,o=koku,dc=example,dc=org", "222222-2222"),
        new GroupServiceLDAPImpl.LdapPerson("cn=kalle.kuntalainen,ou=People,o=koku,dc=example,dc=org", "111111-1111")
        );
    assertEquals("(&(objectclass=groupofnames)(member=cn=kirsi.kuntalainen,ou=People,o=koku,dc=example,dc=org))", cut.getGroupsQuery(p1));
    assertEquals("(&(objectclass=groupofnames)(|(member=cn=kirsi.kuntalainen,ou=People,o=koku,dc=example,dc=org)(member=cn=kalle.kuntalainen,ou=People,o=koku,dc=example,dc=org)))",
        cut.getGroupsQuery(p2));
  }
  
  @Test
  public void testGgetPersonsQuery() {
    assertEquals("(&(objectclass=inetorgperson)(|(uid=a)(uid=b)))", cut.getPersonsQuery(Arrays.asList("a", "b")));
    assertEquals("(&(objectclass=inetorgperson)(uid=a))", cut.getPersonsQuery(Arrays.asList("a")));
  }

}
